function modifierPropriete(propriete, valeur) {
    const elementExemple = document.getElementById('titre-exemple');
    switch (propriete) {
        case 'couleur-fond':
            elementExemple.style.backgroundColor = `rgb(${valeur}, 0, 0)`;
            break;
        case 'padding':
            elementExemple.style.padding = `${valeur}px`;
            break;
        case 'hauteur':
            elementExemple.style.height = `${valeur}px`;
            break;
        case 'largeur':
            elementExemple.style.width = `${valeur}px`;
            break;
        case 'arrondi':
            elementExemple.style.borderRadius = `${valeur}px`;
            break;
        case 'rotation':
            elementExemple.style.transform = `rotate(${valeur}deg)`;
            break;
    }

    const titreExemple = document.getElementById('titre-exemple');
    titreExemple.style[propriete] = valeur;
}
