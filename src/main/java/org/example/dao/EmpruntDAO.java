package org.example.dao;

import org.example.utils.DBConnection;
import org.example.dao.SanctionDAO;

import java.sql.*;
import java.time.LocalDate;

public class EmpruntDAO {

    public static void enregistrerEmprunt(int idLivre, int idLecteur) {
        // Vérifier si livre disponible
        String checkSql = "SELECT disponible FROM livres WHERE id = ?";
        String insertSql = "INSERT INTO emprunts(id_utilisateur, id_livre, date_emprunt) VALUES (?, ?, ?)";
        String updateLivre = "UPDATE livres SET disponible = false WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setInt(1, idLivre);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && !rs.getBoolean("disponible")) {
                System.out.println("❌ Ce livre est déjà emprunté !");
                return;
            }

            try (
                    PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                    PreparedStatement updateStmt = conn.prepareStatement(updateLivre)
            ) {
                insertStmt.setInt(1, idLecteur);
                insertStmt.setInt(2, idLivre);
                insertStmt.setDate(3, Date.valueOf(LocalDate.now()));
                insertStmt.executeUpdate();

                updateStmt.setInt(1, idLivre);
                updateStmt.executeUpdate();

                System.out.println("📚 Emprunt enregistré avec succès.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void retournerLivre(int idEmprunt) {
        String updateRetour = "UPDATE emprunts SET date_retour = ? WHERE id = ?";
        String getLivreId = "SELECT id_livre FROM emprunts WHERE id = ?";
        String majDispo = "UPDATE livres SET disponible = true WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement getStmt = conn.prepareStatement(getLivreId)) {

            getStmt.setInt(1, idEmprunt);
            ResultSet rs = getStmt.executeQuery();

            if (rs.next()) {
                int idLivre = rs.getInt("id_livre");

                try (
                        PreparedStatement updateEmprunt = conn.prepareStatement(updateRetour);
                        PreparedStatement updateLivre = conn.prepareStatement(majDispo)
                ) {
                    updateEmprunt.setDate(1, Date.valueOf(LocalDate.now()));
                    updateEmprunt.setInt(2, idEmprunt);
                    updateEmprunt.executeUpdate();

                    updateLivre.setInt(1, idLivre);
                    updateLivre.executeUpdate();

                    System.out.println("🔁 Livre retourné et remis en disponibilité.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void verifierEtSanctionnerRetards() {
        String sql = "SELECT e.id, e.date_emprunt FROM emprunts e " +
                "LEFT JOIN sanctions s ON e.id = s.id_emprunt " +
                "WHERE e.date_retour IS NULL AND s.id IS NULL";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idEmprunt = rs.getInt("id");
                Date dateEmprunt = rs.getDate("date_emprunt");

                long jours = (System.currentTimeMillis() - dateEmprunt.getTime()) / (1000 * 60 * 60 * 24);
                if (jours > 14) {
                    int retard = (int) (jours - 14);
                    double montant = retard * 1.5;
                    SanctionDAO.enregistrerSanction(idEmprunt, retard, montant);
                    System.out.printf("⚠️ Retard détecté | Emprunt #%d | %d jours | %.2f€\n", idEmprunt, retard, montant);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void afficherEmpruntsActifs() {
        String sql = """
        SELECT e.id AS emprunt_id, l.id AS livre_id, l.titre, l.disponible,
               u.nom, e.date_emprunt
        FROM emprunts e
        JOIN livres l ON e.id_livre = l.id
        JOIN utilisateurs u ON e.id_utilisateur = u.id
        WHERE e.date_retour IS NULL
    """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("📕 Livres empruntés actuellement :");
            while (rs.next()) {
                String etat = rs.getBoolean("disponible") ? "✔️ Disponible" : "❌ Emprunté";
                System.out.printf("📚 Livre #%d: %s | Par: %s | Depuis: %s | %s | Emprunt #%d\n",
                        rs.getInt("livre_id"),
                        rs.getString("titre"),
                        rs.getString("nom"),
                        rs.getDate("date_emprunt"),
                        etat,
                        rs.getInt("emprunt_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
