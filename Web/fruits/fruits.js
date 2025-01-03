// script.js

// Création d'un tableau de fruits
var fruits = ["Pomme", "Banane", "Orange", "Fraise", "Kiwi"];

// Affichage du tableau dans la console
console.log("Tableau de fruits :", fruits);

// Affichage du tableau sous forme de liste HTML
var fruitListElement = document.getElementById("fruit");

for (var i = 0; i < fruits.length; i++) {
    var listItem = document.createElement("li");
    listItem.textContent = fruits[i];
    fruitListElement.appendChild(listItem);
}
