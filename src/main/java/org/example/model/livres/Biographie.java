package org.example.model.livres;

public class Biographie extends Livre {
    private String sujet;

    public Biographie(int id, String titre, String auteur, int annee, String isbn, boolean dispo, String sujet) {
        super(id, titre, auteur, annee, isbn, dispo);
        this.sujet = sujet;
    }

    @Override
    public void afficherDetails() {
        System.out.println("📖 Biographie: " + titre + " | Sujet: " + sujet + " | ISBN: " + isbn);
    }
}

