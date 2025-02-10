*** Settings ***
Library         SeleniumLibrary
Library         RPA.PDF
Library     Opera


*** Variables ***
${PDF_FILE_PATH}    C:\Users/etu/Downloads/Cours1AutomatisationTestsBUT20242025.pdf  
${NB_PAGE}  0

***Test Cases*** 
Show pdf page12

     Open PDF    ${PDF_FILE_PATH}
    ${NB_PAGE} =    Get Number Of Pages
    ${NB_PAGE} =    Convert To String  ${NB_PAGE}
    log     Le fichier pdf a ${NB_PAGE} pages.
    log to console       Le fichier pdf a ${NB_PAGE} pages.


