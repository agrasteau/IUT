<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Age Verification</title>
</head>
<body>
    <h1>Exercice du fizzbuzz en php</h1>
    <?php
    for ($i = 1; $i <= 100; $i++) {
        
        if ($i % 15==0) {
            echo "fizzbuzz";
        }
        else if ($i % 3==0) {
            echo "fizz";
        } 
        else if ($i % 5==0){
            echo "buzz";
        }else {
            echo $i ;
        }
        echo  "<br>" ;
    }
    ?>
</body>
</html>