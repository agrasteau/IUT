*** Settings ***
Library         SeleniumLibrary

*** Variables ***
${LoginUrl}     https://www.cdiscount.com
${BROWSER}      ff
${SearchText}   Voiture - BBURAGO - BMW M3 (E30) 
${ArticleText}  Voiture - BBURAGO - BMW M3 (E30) 

***Test Cases***
Test d'ouverture du site web cdiscount
    Open BROWSER    ${LoginUrl}     ${BROWSER}

Test Tout accepter
    Set Selenium Speed  1
    #Click Element   //div[text()="Tout accepter"]
    # Attendre que la barre de recherche soit visible
    Wait Until Element Is Visible   //input[@type='search' and @name='search']    timeout=10s

    # Entrer le texte dans la barre de recherche
    Input Text      //input[@type='search' and @name='search']    ${SearchText}

    # Cliquer sur l'icône de recherche
    Click Element   //button[contains(@class,'js-search__icon')]

    # Attendre que les résultats s'affichent
    Wait Until Page Contains    ${SearchText}

    # Vérifier que le produit est présent dans les résultats
    Wait Until Page Contains    Voiture - BBURAGO - BMW M3 (E30) 

test fermeture
    Close BROWSER