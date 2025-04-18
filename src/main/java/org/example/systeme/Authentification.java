package org.example.systeme;

import org.example.utils.DBConnection;
import org.example.model.utilisateurs.*;

import java.sql.*;

public class Authentification {

    public static Utilisateur seConnecter(String email, String motDePasse) {
        String sql = "SELECT * FROM utilisateurs WHERE email = ? AND mot_de_passe = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, motDePasse);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String role = rs.getString("role");

                switch (role.toUpperCase()) {
                    case "ADMIN":
                        return new Admin(id, nom, email, motDePasse);
                    case "BIBLIOTHECAIRE":
                        return new Bibliothecaire(id, nom, email, motDePasse);
                    case "LECTEUR":
                        return new Lecteur(id, nom, email, motDePasse);
                    default:
                        return null;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur Authentification : " + e.getMessage());
        }
        return null;
    }
}

