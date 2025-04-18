package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {
    private static Connection connection;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
                    if (input == null) {
                        throw new RuntimeException("Fichier config.properties introuvable !");
                    }
                    Properties prop = new Properties();
                    prop.load(input);

                    String url = prop.getProperty("db.url");
                    String user = prop.getProperty("db.user");
                    String password = prop.getProperty("db.password");

                    connection = DriverManager.getConnection(url, user, password);
                    System.out.println("Connexion MySQL établie.");
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur de connexion à la base de données : " + e.getMessage());
            throw new RuntimeException(e);
        }
        return connection;
    }
}
