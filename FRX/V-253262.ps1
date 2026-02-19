# V-253262
param($obj)
######################## CHECK ISADMIN ########################
$user      = [System.Security.Principal.WindowsIdentity]::GetCurrent()
$userclaim = [System.Security.Principal.WindowsPrincipal]::new($user)
$isadmin = $userclaim.IsInRole("administrateurs") -or $userclaim.IsInRole("administrators") 
if(!$isadmin){
    $obj.state   = "SKIP"
    $obj.message = "SKIPPED : Require Admin Rights"
    return $obj
}
###################### FIN CHECK ISADMIN ######################

try {
    $minPasswordAge = (Get-LocalUser | Get-LocalUser | Where-Object { $_.Name -eq "Administrator" }).PasswordAge
    if ($minPasswordAge -ge 1) {
        $obj.isVulnState = $false
        $obj.message = "The minimum password age is configured correctly."
    } else {
        $obj.isVulnState = $true
        $obj.message = "The minimum password age is not configured to at least 1 day."
    }
} catch {
    $obj.isVulnState = $null
    $obj.message = "ERROR: $($_.Exception.Message)"
}

$obj.state = "DONE"
return $obj
