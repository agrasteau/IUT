package org.example;

/**
 * Représente une personne titulaire d'un ou plusieurs comptes bancaires.
 */
public class Titulaire {
    private String nom;
    private String prenom;
    private String adresse;

    /**
     * Constructeur pour créer un titulaire de compte.
     *
     * @param nom     Le nom de la personne.
     * @param prenom  Le prénom de la personne.
     * @param adresse L'adresse de la personne.
     */
    public Titulaire(String nom, String prenom, String adresse) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
    }

    /**
     * Retourne le nom du titulaire.
     *
     * @return Le nom du titulaire.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne le prénom du titulaire.
     *
     * @return Le prénom du titulaire.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Retourne l'adresse du titulaire.
     *
     * @return L'adresse du titulaire.
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Retourne une représentation sous forme de chaîne des informations du titulaire.
     *
     * @return Une chaîne contenant le prénom, le nom et l'adresse.
     */
    @Override
    public String toString() {
        return prenom + " " + nom + " (" + adresse + ")";
    }
}
