<?php
if(isset($_GET['n'])) {
    $n = $_GET['n'];
    $somme=sommeCarres($n);
    }
function sommeCarres($n) {
    $somme = 0;
    for ($i = 1; $i <= $n; $i++) {
        $somme=0;
        $somme += $i * $i;
    }
    return $somme;
    
    
}
echo "La somme des carrés des nombres de 1 à $n est $somme.";
?>