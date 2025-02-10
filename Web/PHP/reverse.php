
    <?php
     $resultat=1;
     if(isset($_GET['mot'])) {
         $mot = $_GET['mot'];
     }
     function reverseWord($word) {
         $wordLength = strlen($word);
         $reversedWord = '';
     
         for ($i = $wordLength - 1; $i >= 0; $i--) {
             $reversedWord .= $word[$i];
         }
     
         return $reversedWord;
     }
     $reversed = reverseWord($mot);
     //echo "The reversed word is: " , $reversed;
     ?>


