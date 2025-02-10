package org.example;

/**
 * Représente un compte bancaire avec des opérations de base telles que le crédit, le débit et les virements.
 */
public class Compte {

    private final int numero;
    private final Titulaire titulaire;
    private double solde;
    private double decouvertMaxAutorise;
    private double debitMaxAutorise;

    /**
     * Constructeur de la classe Compte.
     *
     * @param numero               Le numéro unique du compte.
     * @param titulaire            Le titulaire du compte.
     * @param solde                Le solde initial du compte (peut être null, auquel cas il sera initialisé à 0).
     * @param decouvertMaxAutorise Le découvert maximal autorisé pour le compte.
     * @param debitMaxAutorise     Le débit maximal autorisé pour le compte.
     */
    public Compte(int numero, Titulaire titulaire, Double solde, double decouvertMaxAutorise, double debitMaxAutorise) {
        if (titulaire == null) {
            throw new IllegalArgumentException("Le titulaire ne peut pas être null.");
        }
        this.numero = numero;
        this.titulaire = titulaire;
        this.solde = (solde != null) ? solde : 0.0;
        this.decouvertMaxAutorise = decouvertMaxAutorise;
        this.debitMaxAutorise = debitMaxAutorise;
    }

    public int getNumero() {
        return numero;
    }

    public Titulaire getTitulaire() {
        return titulaire;
    }

    public double getSolde() {
        return solde;
    }

    public double getDecouvertMaxAutorise() {
        return decouvertMaxAutorise;
    }

    public double getDebitMaxAutorise() {
        return debitMaxAutorise;
    }

    /**
     * Vérifie si le compte est à découvert.
     *
     * @return true si le compte est à découvert, false sinon.
     */
    public boolean estADecouvert() {
        return solde < 0;
    }

    /**
     * Crédite le compte d'un montant donné.
     *
     * @param montant Le montant à créditer (doit être positif).
     */
    public void crediter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant à créditer doit être positif.");
        }
        solde += montant;
    }

    /**
     * Débite le compte d'un montant donné, en respectant le découvert maximal autorisé.
     *
     * @param montant Le montant à débiter (doit être positif).
     */
    public void debiter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant à débiter doit être positif.");
        }
        if (montant > debitMaxAutorise) {
            throw new IllegalArgumentException("Le montant dépasse le débit maximal autorisé.");
        }
        if (solde - montant < -decouvertMaxAutorise) {
            throw new IllegalArgumentException("Le solde ne peut pas dépasser le découvert maximal autorisé.");
        }
        solde -= montant;
    }

    /**
     * Effectue un virement vers un autre compte.
     *
     * @param destinataire Le compte bénéficiaire du virement.
     * @param montant      Le montant à transférer.
     */
    public void effectuerVirement(Compte destinataire, double montant) {
        if (destinataire == null) {
            throw new IllegalArgumentException("Le compte destinataire ne peut pas être null.");
        }
        this.debiter(montant);
        destinataire.crediter(montant);
    }

    @Override
    public String toString() {
        return "Compte{" +
                "numero=" + numero +
                ", titulaire=" + titulaire +
                ", solde=" + solde +
                ", decouvertMaxAutorise=" + decouvertMaxAutorise +
                ", debitMaxAutorise=" + debitMaxAutorise +
                '}';
    }
}
