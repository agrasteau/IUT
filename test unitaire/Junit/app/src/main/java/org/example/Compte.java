package org.example;

/**
 * Repr�sente un compte bancaire avec des op�rations de base telles que le cr�dit, le d�bit et les virements.
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
     * @param numero               Le num�ro unique du compte.
     * @param titulaire            Le titulaire du compte.
     * @param solde                Le solde initial du compte (peut �tre null, auquel cas il sera initialis� � 0).
     * @param decouvertMaxAutorise Le d�couvert maximal autoris� pour le compte.
     * @param debitMaxAutorise     Le d�bit maximal autoris� pour le compte.
     */
    public Compte(int numero, Titulaire titulaire, Double solde, double decouvertMaxAutorise, double debitMaxAutorise) {
        if (titulaire == null) {
            throw new IllegalArgumentException("Le titulaire ne peut pas �tre null.");
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
     * V�rifie si le compte est � d�couvert.
     *
     * @return true si le compte est � d�couvert, false sinon.
     */
    public boolean estADecouvert() {
        return solde < 0;
    }

    /**
     * Cr�dite le compte d'un montant donn�.
     *
     * @param montant Le montant � cr�diter (doit �tre positif).
     */
    public void crediter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant � cr�diter doit �tre positif.");
        }
        solde += montant;
    }

    /**
     * D�bite le compte d'un montant donn�, en respectant le d�couvert maximal autoris�.
     *
     * @param montant Le montant � d�biter (doit �tre positif).
     */
    public void debiter(double montant) {
        if (montant <= 0) {
            throw new IllegalArgumentException("Le montant � d�biter doit �tre positif.");
        }
        if (montant > debitMaxAutorise) {
            throw new IllegalArgumentException("Le montant d�passe le d�bit maximal autoris�.");
        }
        if (solde - montant < -decouvertMaxAutorise) {
            throw new IllegalArgumentException("Le solde ne peut pas d�passer le d�couvert maximal autoris�.");
        }
        solde -= montant;
    }

    /**
     * Effectue un virement vers un autre compte.
     *
     * @param destinataire Le compte b�n�ficiaire du virement.
     * @param montant      Le montant � transf�rer.
     */
    public void effectuerVirement(Compte destinataire, double montant) {
        if (destinataire == null) {
            throw new IllegalArgumentException("Le compte destinataire ne peut pas �tre null.");
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
