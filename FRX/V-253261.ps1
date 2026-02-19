# V-253261
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
    $maxPasswordAge = (Get-LocalUser | Where-Object { $_.Name -eq "Administrator" }).PasswordExpires
    if ($maxPasswordAge -le 60) {
        $obj.isVulnState = $false
        $obj.message = "COMPLIANT: Maximum password age is set to 60 days or less."
    } else {
        $obj.isVulnState = $true
        $obj.message = "NON-COMPLIANT: Maximum password age exceeds 60 days."
    }
} catch {
    $obj.isVulnState = $null
    $obj.message = "ERROR: Unable to determine maximum password age. $_"
}

$obj.state = "DONE"
return $obj
