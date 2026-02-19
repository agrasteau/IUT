param($obj)
######################## CHECK ISADMIN ########################
$user      = [System.Security.Principal.WindowsIdentity]::GetCurrent()
$userclaim = [System.Security.Principal.WindowsPrincipal]::new($user)
$isadmin = $userclaim.IsInRole("administrateurs") -or $userclaim.IsInRole("administrators") 
if(!$isadmin){
    $obj.state   = "SKIP"
    $obj.Message = "SKIPPED : Require Admin Rights"
    return $obj
}
###################### FIN CHECK ISADMIN ######################

try{
    if($env:firmware_type -eq "UEFI"){
        $obj.isVulnState = $false
        $obj.message = "Conformité respectée : Le mode BIOS est configuré en mode UEFI."
    }
    else{
        $obj.isVulnState = $true
        $obj.message = "Non-conformité : Le mode BIOS n'est pas configuré en mode UEFI."
    }
} 
catch {
    $obj.isVulnState = $true
    $obj.message = "Non-conformité : Le mode BIOS n'est pas configuré en mode UEFI."
}
$obj.state = "DONE"
return $obj

