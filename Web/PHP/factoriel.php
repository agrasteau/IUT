<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>factorielle</title>
</head>
<body>
    <h1>factorielle</h1>
    <?php
     $resultat=1;
     if(isset($_GET['nombre'])) {
         $nombre = $_GET['nombre'];
 
         if (is_numeric($nombre)) {
           $resultat = factorielle($nombre);
            echo $resultat;
         } else {
             echo "<p>Please enter a valid number (a number).</p>";
         }
     } else {
         echo "<p>No number provided in the URL.</p>";
     }
     
    


     function factorielle($n) {
        $nombre = 1;
    
        for ($i = 1; $i <= $n; $i++) {
            $nombre *= $i;
        }
        
        return $nombre;
    }
    ?>
</body>
</html>


