package org.example.model;


import java.time.LocalDate;

public class Emprunt {
    private int id;
    private int idUtilisateur;
    private int idLivre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    public Emprunt(int id, int idUtilisateur, int idLivre, LocalDate dateEmprunt, LocalDate dateRetour) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.idLivre = idLivre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public int getId() { return id; }
    public int getIdUtilisateur() { return idUtilisateur; }
    public int getIdLivre() { return idLivre; }
    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public LocalDate getDateRetour() { return dateRetour; }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }
}
