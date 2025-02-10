*** Settings ***
Library         SeleniumLibrary

*** Variables ***
${LoginUrl}     https://www.amazon.com
${BROWSER}      ff
${SearchText}   Bburago 1/24 Alloy Supercar for BMW M3 E30 Racing Car Die Cast Collection Vehicles Miniature Scale Model Collectible Gift (Red)
${ArticleText}  Bburago 1/24 Alloy Supercar for BMW M3 E30 Racing Car Die Cast Collection Vehicles Miniature Scale Model Collectible Gift (Red)

***Test Cases***
Test d'ouverture du site web amazon
    Open BROWSER    ${LoginUrl}     ${BROWSER}

Test Tout accepter
    Set Selenium Speed  1
#    TODO checker if pop up appears
#    Click Element   //div[text()="Tout accepter"]
    Click Element   id=twotabsearchtextbox
    Input Text      id=twotabsearchtextbox    ${SearchText}
    Press Keys      id=twotabsearchtextbox      RETURN
    Wait Until Page Contains    ${ArticleText}    timeout=10s
    Click Element   //span[text()='Bburago 1/24 Alloy Supercar for BMW M3 E30 Racing Car Die Cast Collection Vehicles Miniature Scale Model Collectible Gift (Red)']
    Wait Until Page Contains    ${ArticleText}    timeout=10s
    
test fermeture
    Close BROWSER