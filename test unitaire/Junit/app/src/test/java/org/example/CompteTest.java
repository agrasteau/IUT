package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CompteTest {

    private Compte compteEmetteur;
    private Compte compteRecepteur;
    private Compte compte1;

    @BeforeEach
    void setUp() {
        Titulaire titulaire1 = new Titulaire("Doe", "John", "123 rue Exemple");
        Titulaire titulaire2 = new Titulaire("Smith", "Jane", "456 avenue Exemple");

        compteEmetteur = new Compte(1, titulaire1, 3000.0, 800.0, 1000.0);
        compteRecepteur = new Compte(2, titulaire2, 2000.0, 800.0, 1000.0);
        compte1 = new Compte(3, titulaire2, 5000.0, 800.0, 1000.0);
    }

    @Test
    @DisplayName("Test du virement �chouant en raison du d�passement du d�bit maximal autoris�")
    void testEffectuerVirementAvecException() {
        // Given : un montant d�passant le d�bit maximal autoris�
        double montantNonAutorise = 2500.0;

        // When & Then : s'attendre � une exception IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> 
            compteEmetteur.effectuerVirement(compteRecepteur, montantNonAutorise),
            "Un virement d�passant le d�bit maximal autoris� aurait d� lever une exception."
        );
    }

    @ParameterizedTest
    @CsvSource({
        "100.0, 2900.0, 2100.0",   // Virement autoris�
        "500.0, 2500.0, 2500.0",   // Virement autoris�
        "999.99, 2000.01, 2999.99" // Virement limite autoris�
    })
    @DisplayName("Test des virements autoris�s entre deux comptes")
    void testEffectuerVirementAutorise(double montant, double soldeEmetteurApres, double soldeRecepteurApres) {
        // Given : des comptes initiaux et un montant autoris�
        double soldeEmetteurInitial = compteEmetteur.getSolde();
        double soldeRecepteurInitial = compteRecepteur.getSolde();

        // When : effectuer le virement
        compteEmetteur.effectuerVirement(compteRecepteur, montant);

        // Then : v�rifier les soldes apr�s le virement
        assertEquals(soldeEmetteurInitial - montant, compteEmetteur.getSolde(), "Le solde de l'�metteur est incorrect apr�s le virement.");
        assertEquals(soldeRecepteurInitial + montant, compteRecepteur.getSolde(), "Le solde du r�cepteur est incorrect apr�s le virement.");
        assertEquals(soldeEmetteurApres, compteEmetteur.getSolde(), "Le solde final de l'�metteur est incorrect.");
        assertEquals(soldeRecepteurApres, compteRecepteur.getSolde(), "Le solde final du r�cepteur est incorrect.");
    }
    
    @Test
    @DisplayName("Test de la m�thode toString() pour un compte")
    void testToString() {
        // Given : un compte d�j� initialis� dans le setUp()
        String expected = "Compte{numero=1, titulaire=John Doe (123 rue Exemple), solde=3000.0, decouvertMaxAutorise=800.0, debitMaxAutorise=1000.0}";

        // When : appel de la m�thode toString() sur le compte �metteur
        String result = compteEmetteur.toString();
        // Then : v�rifier que la cha�ne g�n�r�e par toString() est correcte
        assertEquals(expected, result, "La m�thode toString() ne renvoie pas la cha�ne attendue.");
    }
    @Test
    @DisplayName("Test de la r�cup�ration du d�couvert maximal autoris� pour un compte")
    void testGetDecouvertMaxAutorise() {
        // Given : un compte d�j� initialis� dans le setUp()
        double expected = 800.0;

        // When : appel de la m�thode getDecouvertMaxAutorise()
        double resultEmetteur = compteEmetteur.getDecouvertMaxAutorise();
        double resultRecepteur = compteRecepteur.getDecouvertMaxAutorise();
        double resultCompte1 = compte1.getDecouvertMaxAutorise();

        // Then : v�rifier que la valeur retourn�e est correcte pour chaque compte
        assertEquals(expected, resultEmetteur, "Le d�couvert maximal autoris� du compte �metteur est incorrect.");
        assertEquals(expected, resultRecepteur, "Le d�couvert maximal autoris� du compte r�cepteur est incorrect.");
        assertEquals(expected, resultCompte1, "Le d�couvert maximal autoris� du compte 1 est incorrect.");
    }

    @Test
    @DisplayName("Test de la r�cup�ration du d�bit maximal autoris� pour un compte")
    void testGetDebitMaxAutorise() {
        // Given : un compte d�j� initialis� dans le setUp()
        double expected = 1000.0;

        // When : appel de la m�thode getDebitMaxAutorise()
        double resultEmetteur = compteEmetteur.getDebitMaxAutorise();
        double resultRecepteur = compteRecepteur.getDebitMaxAutorise();
        double resultCompte1 = compte1.getDebitMaxAutorise();

        // Then : v�rifier que la valeur retourn�e est correcte pour chaque compte
        assertEquals(expected, resultEmetteur, "Le d�bit maximal autoris� du compte �metteur est incorrect.");
        assertEquals(expected, resultRecepteur, "Le d�bit maximal autoris� du compte r�cepteur est incorrect.");
        assertEquals(expected, resultCompte1, "Le d�bit maximal autoris� du compte 1 est incorrect.");
    }
    
    @Test
    @DisplayName("Test de la r�cup�ration du num�ro de compte")
    void testGetNumero() {
        // Given : un compte d�j� initialis� dans le setUp()
        int expectedEmetteur = 1;
        int expectedRecepteur = 2;
        int expectedCompte1 = 3;

        // When : appel de la m�thode getNumero()
        int resultEmetteur = compteEmetteur.getNumero();
        int resultRecepteur = compteRecepteur.getNumero();
        int resultCompte1 = compte1.getNumero();

        // Then : v�rifier que le num�ro retourn� est correct pour chaque compte
        assertEquals(expectedEmetteur, resultEmetteur, "Le num�ro du compte �metteur est incorrect.");
        assertEquals(expectedRecepteur, resultRecepteur, "Le num�ro du compte r�cepteur est incorrect.");
        assertEquals(expectedCompte1, resultCompte1, "Le num�ro du compte 1 est incorrect.");
    }

    @Test
    @DisplayName("Test de la r�cup�ration du titulaire du compte")
    void testGetTitulaire() {
        // Given : un compte d�j� initialis� dans le setUp()
        Titulaire expectedEmetteur = new Titulaire("Doe", "John", "123 rue Exemple");
        Titulaire expectedRecepteur = new Titulaire("Smith", "Jane", "456 avenue Exemple");

        // When : appel de la m�thode getTitulaire()
        Titulaire resultEmetteur = compteEmetteur.getTitulaire();
        Titulaire resultRecepteur = compteRecepteur.getTitulaire();
        Titulaire resultCompte1 = compte1.getTitulaire();

        // Then : comparer les attributs au lieu des objets eux-m�mes
        assertEquals(expectedEmetteur.getNom(), resultEmetteur.getNom(), "Le nom du titulaire du compte �metteur est incorrect.");
        assertEquals(expectedEmetteur.getPrenom(), resultEmetteur.getPrenom(), "Le pr�nom du titulaire du compte �metteur est incorrect.");
        assertEquals(expectedEmetteur.getAdresse(), resultEmetteur.getAdresse(), "L'adresse du titulaire du compte �metteur est incorrect.");

        assertEquals(expectedRecepteur.getNom(), resultRecepteur.getNom(), "Le nom du titulaire du compte r�cepteur est incorrect.");
        assertEquals(expectedRecepteur.getPrenom(), resultRecepteur.getPrenom(), "Le pr�nom du titulaire du compte r�cepteur est incorrect.");
        assertEquals(expectedRecepteur.getAdresse(), resultRecepteur.getAdresse(), "L'adresse du titulaire du compte r�cepteur est incorrect.");

        assertEquals(expectedRecepteur.getNom(), resultCompte1.getNom(), "Le nom du titulaire du compte 1 est incorrect.");
        assertEquals(expectedRecepteur.getPrenom(), resultCompte1.getPrenom(), "Le pr�nom du titulaire du compte 1 est incorrect.");
        assertEquals(expectedRecepteur.getAdresse(), resultCompte1.getAdresse(), "L'adresse du titulaire du compte 1 est incorrect.");
    }
    
    @Test
    @DisplayName("Test de l'exception lors d'un virement vers un compte null")
    void testEffectuerVirement_CompteNull() {
        // Given : un compte �metteur initialis� dans setUp()

        // When & Then : V�rifier que l'exception est bien lev�e
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> compteEmetteur.effectuerVirement(null, 500.0),
            "Un virement vers un compte null devrait lever une IllegalArgumentException."
        );

        // V�rifier le message de l'exception
        assertEquals("Le compte destinataire ne peut pas �tre null.", exception.getMessage(), 
            "Le message d'exception ne correspond pas � l'attendu.");
    }
    @Test
    @DisplayName("Test de l'exception lors du cr�dit d'un montant n�gatif ou nul")
    void testCrediter_ExceptionMontantNegatifOuNul() {
        // Given : un compte initialis� dans setUp()

        // When & Then : V�rifier qu'une exception est lev�e pour un montant n�gatif
        IllegalArgumentException exceptionNegatif = assertThrows(
            IllegalArgumentException.class,
            () -> compteEmetteur.crediter(-100.0),
            "Cr�diter un montant n�gatif devrait lever une IllegalArgumentException."
        );
        assertEquals("Le montant � cr�diter doit �tre positif.", exceptionNegatif.getMessage(), 
            "Le message d'exception ne correspond pas � l'attendu.");

        // When & Then : V�rifier qu'une exception est lev�e pour un montant nul
        IllegalArgumentException exceptionNul = assertThrows(
            IllegalArgumentException.class,
            () -> compteEmetteur.crediter(0.0),
            "Cr�diter un montant nul devrait lever une IllegalArgumentException."
        );
        assertEquals("Le montant � cr�diter doit �tre positif.", exceptionNul.getMessage(), 
            "Le message d'exception ne correspond pas � l'attendu.");
    }
    @Test
    @DisplayName("Test de l'exception lors du d�bit d'un montant n�gatif ou nul")
    void testDebiter_ExceptionMontantNegatifOuNul() {
        // Given : un compte initialis� dans setUp()

        // When & Then : V�rifier qu'une exception est lev�e pour un montant n�gatif
        IllegalArgumentException exceptionNegatif = assertThrows(
            IllegalArgumentException.class,
            () -> compteEmetteur.debiter(-100.0),
            "D�biter un montant n�gatif devrait lever une IllegalArgumentException."
        );
        assertEquals("Le montant � d�biter doit �tre positif.", exceptionNegatif.getMessage(), 
            "Le message d'exception ne correspond pas � l'attendu.");

        // When & Then : V�rifier qu'une exception est lev�e pour un montant nul
        IllegalArgumentException exceptionNul = assertThrows(
            IllegalArgumentException.class,
            () -> compteEmetteur.debiter(0.0),
            "D�biter un montant nul devrait lever une IllegalArgumentException."
        );
        assertEquals("Le montant � d�biter doit �tre positif.", exceptionNul.getMessage(), 
            "Le message d'exception ne correspond pas � l'attendu.");
    }

    @Test
    @DisplayName("Test de l'exception lors du d�passement du d�bit maximal autoris�")
    void testDebiter_ExceptionDepassementDebitMax() {
        // Given : un compte avec un d�bit maximal autoris� de 1000.0 (d�fini dans setUp())

        // When & Then : V�rifier qu'une exception est lev�e pour un montant d�passant la limite
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> compteEmetteur.debiter(1500.0),
            "D�biter un montant sup�rieur au d�bit maximal autoris� devrait lever une IllegalArgumentException."
        );
        assertEquals("Le montant d�passe le d�bit maximal autoris�.", exception.getMessage(), 
            "Le message d'exception ne correspond pas � l'attendu.");
    }
    @Test
    @DisplayName("Test de l'exception lors du d�passement du d�couvert maximal autoris�")
    void testDebiter_ExceptionDepassementDecouvert() {
        // Given : un compte avec un solde de 3000.0, un d�couvert max de 800.0 et un d�bit max de 1000.0
        double montantADebiter = 3800.0; // Ce montant ne d�passe pas le d�bit max (1000.0) par transaction,
                                         // mais rendra le solde inf�rieur � -800.0 (d�couvert max)

        // When & Then : V�rifier qu'une exception est lev�e
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> {
                compteEmetteur.debiter(900.0); // OK
                compteEmetteur.debiter(900.0); // OK
                compteEmetteur.debiter(900.0); // OK
                compteEmetteur.debiter(900.0); // OK
                compteEmetteur.debiter(900.0); // OK
                compteEmetteur.debiter(200.0); // D�passement du d�couvert -> Exception attendue
            },
            "D�biter un montant qui d�passe le d�couvert maximal autoris� devrait lever une IllegalArgumentException."
        );

        assertEquals(
            "Le solde ne peut pas d�passer le d�couvert maximal autoris�.", 
            exception.getMessage(), 
            "Le message d'exception ne correspond pas � l'attendu."
        );
    }
    
    @Test
    @DisplayName("Test de l'exception lors de la cr�ation d'un compte avec un titulaire null")
    void testConstructeur_ExceptionTitulaireNull() {
        // Given : un titulaire null

        // When & Then : V�rifier qu'une exception est lev�e
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Compte(4, null, 1000.0, 500.0, 300.0),
            "Cr�er un compte avec un titulaire null devrait lever une IllegalArgumentException."
        );

        assertEquals(
            "Le titulaire ne peut pas �tre null.", 
            exception.getMessage(), 
            "Le message d'exception ne correspond pas � l'attendu."
        );
    }
    
    @Test
    @DisplayName("Test de la m�thode estADecouvert() pour un compte en d�couvert apr�s plusieurs d�bits")
    void testEstADecouvert_CompteADecouvert() {
        // Given : un compte avec un solde initial positif (3000.0) et un d�couvert autoris� de 800.0
        // Effectuer plusieurs d�bits pour atteindre un solde n�gatif (d�couvert)

        // D�biter par �tapes
        compteEmetteur.debiter(1000.0); // Le solde devient 3000 - 1000 = 2000
        compteEmetteur.debiter(1000.0); // Le solde devient 2000 - 1000 = 1000
        compteEmetteur.debiter(1000.0); // Le solde devient 1000 - 1000 = 0
        compteEmetteur.debiter(500.0); // Le solde devient 0 - 500 = -500 (d�couvert d�pass�)

        // When : appel de la m�thode estADecouvert() sur le compte �metteur
        boolean result = compteEmetteur.estADecouvert();

        // Then : v�rifier que le compte est bien en d�couvert
        assertTrue(result, "Le compte devrait �tre en d�couvert apr�s plusieurs d�bits.");
    }

    @Test
    @DisplayName("Test de la m�thode estADecouvert() pour un compte non d�couvert")
    void testEstADecouvert_CompteNonADecouvert() {
        // Given : un compte avec un solde positif
        double soldeInitial = compteEmetteur.getSolde(); // Solde initial est positif (3000.0)

        // When : appel de la m�thode estADecouvert() sur le compte �metteur
        boolean result = compteEmetteur.estADecouvert();

        // Then : v�rifier que le compte n'est pas en d�couvert
        assertFalse(result, "Le compte ne devrait pas �tre en d�couvert.");
    }
    
    @Test
    @DisplayName("Test du constructeur d'un compte avec un solde null")
    void testConstructeurAvecSoldeNull() {
        // Given : un titulaire valide et un solde null
        Titulaire titulaire = new Titulaire("Doe", "John", "123 rue Exemple");
        Double soldeNull = null;  // Solde pass� en null

        // When : cr�ation d'un compte avec un solde null
        Compte compte = new Compte(1, titulaire, soldeNull, 800.0, 1000.0);

        // Then : v�rifier que le solde du compte est bien initialis� � 0.0
        assertEquals(0.0, compte.getSolde(), "Le solde du compte devrait �tre initialis� � 0.0 lorsque le solde est null.");
    }
    
    
}
