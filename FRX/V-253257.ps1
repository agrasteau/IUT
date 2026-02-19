<#  V-253257
 Secure Boot must be enabled on Windows 11 systems.Secure Boot is a standard that ensures systems boot only to a trusted operating system. Secure Boot is required to support additional security feature...
#>

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


try{
    if(Confirm-SecureBootUEFI -ErrorAction Stop){
        $obj.isVulnState = $false
        $obj.message = "Conformité respectée : Le Secure Boot est configuré."
    }
    else{
        $obj.isVulnState = $true
        $obj.message = "Non-conformité : Le Secure Boot n'est pas configuré."
    }
} 
catch {
    $obj.isVulnState = $true
    $obj.message = "Non-conformité : Le Secure Boot n'est pas configuré."
}
$obj.state = "DONE"
return $obj
