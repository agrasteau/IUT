# V-253290
param($obj)
######################## CHECK ISADMIN ########################
$user = [System.Security.Principal.WindowsIdentity]::GetCurrent()
$userclaim = [System.Security.Principal.WindowsPrincipal]::new($user)
$isadmin = $userclaim.IsInRole("administrateurs") -or $userclaim.IsInRole("administrators")
if(!$isadmin){
    $obj.state = "SKIP"
    $obj.message = "SKIPPED : Require Admin Rights"
    return $obj
}
###################### FIN CHECK ISADMIN ######################

try {
    $defenderStatus = Get-MpPreference
    if ($defenderStatus.AntivirusEnabled) {
        $obj.isVulnState = $false
        $obj.message = "Windows Defender Antivirus is enabled."
    } else {
        $obj.isVulnState = $true
        $obj.message = "Windows Defender Antivirus is not enabled."
    }
    $obj.state = "DONE"
} catch {
    $obj.isVulnState = $null
    $obj.message = "ERROR: Unable to determine Windows Defender status. $_"
    $obj.state = "ERROR"
}

return $obj
