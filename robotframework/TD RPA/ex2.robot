*** Settings ***
Library         SeleniumLibrary
Library         RPA.PDF
Library         OperatingSystem

*** Variables ***
${PDF_FILE_PATH}    C:\Users/etu/Downloads/Cours1AutomatisationTestsBUT20242025.pdf
${PDF_FILE_PATH2}    C:\Users/etu/Downloads/export  

***Test Cases*** 
Move pdf file
    Move File   ${PDF_FILE_PATH}    ${PDF_FILE_PATH2}

