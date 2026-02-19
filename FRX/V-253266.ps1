# V-253266
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
    $auditPolicy = Get-AuditPolicy -Category "Logon/Logoff"
    $accountLogonFailure = $auditPolicy | Where-Object { $_.Subcategory -eq "Account Logon" -and $_.Failure -eq "Success and Failure" }
    if ($accountLogonFailure) {
        $obj.isVulnState = $false
        $obj.message = "Account Logon Failure events are audited."
    } else {
        $obj.isVulnState = $true
        $obj.message = "Account Logon Failure events are NOT audited."
    }
    $obj.state = "DONE"
} catch {
    $obj.isVulnState = $null
    $obj.message = "ERROR: $($_.Exception.Message)"
    $obj.state = "ERROR"
}

return $obj
