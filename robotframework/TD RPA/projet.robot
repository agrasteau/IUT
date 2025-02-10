*** Settings ***
Library         SeleniumLibrary
Library    RPA.PDF
Library    OperatingSystem
Library    Collections

*** Variables ***
${LoginUrl}     https://yopmail.com/fr/
${BROWSER}      ff
${PDFs_DOWNLOADED}     ../download/
${PDFs_ACCEPTED}    ../accepter/
${PDFs_REFUSED}     ../refuser/
@{SEARCH_KEYWORDS}     JAVA        BDD
*** Test Cases ***
Send interview mail to BU and sorting CVs
    CV sorter
    Send mail
# Déplacer les fichiers PDF
#      @{PDFs}=    List Directory    ${PDFs_DOWNLOADED}    *.pdf
#     FOR    ${file1}    IN    @{PDFs}
#     ${match}=    Ouvrir et lire le PDF    ${PDFs_DOWNLOADED}${file1}
#         Close PDF
#         IF    ${match}
#             Move File    ${PDFs_DOWNLOADED}${file1}    ${PDFs_ACCEPTED}${file1}
        
#         ELSE    
#             Move File    ${PDFs_DOWNLOADED}${file1}    ${PDFs_REFUSED}${file1}
#         END
#     END
*** Keywords ***

Send mail
    Open BROWSER    ${LoginUrl}     ${BROWSER}
    Wait Until Page Contains    Publicités et contenu personnalisés, mesure de performance des publicités et du contenu, études d’audience et développement de services    timeout=10s
    Click Element   class=fc-button-label
    Click Element   class=ycptinput
    Input Text      class=ycptinput     test@univ-lemans.fr
    Click Element   //*[@class="material-icons-outlined f36"]
    Click Element   id=newmail
    Wait Until Page Contains    Nouveau message     timeout=10s
    Select frame     name=ifmail
    Input Text    //*[@id="msgto"]    test@YOPmail.com
    Input Text    //*[@id="msgsubject"]     interview with a new applier for your buisness unit
    Input Text    //*[@id="msgbody"]     This applier has sat the HR test here is his profiles would you like to check it and invite him for an interview please    Best Regards your fav HR team collaborator    
    Click Element   id=msgsend
    #Wait 10 sec to make sur the mail sent popups is getting viewed by the user
    sleep   10s
    Close BROWSER

    

CV sorter
    #load all files
    ${pdf_files}=    List Files In Directory    ${PDFs_DOWNLOADED}    pattern=*.pdf    absolute=false 
    
    #browse each file and move it to the good folder if it satisfy the searched keywords
    FOR    ${pdf_file}    IN    @{pdf_files}
        ${texte_pdf}=    Get Text From PDF    ${PDFs_DOWNLOADED}/${pdf_file}
        ${texte_pdf}=     Convert To String       ${texte_pdf}
        Close PDF
        
        FOR    ${keyword}    IN    @{SEARCH_KEYWORDS}
            ${status}=    Run Keyword And Return Status    Should Contain    ${texte_pdf}    ${keyword}
            ${exists}=    Run Keyword And Return Status    File Should Exist    ${PDFs_DOWNLOADED}/${pdf_file}
            Run Keyword If    ${status} == ${exists} ==True       Move File    ${PDFs_DOWNLOADED}/${pdf_file}    ${PDFs_ACCEPTED}${/}${pdf_file}
        END
    END
    
    ${remaining_pdf_files}=    List Files In Directory    ${PDFs_DOWNLOADED}    pattern=*.pdf    absolute=false
    #place to the rejected folder all CVs that havent statisfied at least on of the keywords searched
    FOR    ${pdf_file}    IN    @{remaining_pdf_files}
        Move File    ${PDFs_DOWNLOADED}/${pdf_file}    ${PDFs_REFUSED}${/}${pdf_file}
    END