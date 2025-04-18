package org.example.model.utilisateurs;

import org.example.dao.LivreDAO;
import org.example.dao.UtilisateurDAO;
import org.example.dao.EmpruntDAO;
import org.example.model.livres.Livre;
import org.example.model.livres.LivreDAOHelper;

import java.util.List;
import java.util.Scanner;

public class Admin extends Utilisateur {

    public Admin(int id, String nom, String email, String motDePasse) {
        super(id, nom, email, motDePasse);
    }

    @Override
    public void afficherMenu() {
        Scanner sc = new Scanner(System.in);
        int choix;
        do {
            System.out.println("\n👑 MENU ADMIN");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Supprimer un livre");
            System.out.println("3. Voir livres disponibles");
            System.out.println("4. Voir livres empruntés");
            System.out.println("5. Ajouter un bibliothécaire");
            System.out.println("6. Supprimer un bibliothécaire");
            System.out.println("0. Déconnexion");
            System.out.print("Choix: ");
            choix = sc.nextInt();
            sc.nextLine();

            switch (choix) {
                case 1 -> ajouterLivre(sc);
                case 2 -> supprimerLivre(sc);
                case 3 -> voirLivresDisponibles();
                case 4 -> voirLivresEmpruntes();
                case 5 -> ajouterBibliothecaire(sc);
                case 6 -> supprimerBibliothecaire(sc);
            }
        } while (choix != 0);
    }

    private void ajouterLivre(Scanner sc) {
        System.out.println("🔧 Ajout d'un livre");

        System.out.print("Titre: ");
        String titre = sc.nextLine();
        System.out.print("Auteur: ");
        String auteur = sc.nextLine();
        System.out.print("Année publication: ");
        int annee = sc.nextInt(); sc.nextLine();
        System.out.print("ISBN: ");
        String isbn = sc.nextLine();

        Livre livre = LivreDAOHelper.createRomanTemp(titre, auteur, annee, isbn); // simplifié
        LivreDAO.ajouterLivre(livre);
    }

    private void supprimerLivre(Scanner sc) {
        System.out.print("ID du livre à supprimer: ");
        int id = sc.nextInt();
        LivreDAO.supprimerLivre(id);
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

    private void voirLivresEmpruntes() {
        EmpruntDAO.afficherEmpruntsActifs();
    }

    private void ajouterBibliothecaire(Scanner sc) {
        System.out.print("Nom : ");
        String nom = sc.nextLine();
        System.out.print("Email : ");
        String email = sc.nextLine();
        System.out.print("Mot de passe : ");
        String mdp = sc.nextLine();
        UtilisateurDAO.ajouterUtilisateur(nom, email, mdp, "BIBLIOTHECAIRE");
    }

    private void supprimerBibliothecaire(Scanner sc) {
        System.out.print("ID du bibliothécaire à supprimer : ");
        int id = sc.nextInt();
        UtilisateurDAO.supprimerUtilisateur(id);
    }
}
