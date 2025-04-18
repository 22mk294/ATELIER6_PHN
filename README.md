# ATELIER6_PHN
# 📚 Système de Gestion d'une Bibliothèque - Java Console + MySQL

## 🚀 Objectif

Ce projet est un système complet de gestion de bibliothèque développé en **Java (console)** avec **MySQL** comme base de données. Il permet à différents types d’utilisateurs (Admin, Bibliothécaire, Lecteur) de gérer les livres, emprunts, retours et sanctions à travers une interface console.

---

## 🧱 Architecture du projet

### 📁 Structure Maven

bibliotheque/ ├── pom.xml ├── src/ │ └── main/ │ ├── java/ │ │ └── org/example/ │ │ ├── Main.java │ │ ├── dao/ │ │ │ ├── LivreDAO.java │ │ │ ├── UtilisateurDAO.java │ │ │ ├── EmpruntDAO.java │ │ │ └── SanctionDAO.java │ │ ├── model/ │ │ │ ├── livres/ │ │ │ │ ├── Livre.java │ │ │ │ ├── Roman.java │ │ │ │ ├── Magazine.java │ │ │ │ ├── ScienceFiction.java │ │ │ │ └── Biographie.java │ │ │ ├── utilisateurs/ │ │ │ │ ├── Utilisateur.java │ │ │ │ ├── Admin.java │ │ │ │ ├── Bibliothecaire.java │ │ │ │ └── Lecteur.java │ │ │ ├── Emprunt.java │ │ │ └── Sanction.java │ │ ├── systeme/ │ │ │ ├── Authentification.java │ │ │ └── Bibliotheque.java │ │ └── utils/ │ │ └── DBConnection.java │ └── resources/ │ └── config.properties


---

## ⚙️ Fonctionnement global

Le projet s’articule autour de la classe principale `Main.java` qui gère la **connexion utilisateur** via `Authentification.java` et redirige ensuite vers le menu du rôle correspondant.

Les rôles (Admin, Bibliothécaire, Lecteur) disposent chacun d’un **menu spécifique** et de **fonctionnalités propres**, tout en partageant certains accès (comme la visualisation des livres).

---

## 🧩 Classes Java

### 🔑 `Utilisateur.java` (classe abstraite)

