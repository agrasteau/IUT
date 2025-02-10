*** Settings ***
Library         SeleniumLibrary
Library         RPA.PDF
Library         OperatingSystem

*** Variables ***
${PDFs_DOWNLOADED}     C:\Users/etu/Downloads/download/
${PDFs_ACCEPTED}    C:\Users/etu/Downloads/accepter/
${PDFs_REFUSED}     C:\Users/etu/Downloads/refuser/

*** Test Cases ***
Déplacer les fichiers PDF
     @{PDFs}=    List Directory    ${PDFs_DOWNLOADED}    *.pdf
    FOR    ${file1}    IN    @{PDFs}
    ${match}=    Ouvrir et lire le PDF    ${PDFs_DOWNLOADED}${file1}
        Close PDF
        IF    ${match}
            Move File    ${PDFs_DOWNLOADED}${file1}    ${PDFs_ACCEPTED}${file1}
        
        ELSE    
            Move File    ${PDFs_DOWNLOADED}${file1}    ${PDFs_REFUSED}${file1}
        END
    END
*** Keywords ***
Ouvrir et lire le PDF
    [Arguments]    ${file1}
    ${file_content}    Get Text From Pdf    ${file1}
    ${text}=    Convert To String    ${file_content}*
    Log To Console      ${text}
    Log To Console      pathing throught 
    ${match}=    Run Keyword And Return Status    Should Contain    ${text}    Oubahssi
    [Return]     ${match}