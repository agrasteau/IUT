
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>palydrome tester</title>
</head>
<body>
    <h1>playdrome tester</h1>
    <?php
    if(isset($_GET['mot'])) {
    $mot = $_GET['mot'];
    palindrome($mot);
    }
    ?>
    <?php  
    function palindrome($mot){
    include "reverse.php";
         $mot2 = reverseWord($mot);
         if ($mot == $mot2){
        echo "le mot " , $mot , " est un palindrome";
        return true;
    }
    else {
        echo "le mot " , $mot , " n'est pas un palindrome";
        return false;
    }
    
}
     ?>
</body>
</html>



