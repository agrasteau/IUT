# V-253264
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
    $lockoutThreshold = (Get-ItemProperty -Path 'HKLM:\SOFTWARE\Microsoft\Windows NT\CurrentVersion\Winlogon').LockoutThreshold
    if ($lockoutThreshold -eq 3) {
        $obj.isVulnState = $false
        $obj.message = 'Account lockout threshold is correctly set to 3.'
    } else {
        $obj.isVulnState = $true
        $obj.message = 'Account lockout threshold is not set to 3.'
    }
} catch {
    $obj.isVulnState = $true
    $obj.message = 'Error checking account lockout threshold: ' + $_.Exception.Message
}

$obj.state = 'DONE'
return $obj
