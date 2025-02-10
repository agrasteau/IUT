package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests unitaires pour la classe Titulaire.
 */
public class TitulaireTest {

    @ParameterizedTest
    @CsvSource({
        "Dupont, Jean, 10 rue de Paris",
        "Durand, Claire, 45 avenue des Champs",
        "Martin, Pierre, 8 impasse des Lilas"
    })
    @DisplayName("Test du constructeur et des getters de Titulaire")
    void testConstructeurEtGetters(String nom, String prenom, String adresse) {
        // Given : un titulaire avec un nom, pr�nom et adresse
        Titulaire titulaire = new Titulaire(nom, prenom, adresse);

        // Then : v�rification des informations du titulaire
        assertEquals(nom, titulaire.getNom(), "Le nom du titulaire est incorrect.");
        assertEquals(prenom, titulaire.getPrenom(), "Le pr�nom du titulaire est incorrect.");
        assertEquals(adresse, titulaire.getAdresse(), "L'adresse du titulaire est incorrecte.");
    }

    @ParameterizedTest
    @CsvSource({
        "Dupont, Jean, 10 rue de Paris, Jean Dupont (10 rue de Paris)",
        "Durand, Claire, 45 avenue des Champs, Claire Durand (45 avenue des Champs)"
    })
    @DisplayName("Test de la m�thode toString de Titulaire")
    void testToString(String nom, String prenom, String adresse, String expectedToString) {
        // Given : un titulaire avec des informations
        Titulaire titulaire = new Titulaire(nom, prenom, adresse);

        // Then : v�rification de la repr�sentation en cha�ne
        assertEquals(expectedToString, titulaire.toString(), "La m�thode toString est incorrecte.");
    }
}