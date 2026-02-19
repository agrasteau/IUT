<# V-253260
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
    $passwordHistory = (Get-ItemProperty -Path "HKLM:\Software\Microsoft\Windows NT\CurrentVersion\Winlogon").PasswordHistory
    if ($passwordHistory -ge 24) {
        $obj.isVulnState = $false
        $obj.message = "COMPLIANT: Password history is set to $passwordHistory."
    } else {
        $obj.isVulnState = $true
        $obj.message = "NON-COMPLIANT: Password history is set to $passwordHistory, which is less than 24."
    }
} catch {
    $obj.isVulnState = $null
    $obj.message = "ERROR: Unable to retrieve password history setting. $_"
}

$obj.state = "DONE"

return $obj
