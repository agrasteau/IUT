While($true){
    $signal = $null
    $wifi = netsh wlan show interfaces
    if((($wifi | select-string "SSID") -split ":").count -eq 0){
        write-host "Pas de carte ou de connexion WIFI"
        break
    }
    else{
        write-host "Connexion : $((($wifi | select-string "Nom") -split ":")[1].trim())" -ForegroundColor Yellow
        write-host "Carte réseau wifi : $((($wifi | select-string "description") -split ":")[1].trim())"
        write-host "Adresse mac : $(((($wifi | select-string "adresse physique") -split ":")[1..7] -join ":").Trim())"
        write-host "SSID : $((($wifi | select-string "SSID") -split ":")[1].trim())"
        $signal=((($wifi | select-string "signal") -split ":")[1]).trim()

        write-host "force du signal"
        write-host "$signal : " -NoNewline -f Cyan
        [int]$x=($signal.Split("%"))[0]
        $force=$x/100 * 20
        $faible=(100 - $x)/100 * 20

        write-host $("$([char]9604)" * $force) -NoNewline -ForegroundColor Green
        write-host $("$([char]9604)" * $faible) -ForegroundColor red
        start-sleep -Seconds 5
        CLS
    }
}