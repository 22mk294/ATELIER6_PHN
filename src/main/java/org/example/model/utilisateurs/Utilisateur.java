package org.example.model.utilisateurs;

public abstract class Utilisateur {
    protected int id;
    protected String nom;
    protected String email;
    protected String motDePasse;

    public Utilisateur(int id, String nom, String email, String motDePasse) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public abstract void afficherMenu();

    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public int getId() { return id; }

    public void afficherInfos() {
        System.out.println("Nom: " + nom + " | Email: " + email);
    }
}

