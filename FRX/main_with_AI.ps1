# ============================================================
#  MAIN.PS1 — Audit STIG Windows 11 avec génération IA
#  Pipeline : CSV → Scripts V-xxxxxx → (IA si absent) → Excel
# ============================================================

# ── CONSTANTES ──────────────────────────────────────────────
# Détection automatique du dossier où se trouve ce script
$rootStigviewerPath = $PSScriptRoot
if (-not $rootStigviewerPath) {
    # Fallback si lancé depuis la console directement
    $rootStigviewerPath = Split-Path -Parent $MyInvocation.MyCommand.Path
}
Write-Host "Dossier de travail : $rootStigviewerPath" -ForegroundColor DarkGray

# Recherche du template (nom exact du fichier)
$templateFile = Get-ChildItem -Path $rootStigviewerPath -Filter "Template*V-*.ps1" -File | Select-Object -First 1
if (-not $templateFile) {
    # Fallback : cherche n'importe quel template dans le dossier
    $templateFile = Get-ChildItem -Path $rootStigviewerPath -Filter "Template*.ps1" -File | Select-Object -First 1
}
if (-not $templateFile) {
    Write-Warning "Aucun fichier template trouvé dans $rootStigviewerPath"
    $template = $null
} else {
    $template = $templateFile.FullName
    Write-Host "Template trouvé : $($templateFile.Name)" -ForegroundColor DarkGray
}

$temporaryDeposit   = Join-Path $rootStigviewerPath "TODO"
$xlsxOutput         = Join-Path $rootStigviewerPath "MainResult.xlsx"
$apiKey             = ""   # <-- ta clé AI21
$assistant          = ""   # <-- ton assistant AI21


# ════════════════════════════════════════════════════════════
#  FONCTIONS IA  (reprises de AI.ps1)
# ════════════════════════════════════════════════════════════

function ConvertFrom-UTF8 {
    param($texte)
    return [System.Text.Encoding]::UTF8.GetString(
               [System.Text.Encoding]::Default.GetBytes($texte))
}

function Send-IAMessage {
    <#
    .SYNOPSIS
        Envoie un message à l'assistant AI21 et retourne l'objet de run.
    .PARAMETER message
        Le prompt texte à envoyer.
    #>
    param([string]$message)

    $body = @{
        input  = @(@{ role = "user"; content = $message })
        params = @{ output_type = "json" }
    } | ConvertTo-Json -Depth 4 -Compress

    try {
        $response = Invoke-RestMethod `
            -Uri     "https://api.ai21.com/studio/v1/assistants/$assistant/run" `
            -Method  Post `
            -Headers @{
                "Content-Type"  = "application/json"
                "Authorization" = "Bearer $apiKey"
            } `
            -Body ([System.Text.Encoding]::UTF8.GetBytes($body))
    }
    catch {
        Write-Warning "Erreur API IA : $($_.Exception.Response.StatusCode)"
        return $null
    }
    return $response
}

