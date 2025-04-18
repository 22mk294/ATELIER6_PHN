package org.example.dao;


import org.example.model.livres.Livre;
import org.example.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivreDAO {

    public static List<Livre> getLivresDisponibles() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livres WHERE disponible = true";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                // à adapter selon le type de Livre
                livres.add(new LivreSimple(rs.getInt("id"), rs.getString("titre"),
                        rs.getString("auteur"), rs.getInt("annee_publication"),
                        rs.getString("isbn"), true));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }

    public static List<Livre> getAllLivres() {
        List<Livre> livres = new ArrayList<>();
        String sql = "SELECT * FROM livres";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                livres.add(new LivreSimple(rs.getInt("id"), rs.getString("titre"),
                        rs.getString("auteur"), rs.getInt("annee_publication"),
                        rs.getString("isbn"), rs.getBoolean("disponible")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return livres;
    }


    public static void ajouterLivre(Livre l) {
        String sql = "INSERT INTO livres (titre, auteur, annee_publication, isbn, type, disponible) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, l.getTitre());
            ps.setString(2, l.getAuteur());
            ps.setInt(3, l.getAnneePublication());
            ps.setString(4, l.getIsbn());
            ps.setString(5, l.getClass().getSimpleName().toUpperCase());
            ps.setBoolean(6, true);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void supprimerLivre(int id) {
        String sql = "DELETE FROM livres WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Classe simplifiée pour test
    static class LivreSimple extends Livre {
        public LivreSimple(int id, String titre, String auteur, int annee, String isbn, boolean dispo) {
            super(id, titre, auteur, annee, isbn, dispo);
        }
        @Override
        public void afficherDetails() {
            String etat = disponible ? "✔️ Disponible" : "❌ Emprunté";
            System.out.printf("ID: %d | %s | Auteur: %s | Année: %d | ISBN: %s | %s%n",
                    id, titre, auteur, anneePublication, isbn, etat);
        }
    }
}

