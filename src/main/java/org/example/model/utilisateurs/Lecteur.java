package org.example.model.utilisateurs;

import org.example.dao.EmpruntDAO;
import org.example.dao.LivreDAO;
import org.example.model.livres.Livre;

import java.util.List;
import java.util.Scanner;

public class Lecteur extends Utilisateur {

    public Lecteur(int id, String nom, String email, String motDePasse) {
        super(id, nom, email, motDePasse);
    }

    @Override
    public void afficherMenu() {
        Scanner sc = new Scanner(System.in);
        int choix;
        do {
            System.out.println("\n📖 MENU LECTEUR");
            System.out.println("1. Voir livres disponibles");
            System.out.println("2. Consulter catalogue");
            System.out.println("3. Emprunter un livre");
            System.out.println("4. Retourner un livre");
            System.out.println("0. Déconnexion");
            System.out.print("Choix: ");
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1 -> voirLivresDisponibles();
                case 2 -> consulterCatalogue();
                case 3 -> emprunterLivre(sc);
                case 4 -> retournerLivre(sc);
            }
        } while (choix != 0);
    }

    private void voirLivresDisponibles() {
        List<Livre> livres = LivreDAO.getLivresDisponibles();
        if (livres.isEmpty()) {
            System.out.println("❌ Aucun livre disponible.");
        } else {
            System.out.println("📗 Livres disponibles :");
            livres.forEach(Livre::afficherDetails);
        }
    }

    private void consulterCatalogue() {
        List<Livre> livres = LivreDAO.getAllLivres();
        if (livres.isEmpty()) {
            System.out.println("❌ Aucun livre enregistré.");
        } else {
            System.out.println("📚 Catalogue complet :");
            livres.forEach(Livre::afficherDetails);
        }
    }

    private void emprunterLivre(Scanner sc) {
        System.out.print("ID du livre à emprunter : ");
        int idLivre = sc.nextInt();
        EmpruntDAO.enregistrerEmprunt(idLivre, this.id);
    }

    private void retournerLivre(Scanner sc) {
        System.out.print("ID de l'emprunt à retourner : ");
        int idEmprunt = sc.nextInt();
        EmpruntDAO.retournerLivre(idEmprunt);
    }
}
