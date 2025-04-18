package org.example.model.utilisateurs;

import org.example.dao.EmpruntDAO;
import org.example.dao.LivreDAO;
import org.example.dao.SanctionDAO;
import org.example.model.livres.Livre;
import org.example.model.livres.LivreDAOHelper;

import java.util.List;
import java.util.Scanner;

public class Bibliothecaire extends Utilisateur {

    public Bibliothecaire(int id, String nom, String email, String motDePasse) {
        super(id, nom, email, motDePasse);
    }

    @Override
    public void afficherMenu() {
        Scanner sc = new Scanner(System.in);
        int choix;
        do {
            System.out.println("\n📚 MENU BIBLIOTHÉCAIRE");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Supprimer un livre");
            System.out.println("3. Enregistrer un emprunt");
            System.out.println("4. Appliquer une sanction");
            System.out.println("5. Voir livres disponibles");
            System.out.println("6. Voir livres empruntés");
            System.out.println("0. Déconnexion");
            System.out.print("Choix: ");
            choix = sc.nextInt(); sc.nextLine();

            switch (choix) {
                case 1 -> ajouterLivre(sc);
                case 2 -> supprimerLivre(sc);
                case 3 -> enregistrerEmprunt(sc);
                case 4 -> appliquerSanction(sc);
                case 5 -> voirLivresDisponibles();
                case 6 -> voirLivresEmpruntes();
            }
        } while (choix != 0);
    }

    private void ajouterLivre(Scanner sc) {
        System.out.println("📗 Ajouter un livre");

        System.out.print("Titre: ");
        String titre = sc.nextLine();
        System.out.print("Auteur: ");
        String auteur = sc.nextLine();
        System.out.print("Année publication: ");
        int annee = sc.nextInt(); sc.nextLine();
        System.out.print("ISBN: ");
        String isbn = sc.nextLine();

        Livre livre = LivreDAOHelper.createRomanTemp(titre, auteur, annee, isbn);
        LivreDAO.ajouterLivre(livre);
    }

    private void supprimerLivre(Scanner sc) {
        System.out.print("ID du livre à supprimer: ");
        int id = sc.nextInt();
        LivreDAO.supprimerLivre(id);
    }

    private void enregistrerEmprunt(Scanner sc) {
        System.out.print("ID du lecteur: ");
        int idLecteur = sc.nextInt();
        System.out.print("ID du livre: ");
        int idLivre = sc.nextInt();

        EmpruntDAO.enregistrerEmprunt(idLivre, idLecteur);
    }

    private void appliquerSanction(Scanner sc) {
        EmpruntDAO.verifierEtSanctionnerRetards();
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
}
