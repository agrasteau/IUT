<?php
// Génère un nombre aléatoire entre 1 et 100
$nombreSecret = rand(1, 100);
$nombreTentatives = 10;
$tentativesRestantes = $nombreTentatives;
$numbertofind = $nombreSecret;

        echo 'je suis rentrer dedans';
        $tentativesRestantes--;
        $devine = (int)$_POST['$devine'];
        if ($devine === $numbertofind) {
            echo "Bravo ! Vous avez deviné le nombre secret : $nombreSecret.";
            exit;
        } elseif ($devine < $numbertofind) {
            echo "Le nombre secret est plus grand. Il vous reste $tentativesRestantes tentatives.";
        } else {
            echo "Le nombre secret est plus petit. Il vous reste $tentativesRestantes tentatives.";
        }

        if ($tentativesRestantes === 0) {
            echo "Désolé, vous avez épuisé toutes vos tentatives. Le nombre secret était : $nombreSecret.";
            exit;
        }
    echo 'il C rien passer chef';


?>
<!DOCTYPE html>
<html>
<head>
    <title>Jeu de devinette en PHP</title>
</head>
<body>
    <h1>Jeu de devinette</h1>
    <p>Devinez un nombre entre 1 et 100.</p>
    <form method="post">
        <input type="number" name="devine" min="1" max="100" required>
        <input type="submit" value="Devinez">
        <input type="hidden" id="numbertofind" name="numbertofind" />
        <!-- <input type="hidden" id="test" name="test" value="" /> -->
    </form>
</body>
</html>
