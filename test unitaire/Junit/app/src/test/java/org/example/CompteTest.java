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
    @DisplayName("Test du virement échouant en raison du dépassement du débit maximal autorisé")
    void testEffectuerVirementAvecException() {
        // Given : un montant dépassant le débit maximal autorisé
        double montantNonAutorise = 2500.0;

        // When & Then : s'attendre à une exception IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> 
            compteEmetteur.effectuerVirement(compteRecepteur, montantNonAutorise),
            "Un virement dépassant le débit maximal autorisé aurait dû lever une exception."
        );
    }

    @ParameterizedTest
    @CsvSource({
        "100.0, 2900.0, 2100.0",   // Virement autorisé
        "500.0, 2500.0, 2500.0",   // Virement autorisé
        "999.99, 2000.01, 2999.99" // Virement limite autorisé
    })
    @DisplayName("Test des virements autorisés entre deux comptes")
    void testEffectuerVirementAutorise(double montant, double soldeEmetteurApres, double soldeRecepteurApres) {
        // Given : des comptes initiaux et un montant autorisé
        double soldeEmetteurInitial = compteEmetteur.getSolde();
        double soldeRecepteurInitial = compteRecepteur.getSolde();

        // When : effectuer le virement
        compteEmetteur.effectuerVirement(compteRecepteur, montant);

        // Then : vérifier les soldes après le virement
        assertEquals(soldeEmetteurInitial - montant, compteEmetteur.getSolde(), "Le solde de l'émetteur est incorrect après le virement.");
        assertEquals(soldeRecepteurInitial + montant, compteRecepteur.getSolde(), "Le solde du récepteur est incorrect après le virement.");
        assertEquals(soldeEmetteurApres, compteEmetteur.getSolde(), "Le solde final de l'émetteur est incorrect.");
        assertEquals(soldeRecepteurApres, compteRecepteur.getSolde(), "Le solde final du récepteur est incorrect.");
    }
    
    @Test
    @DisplayName("Test de la méthode toString() pour un compte")
    void testToString() {
        // Given : un compte déjà initialisé dans le setUp()
        String expected = "Compte{numero=1, titulaire=John Doe (123 rue Exemple), solde=3000.0, decouvertMaxAutorise=800.0, debitMaxAutorise=1000.0}";

        // When : appel de la méthode toString() sur le compte émetteur
        String result = compteEmetteur.toString();
        // Then : vérifier que la chaîne générée par toString() est correcte
        assertEquals(expected, result, "La méthode toString() ne renvoie pas la chaîne attendue.");
    }
    @Test
    @DisplayName("Test de la récupération du découvert maximal autorisé pour un compte")
    void testGetDecouvertMaxAutorise() {
        // Given : un compte déjà initialisé dans le setUp()
        double expected = 800.0;

        // When : appel de la méthode getDecouvertMaxAutorise()
        double resultEmetteur = compteEmetteur.getDecouvertMaxAutorise();
        double resultRecepteur = compteRecepteur.getDecouvertMaxAutorise();
        double resultCompte1 = compte1.getDecouvertMaxAutorise();

        // Then : vérifier que la valeur retournée est correcte pour chaque compte
        assertEquals(expected, resultEmetteur, "Le découvert maximal autorisé du compte émetteur est incorrect.");
        assertEquals(expected, resultRecepteur, "Le découvert maximal autorisé du compte récepteur est incorrect.");
        assertEquals(expected, resultCompte1, "Le découvert maximal autorisé du compte 1 est incorrect.");
    }

    @Test
    @DisplayName("Test de la récupération du débit maximal autorisé pour un compte")
    void testGetDebitMaxAutorise() {
        // Given : un compte déjà initialisé dans le setUp()
        double expected = 1000.0;

        // When : appel de la méthode getDebitMaxAutorise()
        double resultEmetteur = compteEmetteur.getDebitMaxAutorise();
        double resultRecepteur = compteRecepteur.getDebitMaxAutorise();
        double resultCompte1 = compte1.getDebitMaxAutorise();

        // Then : vérifier que la valeur retournée est correcte pour chaque compte
        assertEquals(expected, resultEmetteur, "Le débit maximal autorisé du compte émetteur est incorrect.");
        assertEquals(expected, resultRecepteur, "Le débit maximal autorisé du compte récepteur est incorrect.");
        assertEquals(expected, resultCompte1, "Le débit maximal autorisé du compte 1 est incorrect.");
    }
    
    @Test
    @DisplayName("Test de la récupération du numéro de compte")
    void testGetNumero() {
        // Given : un compte déjà initialisé dans le setUp()
        int expectedEmetteur = 1;
        int expectedRecepteur = 2;
        int expectedCompte1 = 3;

        // When : appel de la méthode getNumero()
        int resultEmetteur = compteEmetteur.getNumero();
        int resultRecepteur = compteRecepteur.getNumero();
        int resultCompte1 = compte1.getNumero();

        // Then : vérifier que le numéro retourné est correct pour chaque compte
        assertEquals(expectedEmetteur, resultEmetteur, "Le numéro du compte émetteur est incorrect.");
        assertEquals(expectedRecepteur, resultRecepteur, "Le numéro du compte récepteur est incorrect.");
        assertEquals(expectedCompte1, resultCompte1, "Le numéro du compte 1 est incorrect.");
    }

    @Test
    @DisplayName("Test de la récupération du titulaire du compte")
    void testGetTitulaire() {
        // Given : un compte déjà initialisé dans le setUp()
        Titulaire expectedEmetteur = new Titulaire("Doe", "John", "123 rue Exemple");
        Titulaire expectedRecepteur = new Titulaire("Smith", "Jane", "456 avenue Exemple");

        // When : appel de la méthode getTitulaire()
        Titulaire resultEmetteur = compteEmetteur.getTitulaire();
        Titulaire resultRecepteur = compteRecepteur.getTitulaire();
        Titulaire resultCompte1 = compte1.getTitulaire();

        // Then : comparer les attributs au lieu des objets eux-mêmes
        assertEquals(expectedEmetteur.getNom(), resultEmetteur.getNom(), "Le nom du titulaire du compte émetteur est incorrect.");
        assertEquals(expectedEmetteur.getPrenom(), resultEmetteur.getPrenom(), "Le prénom du titulaire du compte émetteur est incorrect.");
        assertEquals(expectedEmetteur.getAdresse(), resultEmetteur.getAdresse(), "L'adresse du titulaire du compte émetteur est incorrect.");

        assertEquals(expectedRecepteur.getNom(), resultRecepteur.getNom(), "Le nom du titulaire du compte récepteur est incorrect.");
        assertEquals(expectedRecepteur.getPrenom(), resultRecepteur.getPrenom(), "Le prénom du titulaire du compte récepteur est incorrect.");
        assertEquals(expectedRecepteur.getAdresse(), resultRecepteur.getAdresse(), "L'adresse du titulaire du compte récepteur est incorrect.");

        assertEquals(expectedRecepteur.getNom(), resultCompte1.getNom(), "Le nom du titulaire du compte 1 est incorrect.");
        assertEquals(expectedRecepteur.getPrenom(), resultCompte1.getPrenom(), "Le prénom du titulaire du compte 1 est incorrect.");
        assertEquals(expectedRecepteur.getAdresse(), resultCompte1.getAdresse(), "L'adresse du titulaire du compte 1 est incorrect.");
    }
    
    @Test
    @DisplayName("Test de l'exception lors d'un virement vers un compte null")
    void testEffectuerVirement_CompteNull() {
        // Given : un compte émetteur initialisé dans setUp()

        // When & Then : Vérifier que l'exception est bien levée
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> compteEmetteur.effectuerVirement(null, 500.0),
            "Un virement vers un compte null devrait lever une IllegalArgumentException."
        );

        // Vérifier le message de l'exception
        assertEquals("Le compte destinataire ne peut pas être null.", exception.getMessage(), 
            "Le message d'exception ne correspond pas à l'attendu.");
    }
    @Test
    @DisplayName("Test de l'exception lors du crédit d'un montant négatif ou nul")
    void testCrediter_ExceptionMontantNegatifOuNul() {
        // Given : un compte initialisé dans setUp()

        // When & Then : Vérifier qu'une exception est levée pour un montant négatif
        IllegalArgumentException exceptionNegatif = assertThrows(
            IllegalArgumentException.class,
            () -> compteEmetteur.crediter(-100.0),
            "Créditer un montant négatif devrait lever une IllegalArgumentException."
        );
        assertEquals("Le montant à créditer doit être positif.", exceptionNegatif.getMessage(), 
            "Le message d'exception ne correspond pas à l'attendu.");

        // When & Then : Vérifier qu'une exception est levée pour un montant nul
        IllegalArgumentException exceptionNul = assertThrows(
            IllegalArgumentException.class,
            () -> compteEmetteur.crediter(0.0),
            "Créditer un montant nul devrait lever une IllegalArgumentException."
        );
        assertEquals("Le montant à créditer doit être positif.", exceptionNul.getMessage(), 
            "Le message d'exception ne correspond pas à l'attendu.");
    }
    @Test
    @DisplayName("Test de l'exception lors du débit d'un montant négatif ou nul")
    void testDebiter_ExceptionMontantNegatifOuNul() {
        // Given : un compte initialisé dans setUp()

        // When & Then : Vérifier qu'une exception est levée pour un montant négatif
        IllegalArgumentException exceptionNegatif = assertThrows(
            IllegalArgumentException.class,
            () -> compteEmetteur.debiter(-100.0),
            "Débiter un montant négatif devrait lever une IllegalArgumentException."
        );
        assertEquals("Le montant à débiter doit être positif.", exceptionNegatif.getMessage(), 
            "Le message d'exception ne correspond pas à l'attendu.");

        // When & Then : Vérifier qu'une exception est levée pour un montant nul
        IllegalArgumentException exceptionNul = assertThrows(
            IllegalArgumentException.class,
            () -> compteEmetteur.debiter(0.0),
            "Débiter un montant nul devrait lever une IllegalArgumentException."
        );
        assertEquals("Le montant à débiter doit être positif.", exceptionNul.getMessage(), 
            "Le message d'exception ne correspond pas à l'attendu.");
    }

    @Test
    @DisplayName("Test de l'exception lors du dépassement du débit maximal autorisé")
    void testDebiter_ExceptionDepassementDebitMax() {
        // Given : un compte avec un débit maximal autorisé de 1000.0 (défini dans setUp())

        // When & Then : Vérifier qu'une exception est levée pour un montant dépassant la limite
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> compteEmetteur.debiter(1500.0),
            "Débiter un montant supérieur au débit maximal autorisé devrait lever une IllegalArgumentException."
        );
        assertEquals("Le montant dépasse le débit maximal autorisé.", exception.getMessage(), 
            "Le message d'exception ne correspond pas à l'attendu.");
    }
    @Test
    @DisplayName("Test de l'exception lors du dépassement du découvert maximal autorisé")
    void testDebiter_ExceptionDepassementDecouvert() {
        // Given : un compte avec un solde de 3000.0, un découvert max de 800.0 et un débit max de 1000.0
        double montantADebiter = 3800.0; // Ce montant ne dépasse pas le débit max (1000.0) par transaction,
                                         // mais rendra le solde inférieur à -800.0 (découvert max)

        // When & Then : Vérifier qu'une exception est levée
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> {
                compteEmetteur.debiter(900.0); // OK
                compteEmetteur.debiter(900.0); // OK
                compteEmetteur.debiter(900.0); // OK
                compteEmetteur.debiter(900.0); // OK
                compteEmetteur.debiter(900.0); // OK
                compteEmetteur.debiter(200.0); // Dépassement du découvert -> Exception attendue
            },
            "Débiter un montant qui dépasse le découvert maximal autorisé devrait lever une IllegalArgumentException."
        );

        assertEquals(
            "Le solde ne peut pas dépasser le découvert maximal autorisé.", 
            exception.getMessage(), 
            "Le message d'exception ne correspond pas à l'attendu."
        );
    }
    
    @Test
    @DisplayName("Test de l'exception lors de la création d'un compte avec un titulaire null")
    void testConstructeur_ExceptionTitulaireNull() {
        // Given : un titulaire null

        // When & Then : Vérifier qu'une exception est levée
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Compte(4, null, 1000.0, 500.0, 300.0),
            "Créer un compte avec un titulaire null devrait lever une IllegalArgumentException."
        );

        assertEquals(
            "Le titulaire ne peut pas être null.", 
            exception.getMessage(), 
            "Le message d'exception ne correspond pas à l'attendu."
        );
    }
    
    @Test
    @DisplayName("Test de la méthode estADecouvert() pour un compte en découvert après plusieurs débits")
    void testEstADecouvert_CompteADecouvert() {
        // Given : un compte avec un solde initial positif (3000.0) et un découvert autorisé de 800.0
        // Effectuer plusieurs débits pour atteindre un solde négatif (découvert)

        // Débiter par étapes
        compteEmetteur.debiter(1000.0); // Le solde devient 3000 - 1000 = 2000
        compteEmetteur.debiter(1000.0); // Le solde devient 2000 - 1000 = 1000
        compteEmetteur.debiter(1000.0); // Le solde devient 1000 - 1000 = 0
        compteEmetteur.debiter(500.0); // Le solde devient 0 - 500 = -500 (découvert dépassé)

        // When : appel de la méthode estADecouvert() sur le compte émetteur
        boolean result = compteEmetteur.estADecouvert();

        // Then : vérifier que le compte est bien en découvert
        assertTrue(result, "Le compte devrait être en découvert après plusieurs débits.");
    }

    @Test
    @DisplayName("Test de la méthode estADecouvert() pour un compte non découvert")
    void testEstADecouvert_CompteNonADecouvert() {
        // Given : un compte avec un solde positif
        double soldeInitial = compteEmetteur.getSolde(); // Solde initial est positif (3000.0)

        // When : appel de la méthode estADecouvert() sur le compte émetteur
        boolean result = compteEmetteur.estADecouvert();

        // Then : vérifier que le compte n'est pas en découvert
        assertFalse(result, "Le compte ne devrait pas être en découvert.");
    }
    
    @Test
    @DisplayName("Test du constructeur d'un compte avec un solde null")
    void testConstructeurAvecSoldeNull() {
        // Given : un titulaire valide et un solde null
        Titulaire titulaire = new Titulaire("Doe", "John", "123 rue Exemple");
        Double soldeNull = null;  // Solde passé en null

        // When : création d'un compte avec un solde null
        Compte compte = new Compte(1, titulaire, soldeNull, 800.0, 1000.0);

        // Then : vérifier que le solde du compte est bien initialisé à 0.0
        assertEquals(0.0, compte.getSolde(), "Le solde du compte devrait être initialisé à 0.0 lorsque le solde est null.");
    }
    
    
}
