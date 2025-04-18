package org.example.model.livres;

public abstract class Livre {
    protected int id;
    protected String titre;
    protected String auteur;
    protected int anneePublication;
    protected String isbn;
    protected boolean disponible;

    public Livre(int id, String titre, String auteur, int anneePublication, String isbn, boolean disponible) {
        this.id = id;
        this.titre = titre;
        this.auteur = auteur;
        this.anneePublication = anneePublication;
        this.isbn = isbn;
        this.disponible = disponible;
    }

    public abstract void afficherDetails();

    public int getId() { return id; }
    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public int getAnneePublication() { return anneePublication; }
    public String getIsbn() { return isbn; }
    public boolean isDisponible() { return disponible; }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}


