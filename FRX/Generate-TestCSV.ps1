# ============================================================
#  Generate-TestCSV.ps1
#  Génère un fichier STIG.csv de test avec des contrôles
#  Windows 11 STIG réels pour tester le pipeline main.ps1
# ============================================================

$outputDir = $PSScriptRoot
if (-not $outputDir) { $outputDir = Split-Path -Parent $MyInvocation.MyCommand.Path }

$csvPath = Join-Path $outputDir "STIG_Win11_$(Get-Date -Format 'yyyyMMdd').csv"

$stigEntries = @(
    [PSCustomObject]@{
        FindingID  = "V-253256"
        Version    = "1"
        RuleID     = "SV-253256r991589_rule"
        IAControls = "SC-28"
        Severity   = "High"
        Message    = "Windows 11 must use UEFI. UEFI provides additional security features over legacy BIOS."
    },
    [PSCustomObject]@{
        FindingID  = "V-253257"
        Version    = "1"
        RuleID     = "SV-253257r991589_rule"
        IAControls = "SC-28"
        Severity   = "High"
        Message    = "Secure Boot must be enabled on Windows 11 systems. Secure Boot is a standard that ensures systems boot only to a trusted operating system."
    },
    [PSCustomObject]@{
        FindingID  = "V-253259"
        Version    = "1"
        RuleID     = "SV-253259r991589_rule"
        IAControls = "AC-2"
        Severity   = "High"
        Message    = "Windows 11 must be configured to require a minimum password length of 14 characters."
    },
    [PSCustomObject]@{
        FindingID  = "V-253260"
        Version    = "1"
        RuleID     = "SV-253260r991589_rule"
        IAControls = "IA-5"
        Severity   = "Medium"
        Message    = "Windows 11 must enforce password history of 24 or more passwords remembered."
    },
    [PSCustomObject]@{
        FindingID  = "V-253261"
        Version    = "1"
        RuleID     = "SV-253261r991589_rule"
        IAControls = "IA-5"
        Severity   = "Medium"
        Message    = "The maximum password age must be configured to 60 days or less on Windows 11."
    },
    [PSCustomObject]@{
        FindingID  = "V-253262"
        Version    = "1"
        RuleID     = "SV-253262r991589_rule"
        IAControls = "IA-5"
        Severity   = "Medium"
        Message    = "The minimum password age must be configured to at least 1 day on Windows 11."
    },
    [PSCustomObject]@{
        FindingID  = "V-253263"
        Version    = "1"
        RuleID     = "SV-253263r991589_rule"
        IAControls = "IA-5"
        Severity   = "Medium"
        Message    = "Windows 11 must have the built-in Windows password complexity policy enabled."
    },
    [PSCustomObject]@{
        FindingID  = "V-253264"
        Version    = "1"
        RuleID     = "SV-253264r991589_rule"
        IAControls = "AC-7"
        Severity   = "Medium"
        Message    = "Windows 11 must be configured to lock the account after 3 invalid logon attempts within 15 minutes."
    },
    [PSCustomObject]@{
        FindingID  = "V-253265"
        Version    = "1"
        RuleID     = "SV-253265r991589_rule"
        IAControls = "AC-7"
        Severity   = "Medium"
        Message    = "Windows 11 account lockout duration must be configured to 15 minutes or greater."
    },
    [PSCustomObject]@{
        FindingID  = "V-253266"
        Version    = "1"
        RuleID     = "SV-253266r991589_rule"
        IAControls = "AU-9"
        Severity   = "Medium"
        Message    = "Windows 11 must be configured to audit Account Logon Failure events."
    },
    [PSCustomObject]@{
        FindingID  = "V-253280"
        Version    = "1"
        RuleID     = "SV-253280r991589_rule"
        IAControls = "CM-7"
        Severity   = "Low"
        Message    = "Windows 11 must have Windows Defender Firewall enabled for the domain profile."
    },
    [PSCustomObject]@{
        FindingID  = "V-253290"
        Version    = "1"
        RuleID     = "SV-253290r991589_rule"
        IAControls = "SI-3"
        Severity   = "High"
        Message    = "Windows 11 must have Windows Defender Antivirus enabled."
    }
)

$stigEntries | Export-Csv -Path $csvPath -NoTypeInformation -Encoding UTF8
Write-Host "CSV généré : $csvPath ($($stigEntries.Count) contrôles)" -ForegroundColor Green
Write-Host "Lance maintenant main.ps1" -ForegroundColor Cyan
