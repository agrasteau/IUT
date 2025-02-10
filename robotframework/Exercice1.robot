*** Settings ***
Library         SeleniumLibrary

*** Variables ***
${LoginUrl}     https://www.google.com
${BROWSER}      ff
${SearchText}   Le Mans Université
${ENTLinkText}  ENT Le Mans Université
${StudentAccountLinkText}   Activation compte étudiant

***Test Cases***
Test d'ouverture du site web le mans univ
    Open BROWSER    ${LoginUrl}     ${BROWSER}

Test Tout accepter
    Set Selenium Speed  1
    Click Element   //div[text()="Tout accepter"]

Chercher le site de l'université Le Mans
    Input Text      name=q    ${SearchText}
    Press Keys      name=q      RETURN    
    Wait Until Page Contains    ${SearchText}
    Click Link      ENT   
    Click Element   class=cookiebanner-accept
    Click Element   id=header-a1

test fermeture
    Close BROWSER