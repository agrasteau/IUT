<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>age check</title>
</head>
<body>
<body>
    <h1>Age Verification</h1>

    <?php
    if(isset($_GET['age'])) {
        $age = $_GET['age'];

        if (is_numeric($age)) {
            if ($age < 18) {
                echo "<p>You are a minor.</p>";
            } else {
                echo "<p>You are a major.</p>";
            }
        } else {
            echo "<p>Please enter a valid age (a number).</p>";
        }
    } else {
        echo "<p>No age provided in the URL.</p>";
    }
    ?>
</body>
</html>