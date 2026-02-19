# V-253280
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
    $firewallState = Get-NetFirewallProfile -Profile Domain
    if ($firewallState.Enabled -eq $true) {
        $obj.isVulnState = $false
        $obj.message = "Windows Defender Firewall is enabled for the domain profile."
    } else {
        $obj.isVulnState = $true
        $obj.message = "Windows Defender Firewall is not enabled for the domain profile."
    }
    $obj.state = "DONE"
} catch {
    $obj.isVulnState = $null
    $obj.message = "ERROR: $($_.Exception.Message)"
    $obj.state = "ERROR"
}

return $obj
