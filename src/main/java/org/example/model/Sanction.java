package org.example.model;


public class Sanction {
    private int id;
    private int idEmprunt;
    private int joursRetard;
    private double montant;

    public Sanction(int id, int idEmprunt, int joursRetard, double montant) {
        this.id = id;
        this.idEmprunt = idEmprunt;
        this.joursRetard = joursRetard;
        this.montant = montant;
    }

    public int getId() { return id; }
    public int getIdEmprunt() { return idEmprunt; }
    public int getJoursRetard() { return joursRetard; }
    public double getMontant() { return montant; }
}
