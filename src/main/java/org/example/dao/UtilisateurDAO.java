package org.example.dao;


import org.example.utils.DBConnection;
import java.sql.*;

public class UtilisateurDAO {
    public static void ajouterUtilisateur(String nom, String email, String mdp, String role) {
        String sql = "INSERT INTO utilisateurs(nom, email, mot_de_passe, role) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nom);
            ps.setString(2, email);
            ps.setString(3, mdp);
            ps.setString(4, role.toUpperCase());
            ps.executeUpdate();
            System.out.println("✅ Utilisateur ajouté.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void supprimerUtilisateur(int id) {
        String sql = "DELETE FROM utilisateurs WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("🗑️ Utilisateur supprimé.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
