*** Settings ***

Library    RPA.PDF
Library    OperatingSystem
Library    Collections

*** Variables ***
${PDFs_DOWNLOADED}     C:\Users/etu/Downloads/download/
${PDFs_ACCEPTED}    C:\Users/etu/Downloads/accepter/
${PDFs_REFUSED}     C:\Users/etu/Downloads/refuser/
@{SEARCH_TERMS}     JAVA        BDD
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
    ${text}=    Convert To String    ${file_content}
    ${match}=    Run Keyword And Return Status    Should Contain    ${text}    Oubahssi
    [Return]     ${match}
Tri des CV
    ${pdf_files}=    List Files In Directory    ${PDFs_DOWNLOADED}    pattern=*.pdf    absolute=false

    FOR    ${pdf_file}    IN    @{pdf_files}
        ${texte_pdf}=    Get Text From PDF    ${PDFs_DOWNLOADED}/${pdf_file}
        ${texte_pdf}=     Convert To String       ${texte_pdf}
        Close PDF
        # Boucle déplaçant tous les fichiers correspondants
        FOR    ${term}    IN    @{SEARCH_TERMS}
            ${status}=    Run Keyword And Return Status    Should Contain    ${texte_pdf}    ${term}
            ${exists}=    Run Keyword And Return Status    File Should Exist    ${PDFs_DOWNLOADED}/${pdf_file}
            Run Keyword If    ${status} == ${exists} ==True       Move File    ${PDFs_DOWNLOADED}/${pdf_file}    ${PDFs_ACCEPTED}${/}${pdf_file}
        END
    END
    
    ${remaining_pdf_files}=    List Files In Directory    ${PDFs_DOWNLOADED}    pattern=*.pdf    absolute=false
    # Boucle pour déplacer tous les fichiers n'ayant pas passé la validation
    FOR    ${pdf_file}    IN    @{remaining_pdf_files}
        Move File    ${PDFs_DOWNLOADED}/${pdf_file}    ${PDFs_REFUSED}${/}${pdf_file}
    END