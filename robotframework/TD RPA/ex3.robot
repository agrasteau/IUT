*** Settings ***
Library         SeleniumLibrary
Library         RPA.PDF
Library         OperatingSystem

*** Variables ***
${PDF_FILE_PATH}    C:\Users/etu/Downloads/export/Cours1AutomatisationTestsBUT20242025.pdf
${META}


***Test Cases*** 
Move pdf file
    Open PDF    ${PDF_FILE_PATH}
    ${META}=    Get PDFINFO    ${PDF_FILE_PATH}
    Log To Console    PDF Metadata: ${META}


