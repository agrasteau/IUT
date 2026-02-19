# V-253263
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
    $passwordPolicy = Get-LocalUser | Where-Object { $_.Name -eq "Administrator" } | Get-LocalUser | Select-Object -ExpandProperty PasswordRequired
    if ($passwordPolicy) {
        $obj.isVulnState = $false
        $obj.message = "Password complexity policy is enabled."
    } else {
        $obj.isVulnState = $true
        $obj.message = "Password complexity policy is not enabled."
    }
    $obj.state = "DONE"
} catch {
    $obj.isVulnState = $null
    $obj.message = "ERROR: $($_.Exception.Message)"
    $obj.state = "ERROR"
}

return $obj
