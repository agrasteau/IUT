# V-253265
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
    $lockoutDuration = (Get-LocalUser | Get-LocalGroupMember -Member $_.Name | Where-Object {$_.Name -eq 'Administrators'}).LockoutDuration
    if ($lockoutDuration -ge 15) {
        $obj.isVulnState = $false
        $obj.message = 'Compliant: Lockout duration is set to 15 minutes or greater.'
    } else {
        $obj.isVulnState = $true
        $obj.message = 'Non-Compliant: Lockout duration is less than 15 minutes.'
    }
} catch {
    $obj.isVulnState = $null
    $obj.message = 'ERROR: Unable to determine lockout duration. ' + $_.Exception.Message
}

$obj.state = 'DONE'
return $obj
