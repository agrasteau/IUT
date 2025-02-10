
function isPalindrome(text) {
    var cleanedText = text.toLowerCase().replace(/[^a-zA-Z0-9]/g, '');
    var reversedText = cleanedText.split('').reverse().join('');
    return cleanedText === reversedText;
}

document.getElementById("palindromeForm").addEventListener("submit", function(event) {
    event.preventDefault(); 

    var userInput = document.getElementById("textInput").value;

    var isPalindromeResult = isPalindrome(userInput);

    var resultParagraph = document.getElementById("resultParagraph");
    resultParagraph.textContent = isPalindromeResult ? "C'est un palindrome!" : "Ce n'est pas un palindrome.";
    console.log(isPalindromeResult)
    document.getElementById("textInput").value = "";
});