```java
int id;
String nom;
String email;
String motDePasse;
abstract void afficherMenu();

👑 Admin.java
Fonctionnalités :

Ajouter/Supprimer un livre

Voir livres disponibles/empruntés

Ajouter/Supprimer un bibliothécaire

🧑‍🏫 Bibliothecaire.java
Fonctionnalités :

Ajouter/Supprimer un livre

Enregistrer un emprunt

Appliquer des sanctions

Voir livres disponibles/empruntés

📖 Lecteur.java
Fonctionnalités :

Voir les livres disponibles

Consulter le catalogue complet

Emprunter un livre

Retourner un livre

📚 Livre.java (classe abstraite)
java
Copier le code
int id;
String titre;
String auteur;
int anneePublication;
String isbn;
boolean disponible;
abstract void afficherDetails();
Sous-classes :
Roman: genre, nombrePages

Magazine: numero, moisPublication

ScienceFiction: univers

Biographie: sujet

📆 Emprunt.java
java
Copier le code
int id;
int idUtilisateur;
int idLivre;
LocalDate dateEmprunt;
LocalDate dateRetour;
⚠️ Sanction.java
java
Copier le code
int id;
int idEmprunt;
int joursRetard;
double montant;
🔐 Authentification.java
Permet la connexion utilisateur via email + mot de passe.

Renvoie l’instance correspondante (Admin, Bibliothecaire, Lecteur).

🏛️ Bibliotheque.java
Classe singleton centrale (optionnelle)

Contient toutes les opérations principales sur les livres

🗃️ DAO (Data Access Object)
LivreDAO.java
ajouterLivre(Livre)

supprimerLivre(int id)

getLivresDisponibles()

getAllLivres()

UtilisateurDAO.java
ajouterUtilisateur(nom, email, mdp, role)

supprimerUtilisateur(int id)

EmpruntDAO.java
enregistrerEmprunt(idLivre, idLecteur)

retournerLivre(idEmprunt)

afficherEmpruntsActifs()

verifierEtSanctionnerRetards()

SanctionDAO.java
enregistrerSanction(idEmprunt, jours, montant)

🧑‍💻 Utilisateurs par défaut (insérés dans la BDD)
Nom	Email	Mot de passe	Rôle
Alice Admin	admin@bib.com	admin123	ADMIN
Bob Biblio	biblio@bib.com	biblio123	BIBLIOTHECAIRE
Léa Lecteur	lecteur@bib.com	lecteur123	LECTEUR

🛠️ Base de données : script SQL complet
sql
Copier le code
CREATE DATABASE IF NOT EXISTS bibliotheque;
USE bibliotheque;

CREATE TABLE utilisateurs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    mot_de_passe VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'BIBLIOTHECAIRE', 'LECTEUR') NOT NULL
);

CREATE TABLE livres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    auteur VARCHAR(255) NOT NULL,
    annee_publication INT NOT NULL,
    isbn VARCHAR(50) NOT NULL,
    type ENUM('ROMAN','MAGAZINE','SCIENCE_FICTION','BIOGRAPHIE') NOT NULL,
    disponible BOOLEAN DEFAULT TRUE
);

CREATE TABLE emprunts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_utilisateur INT NOT NULL,
    id_livre INT NOT NULL,
    date_emprunt DATE NOT NULL,
    date_retour DATE,
    FOREIGN KEY (id_utilisateur) REFERENCES utilisateurs(id),
    FOREIGN KEY (id_livre) REFERENCES livres(id)
);

CREATE TABLE sanctions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_emprunt INT NOT NULL,
    jours_retard INT NOT NULL,
    montant DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_emprunt) REFERENCES emprunts(id)
);
🧪 Données de test (facultatif)
sql
Copier le code
-- Utilisateurs
INSERT INTO utilisateurs (nom, email, mot_de_passe, role) VALUES
('Alice Admin', 'admin@bib.com', 'admin123', 'ADMIN'),
('Bob Biblio', 'biblio@bib.com', 'biblio123', 'BIBLIOTHECAIRE'),
('Léa Lecteur', 'lecteur@bib.com', 'lecteur123', 'LECTEUR');

-- Livres
INSERT INTO livres (titre, auteur, annee_publication, isbn, type, disponible) VALUES
('Le Petit Prince', 'Antoine de Saint-Exupéry', 1943, '9780156012195', 'ROMAN', TRUE),
('Sciences Mag', 'Equipe Rédac', 2024, 'MAG001', 'MAGAZINE', TRUE),
('Star Wars Galaxy', 'George Lucas', 2000, 'SF001', 'SCIENCE_FICTION', TRUE),
('Steve Jobs', 'Walter Isaacson', 2011, 'BIO001', 'BIOGRAPHIE', TRUE);
🧰 Configuration config.properties
properties
Copier le code
db.url=jdbc:mysql://localhost:3306/bibliotheque
db.user=root
db.password=your_mysql_password
🏁 Instructions pour exécuter
Importer le projet dans un IDE Java (IntelliJ ou Eclipse)

Configurer MySQL avec le script SQL fourni

Vérifier les identifiants dans config.properties

Exécuter Main.java

Se connecter avec un utilisateur par défaut

✅ Fonctionnalités clés par rôle
Fonction / Rôle	Admin	Bibliothécaire	Lecteur
Connexion	✔	✔	✔
Ajouter livre	✔	✔	✖
Supprimer livre	✔	✔	✖
Voir livres dispo.	✔	✔	✔
Voir livres empruntés	✔	✔	✖
Emprunter livre	✖	✔ (pour test)	✔
Retourner livre	✖	✔ (via menu)	✔
Appliquer sanction	✖	✔	✖
Ajouter bibliothécaire	✔	✖	✖
