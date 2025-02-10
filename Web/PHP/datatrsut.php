<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DataTrust en php</title>
</head>

<body>
    <?php
    echo ' <form method="POST" action="">' ;
    echo "Nom:"."<br>". '<input type="text" name="Nom">'  ;   
    if (!empty($_POST['Nom'])) {
        $nom = ($_POST['Nom']);
        
        echo $nom . "<br>" ;
        }
    
        echo "<br>";    
        echo "Prenom:" . "<br>" . '<input type="text" name="Prenom">';
    if (!empty($_POST['Prenom'])) {
            $prenom = ($_POST['Prenom']);
            
            echo $prenom . "<br>";
        }

        echo "<br>";
        echo "Mail:" . "<br>" . '  <input type="text" name="Mail">  ';
        if (!empty($_POST['Mail'])) {
            $mail = ($_POST['Mail']);
            echo $mail . "<br>";
        }

        echo "<br>";
        echo "Sujet:" . "<br>".'<input type="text" name="Message">' ;
        if (!empty($_POST['Sujet'])) {
            $sujet = ($_POST['Sujet']);
            echo $sujet . "<br>";
        }

        echo "<br>";
        echo "Message:" . "<br>". '<input type="text" name="Sujet">';
        if (!empty($_POST['Message'])) {
            $message = ($_POST['Message']);
            
            echo $message . "<br>";
        }
            
    ?>


    <form method="POST" action="">
        
        
        




    </form>


</body>