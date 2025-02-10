*** Settings ***
Library         SeleniumLibrary
Library         FakerLibrary
Library         CSVLibrary
Library         Collections
Library         OperatingSystem
Suite Teardown  Close Browser

#Faker ne fonctionnant pas sur les machines de l'iut il ne sera pas utilisé il faut donc modifier manuelement la variable ${mail} a chaque exécution

*** Variables ***
${URL}          https://demowebshop.tricentis.com/
${Id}           alexandre
${pwd}          azertyuiop123456798
${FirstName}    test
${LastName}     test
${FirstName1}    test1
${LastName1}     test1
${mail}         testmailejkhghr180@skrak.com 

*** Test Cases ***   USERNAME    PASSWORD
OpenBrower
    Open BROWSER    ${URL}
    Maximize Browser Window

navigate to register page
    Click Element   class=ico-register
    Wait Until Page Contains    Your Personal Details      timeout=10s

input Personal informations
    Click Element   id=gender-male
    Input Text      id=FirstName    ${FirstName}
    Input Text      id=LastName     ${LastName}
    Input Text      id=Email        ${mail}
    Input Text      id=Password     ${pwd}
    Input Text      id=ConfirmPassword     ${pwd}
    Click Element   id=register-button
    Wait Until Page Contains     Your registration completed     timeout=25s

Updating Personal informations
    Click Element   class=account
    Click Element   id=gender-female
    Input Text      id=FirstName    ${FirstName1}
    Input Text      id=LastName     ${LastName1}
    
Logout
    Click Element   class=ico-logout
    Wait Until Page Contains     Log in     timeout=10s

Login
    Click Element   class=ico-login 
    Wait Until Page Contains    Returning Custome   timeout=10s
    Input Text      id=Email        ${mail}
    Input Text      id=Password     ${pwd}
    Click Element   xpath=//input[@class='button-1 login-button']

Log Out after logged in
    Wait Until Page Contains     Log out     timeout=30s
    Click Element   class=ico-logout
    Wait Until Page Contains    Log in     timeout=30s

Forgot password
    Click Element   class=ico-login 
    Click Element   link=Forgot password?
    Input Text      id=Email        ${mail}
    Click Element   xpath=//input[@class='button-1 password-recovery-button']
    Wait Until Page Contains    Email with instructions has been sent to you.   timeout=10s


*** Keywords ***