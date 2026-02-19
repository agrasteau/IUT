param($obj)
######################## CHECK ISADMIN ########################
#$user      = [System.Security.Principal.WindowsIdentity]::GetCurrent()
#$userclaim = [System.Security.Principal.WindowsPrincipal]::new($user)
#$isadmin = $userclaim.IsInRole("administrateurs") -or $userclaim.IsInRole("administrators") 
#if(!$isadmin){
#    $obj.state   = "SKIP"
#    $obj.message = "SKIPPED : Require Admin Rights"
#    return $obj
#}
###################### FIN CHECK ISADMIN ######################







$obj.isVulnState = $null
$obj.message = ""
$obj.state = "DONE"

return $obj
