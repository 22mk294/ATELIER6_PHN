package org.example.model.livres;

public class Magazine extends Livre {
    private int numero;
    private String moisPublication;

    public Magazine(int id, String titre, String auteur, int annee, String isbn, boolean dispo, int numero, String mois) {
        super(id, titre, auteur, annee, isbn, dispo);
        this.numero = numero;
        this.moisPublication = mois;
    }

    @Override
    public void afficherDetails() {
        System.out.println("📰 Magazine: " + titre + " | Numéro: " + numero + " | Mois: " + moisPublication + " | ISBN: " + isbn);
    }
}

