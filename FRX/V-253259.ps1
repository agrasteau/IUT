<# V-253259
param($obj)
######################## CHECK ISADMIN ########################
$user = [System.Security.Principal.WindowsIdentity]::GetCurrent()
$userclaim = [System.Security.Principal.WindowsPrincipal]::new($user)
$isadmin = $userclaim.IsInRole('administrateurs') -or $userclaim.IsInRole('administrators')
if(!$isadmin){
    $obj.state = 'SKIP'
    $obj.message = 'SKIPPED : Require Admin Rights'
    return $obj
}
###################### FIN CHECK ISADMIN ######################

try {
    $minPasswordLength = (Get-LocalUser | Get-LocalUser | Where-Object { $_.Name -eq 'Administrator' }).PasswordMinimumLength
    if ($minPasswordLength -ge 14) {
        $obj.isVulnState = $false
        $obj.message = 'Compliant: Minimum password length is 14 or more.'
    } else {
        $obj.isVulnState = $true
        $obj.message = 'Non-Compliant: Minimum password length is less than 14.'
    }
    $obj.state = 'DONE'
} catch {
    $obj.isVulnState = $null
    $obj.message = 'ERROR: Unable to determine password policy. ' + $_.Exception.Message
    $obj.state = 'ERROR'
}

return $obj
