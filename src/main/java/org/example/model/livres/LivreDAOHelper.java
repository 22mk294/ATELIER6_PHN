package org.example.model.livres;



public class LivreDAOHelper {
    public static Livre createRomanTemp(String titre, String auteur, int annee, String isbn) {
        return new Roman(0, titre, auteur, annee, isbn, true, "Drame", 250);
    }
}
