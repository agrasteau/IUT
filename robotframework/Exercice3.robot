*** Settings ***
Library         SeleniumLibrary
Library         DataDriver    ./logins/logins.csv

Suite Teardown  Close Browser
Test Template  acces site web
Test Setup  ouvrir navigateur


*** Variables ***
${URL}          https://www.saucedemo.com/


*** Test Cases ***   USERNAME    PASSWORD
Test récupération des données
    ouvrir navigateur
    acces site web  ${username}    ${password}

*** Keywords ***
ouvrir navigateur
    Open BROWSER    ${URL}     ff
    Maximize Browser Window

acces site web
    [Arguments]    ${username}    ${password}
    Input Text    //*[@id="user-name"]    ${username}
    Input Text    //*[@id="password"]    ${password}
    Click Button    //*[@id="login-button"]
    Wait Until Page Contains    Products
    Sleep   5s
    Close Browser
