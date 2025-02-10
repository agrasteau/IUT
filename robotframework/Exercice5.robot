*** Settings ***
Library         SeleniumLibrary
Library         FakerLibrary
Library         CSVLibrary
Library         Collections
Library         OperatingSystem
Suite Teardown  Close Browser
Test Template  acces site web
Test Setup  ouvrir navigateur


*** Variables ***
${URL}          https://demowebshop.tricentis.com/

*** Test Cases ***   USERNAME    PASSWORD
Test récupération des données
    ouvrir navigateur

*** Keywords ***
ouvrir navigateur
    Open BROWSER    ${URL}
    Maximize Browser Window
