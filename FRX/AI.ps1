
# Programme principal
$assistant = ""
$apiKey       = ""
$demande = "donne moi la recette d'un gatal au yaourt"



function ConvertFrom-UTF8{
    param($texte)
    $texte_correct = [System.Text.Encoding]::UTF8.GetString([System.Text.Encoding]::Default.GetBytes($texte))
    return $texte_correct
}
function Send-IAFROUXMessage {
    param($assistant,[string]$message)
    try {
        $response = Invoke-RestMethod -Uri "https://api.ai21.com/studio/v1/assistants/$assistant/run" `
            -Method Post `
            -Headers @{
                "Content-Type" = "application/json"
                "Authorization" = "Bearer $apiKey"
            } `
            -Body ([System.Text.Encoding]::UTF8.GetBytes((@{
                input = @(
                    @{
                        role   = "user"
                        content = $message
                    }
                )
                params = @{
                    output_type = "json"
                    include     = @("data_sources","requirements_result","requirements_result.metadata")
                }
            } | ConvertTo-Json -Depth 4 -Compress)))
    }
    catch {
        Write-Host "Erreur lors de l'appel   l'API :" $_.Exception.Response.StatusCode
        $errorResponse = $_.Exception.Response.GetResponseStream()
        $reader = New-Object System.IO.StreamReader($errorResponse)
        Write-Host $reader.ReadToEnd()
    }
    return $response
}
function Get-IAFROUXReponse {
    param($apikey,$runId)
    # R cup ration de la r ponse 
    do {
        Start-Sleep -Milliseconds 250

        $response = Invoke-RestMethod -Uri "https://api.ai21.com/studio/v1/maestro/runs/$runId" `
            -Method  Get `
            -Headers @{"Authorization" = "Bearer $apiKey"}
        Write-Host "." -ForegroundColor Gray -NoNewline
    } while ($response.status -eq "in_progress")
    $texte = ConvertFrom-UTF8 $response.result
    return $texte

}

$StatusQuestion  = Send-IAFROUXMessage -message $demande -assistant $assistant 
$ReponseQuestion = Get-IAFROUXReponse  -runId $StatusQuestion.id -apikey $api
Write-Host "`n$ReponseQuestion" -ForegroundColor Yellow
