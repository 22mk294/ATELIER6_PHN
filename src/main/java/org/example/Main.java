package org.example;

import org.example.model.utilisateurs.Utilisateur;
import org.example.systeme.Authentification;
import org.example.dao.EmpruntDAO;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Connexion au systeme ===");
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Mot de passe: ");
        String mdp = sc.nextLine();

        Utilisateur user = Authentification.seConnecter(email, mdp);
        if (user != null) {
            System.out.println("Bienvenue " + user.getNom());

            // Vérification des retards
            EmpruntDAO.verifierEtSanctionnerRetards();

            // Navigation par rôle
            user.afficherMenu();
        } else {
            System.out.println("Connexion échouée.");
        }
    }
}