function Get-IAReponse {
    <#
    .SYNOPSIS
        Attend et récupère la réponse d'un run AI21 (polling toutes les 250ms).
    .PARAMETER runId
        L'identifiant du run renvoyé par Send-IAMessage.
    #>
    param([string]$runId)

    do {
        Start-Sleep -Milliseconds 250
        $response = Invoke-RestMethod `
            -Uri     "https://api.ai21.com/studio/v1/maestro/runs/$runId" `
            -Method  Get `
            -Headers @{ "Authorization" = "Bearer $apiKey" }
        Write-Host "." -ForegroundColor Gray -NoNewline
    } while ($response.status -eq "in_progress")

    Write-Host ""   # retour à la ligne après les points
    return (ConvertFrom-UTF8 $response.result)
}

function New-StigScriptViaIA {
    <#
    .SYNOPSIS
        Demande à l'IA de générer un script V-xxxxxx.ps1 pour un contrôle STIG donné.
    .PARAMETER stig
        L'objet CSV du contrôle STIG (FindingID, Message, Severity…).
    .PARAMETER templateContent
        Le contenu du template de base (lu depuis Template_V-xxxxxx.ps1).
    .OUTPUTS
        Chaîne de caractères : code PowerShell généré par l'IA.
    #>
    param($stig, [string]$templateContent)

    # ── Construction du prompt ───────────────────────────────
    $prompt = @"
Tu es un expert en sécurité Windows et PowerShell.
Je dois créer un script d'audit STIG PowerShell pour le contrôle suivant :

ID        : $($stig.FindingID)
Sévérité  : $($stig.Severity)
Règle     : $($stig.RuleID)
Contrôles : $($stig.IAControls)
Description :
$($stig.Message)

Voici le template PowerShell à compléter (structure obligatoire à respecter) :
$templateContent

CONSIGNES STRICTES :
1. Réponds UNIQUEMENT avec le code PowerShell, sans explication, sans balise markdown.
2. Commence directement par la ligne : <# $($stig.FindingID)
3. Utilise uniquement des cmdlets PowerShell natifs Windows (pas de modules tiers).
4. Remplis correctement ${'$'}obj.isVulnState, ${'$'}obj.message et ${'$'}obj.state.
5. Inclus le bloc CHECK ISADMIN si des droits élevés sont nécessaires.
6. Gère les erreurs avec try/catch.
"@

    Write-Host "  [IA] Génération du script $($stig.FindingID)..." -ForegroundColor Cyan

    $run = Send-IAMessage -message $prompt
    if (-not $run) { return $null }

    $code = Get-IAReponse -runId $run.id
    return $code
}


# ════════════════════════════════════════════════════════════
#  PARTIE 1 — CHARGEMENT DES DONNÉES
# ════════════════════════════════════════════════════════════

Write-Host "`n=== Chargement des données ===" -ForegroundColor Yellow

# Dernier CSV STIG disponible
$csvFiles = Get-ChildItem -Path $rootStigviewerPath -Filter "*.csv" -File -ErrorAction SilentlyContinue
if (-not $csvFiles) {
    Write-Error "Aucun fichier CSV trouvé dans : $rootStigviewerPath"
    exit 1
}
$csvfile       = $csvFiles.FullName | Sort-Object -Descending | Select-Object -First 1
$CSVFileImport = Import-Csv -Path $csvfile -Encoding UTF8
Write-Host "CSV chargé : $csvfile ($($CSVFileImport.Count) contrôles)"

# Affiche les colonnes disponibles pour diagnostic
if ($CSVFileImport.Count -gt 0) {
    Write-Host "Colonnes CSV : $($CSVFileImport[0].PSObject.Properties.Name -join ', ')" -ForegroundColor DarkGray
}

# Scripts V-xxxxxx déjà existants (uniquement à la racine, pas dans /TODO/)
$ScriptsList = Get-ChildItem -Path $rootStigviewerPath -Filter "V-*.ps1" -File |
               Select-Object BaseName, FullName

# Contenu du template (pour la génération IA)
if ($template -and (Test-Path $template)) {
    $templateContent = Get-Content -Path $template | Out-String
} else {
    Write-Warning "Template introuvable, utilisation d'un template minimal."
    $templateContent = @'
param($obj)
$obj.isVulnState = $null
$obj.message = ""
$obj.state = "DONE"
return $obj
'@
}


# ════════════════════════════════════════════════════════════
#  PARTIE 2 — TRAITEMENT DE CHAQUE CONTRÔLE STIG
# ════════════════════════════════════════════════════════════

Write-Host "`n=== Traitement des contrôles STIG ===" -ForegroundColor Yellow

$CSVFileImport | ForEach-Object {

    # Ajout des colonnes de résultat à l'objet
    $_ | Add-Member -MemberType NoteProperty -Name "State"       -Value "" -Force
    $_ | Add-Member -MemberType NoteProperty -Name "IsVulnState" -Value "" -Force

    $FileToRun = $ScriptsList | Where-Object BaseName -like $_.FindingID

    # ── CAS 1 : le script existe → on l'exécute ─────────────
    if ($FileToRun) {
        Write-Host "  [OK]     $($_.FindingID)" -ForegroundColor Green
        $_.State = ""
        $_ = . $FileToRun.FullName $_
    }

    # ── CAS 2 : le script est ABSENT ────────────────────────
    else {
        Write-Host "  [ABSENT] $($_.FindingID)" -ForegroundColor Red
        $_.State = "ABSENT"

        # Création du dossier TODO si nécessaire
        if (!(Test-Path $temporaryDeposit)) {
            New-Item -ItemType Directory -Path $temporaryDeposit -Force | Out-Null
        }

        $outputPath = Join-Path $temporaryDeposit "$($_.FindingID).ps1"

        # ── Tentative de génération par l'IA ────────────────
        $codeGenere = New-StigScriptViaIA -stig $_ -templateContent $templateContent

        if ($codeGenere) {
            # Sauvegarde du script généré par l'IA
            $codeGenere | Set-Content -Path $outputPath -Encoding UTF8 -Force
            Write-Host "  [IA OK]  Script généré → $outputPath" -ForegroundColor Cyan

            # Exécution immédiate du script généré
            try {
                $_ = . $outputPath $_
                Write-Host "  [RUN]    $($_.FindingID) → IsVulnState=$($_.IsVulnState)" -ForegroundColor Cyan
            }
            catch {
                Write-Warning "  Erreur à l'exécution du script généré : $_"
                $_.State = "ERROR"
            }
        }
        else {
            # Fallback : template vide si l'IA échoue
            Write-Warning "  [IA KO]  Génération échouée, template vide créé."
            "<#  $($_.FindingID)`n $($_.Message)`n#>`n`n" + $templateContent |
                Set-Content -Path $outputPath -Encoding UTF8 -Force
        }
    }
}


# ════════════════════════════════════════════════════════════
#  PARTIE 3 — EXPORT EXCEL
# ════════════════════════════════════════════════════════════

Write-Host "`n=== Export Excel ===" -ForegroundColor Yellow

$objxls  = New-Object -ComObject "Excel.Application"
$objxls.Visible = $false   # mettre $true pour voir Excel s'ouvrir

$classeur = $objxls.Workbooks.Add()
$feuille  = $classeur.Worksheets.Item(1)
$feuille.Name = "Résultats STIG"

# En-têtes
$feuille.Cells.Range("A1:H1") = "FindingID","Version","RuleID","IAControls","Severity","State","IsVulnState","Message"

# Mise en forme des en-têtes
$headerRange = $feuille.Cells.Range("A1:H1")
$headerRange.Font.Bold = $true
$headerRange.Interior.ColorIndex = 48

# Remplissage des données
$CSVFileImport | ForEach-Object { $ligne = 2 } {

    $feuille.Cells.Range("A${ligne}:H${ligne}") = `
        $_.FindingID, $_.Version, $_.RuleID, $_.IAControls,
        $_.Severity,  $_.State,  $_.IsVulnState, $_.Message

    # Couleur colonne Severity
    $backcolor, $forecolor = switch ($_.Severity) {
        'High'   { 38, 54 }
        'Medium' { 36, 53 }
        'Low'    { 35, 51 }
        Default  { 48, 56 }
    }
    $feuille.Cells.Item($ligne, 5).Interior.ColorIndex = $backcolor
    $feuille.Cells.Item($ligne, 5).Font.ColorIndex     = $forecolor

    # Couleur colonne IsVulnState
    $backcolor, $forecolor = switch ($_.IsVulnState) {
        $false   { 35, 51 }
        $true    { 38, 54 }
        Default  { 48, 56 }
    }
    $cell = $feuille.Cells.Item($ligne, 7)
    $cell.Interior.ColorIndex = $backcolor
    $cell.Font.ColorIndex     = $forecolor
    $cell.Value2 = switch ($_.IsVulnState) {
        $false  { "CONFORME"   }
        $true   { "VULNERABLE" }
        Default { "N/A"        }
    }

    $ligne++
}

$feuille.Columns.AutoFit() | Out-Null

# Sauvegarde
$xlsxPath = $xlsxOutput
$objxls.DisplayAlerts = $false
$classeur.SaveAs($xlsxPath)
$classeur.Close()
$objxls.Quit()

Write-Host "Rapport Excel sauvegardé : $xlsxPath" -ForegroundColor Green
Write-Host "`n=== Terminé ===" -ForegroundColor Yellow
