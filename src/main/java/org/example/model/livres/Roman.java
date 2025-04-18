package org.example.model.livres;

public class Roman extends Livre {
    private String genre;
    private int nombrePages;

    public Roman(int id, String titre, String auteur, int annee, String isbn, boolean dispo, String genre, int pages) {
        super(id, titre, auteur, annee, isbn, dispo);
        this.genre = genre;
        this.nombrePages = pages;
    }

    @Override
    public void afficherDetails() {
        System.out.println("📘 Roman: " + titre + " | Auteur: " + auteur + " | Genre: " + genre + " | Pages: " + nombrePages + " | ISBN: " + isbn);
    }
}
