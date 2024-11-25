package Gestionbibliotheque;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class Bibliotheque {

        // Connexion à la base de données
        private static final String URL = "jdbc:mysql://localhost:3306/bibliotheque"; // Remplacez par votre base de données
        private static final String USER = "root"; // Remplacez par votre utilisateur
        private static final String PASSWORD = ""; // Remplacez par votre mot de passe

        // Liste pour stocker les articles et membres
        private List<Article> articles;
        private List<Membre> membres;

        // Constructeur
        public Bibliotheque() {
            articles = new ArrayList<>();
            membres = new ArrayList<>();
        }

        // Ajouter un article
        public void ajouterArticle(Article article) {
            articles.add(article);
        }

        // Afficher tous les articles
        public void afficherArticles() {
            for (Article article : articles) {
                article.afficherDetails();
            }
        }

        // Ajouter un membre
        public void ajouterMembre(Membre membre) {
            membres.add(membre);
        }

        // Afficher tous les membres
        public void afficherMembres() {
            for (Membre membre : membres) {
                membre.afficherDetails();
            }
        }

        // Rechercher un article par titre
        public Article rechercherArticleParTitre(String titre) {
            for (Article article : articles) {
                if (article.getTitre().equalsIgnoreCase(titre)) {
                    return article;
                }
            }
            return null;
        }

        // Connexion à la base de données MySQL
        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }

        // Ajouter un livre dans la base de données
        public void ajouterLivreDansDB(Livre livre) throws SQLException {
            String query = "INSERT INTO Article (titre, statut) VALUES (?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setString(1, livre.getTitre());
                stmt.setString(2, livre.getStatut());
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int articleId = rs.getInt(1);
                    String insertLivreQuery = "INSERT INTO Livre (id, auteur, isbn) VALUES (?, ?, ?)";
                    try (PreparedStatement insertLivreStmt = conn.prepareStatement(insertLivreQuery)) {
                        insertLivreStmt.setInt(1, articleId);
                        insertLivreStmt.setString(2, livre.getAuteur());
                        insertLivreStmt.setInt(3, livre.getIsbn());
                        insertLivreStmt.executeUpdate();
                    }
                }
            }
        }

        // Main method
        public static void main(String[] args) {
            try {
                // Création de la bibliothèque
                Bibliotheque bibliotheque = new Bibliotheque();

                // Ajouter des articles
                Livre livre1 = new Livre(1, "Java pour les Nuls", "disponible", "John Doe", 123456);
                CD cd1 = new CD(2, "Best of Jazz", "disponible", 120);
                DVD dvd1 = new DVD(3, "Inception", "disponible", 148);

                bibliotheque.ajouterArticle(livre1);
                bibliotheque.ajouterArticle(cd1);
                bibliotheque.ajouterArticle(dvd1);

                // Ajouter des membres
                Membre membre1 = new Membre(1, "Mohamed", "Bah", "123 Rue Exemple", "123456789", "batobad@yahoo.fr");
                bibliotheque.ajouterMembre(membre1);

                // Afficher les articles
                bibliotheque.afficherArticles();

                // Emprunter un article
                Compte compte1 = new Compte(1, membre1);
                compte1.emprunterArticle(livre1);

                // Afficher les emprunts du membre
                compte1.afficherEmprunts();

                // Retourner un article
                compte1.retournerArticle(livre1);

                // Afficher les articles après retour
                bibliotheque.afficherArticles();

                // Ajouter un livre dans la base de données
                bibliotheque.ajouterLivreDansDB(livre1);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Classe Article (classe de base)
        public static abstract class Article {
            protected int id;
            protected String titre;
            protected String statut; // "disponible" ou "emprunté"

            public Article(int id, String titre, String statut) {
                this.id = id;
                this.titre = titre;
                this.statut = statut;
            }

            public abstract void afficherDetails();

            public int getId() {
                return id;
            }

            public String getTitre() {
                return titre;
            }

            public String getStatut() {
                return statut;
            }

            public void setStatut(String statut) {
                this.statut = statut;
            }
        }

        // Classe Livre
        public static class Livre extends Article {
            private String auteur;
            private int isbn;

            public Livre(int id, String titre, String statut, String auteur, int isbn) {
                super(id, titre, statut);
                this.auteur = auteur;
                this.isbn = isbn;
            }

            @Override
            public void afficherDetails() {
                System.out.println("Livre: " + titre + " - Auteur: " + auteur + " - ISBN: " + isbn + " - Statut: " + statut);
            }

            public String getAuteur() {
                return auteur;
            }

            public int getIsbn() {
                return isbn;
            }
        }

        // Classe CD
        public static class CD extends Article {
            private int duree;

            public CD(int id, String titre, String statut, int duree) {
                super(id, titre, statut);
                this.duree = duree;
            }

            @Override
            public void afficherDetails() {
                System.out.println("CD: " + titre + " - Durée: " + duree + " minutes - Statut: " + statut);
            }

            public int getDuree() {
                return duree;
            }
        }

        // Classe DVD
        public static class DVD extends Article {
            private int duree;

            public DVD(int id, String titre, String statut, int duree) {
                super(id, titre, statut);
                this.duree = duree;
            }

            @Override
            public void afficherDetails() {
                System.out.println("DVD: " + titre + " - Durée: " + duree + " minutes - Statut: " + statut);
            }

            public int getDuree() {
                return duree;
            }
        }

        // Classe Membre
        public static class Membre {
            private int id;
            private String prenom;
            private String nom;
            private String adresse;
            private String telephone;
            private String email;

            public Membre(int id, String prenom, String nom, String adresse, String telephone, String email) {
                this.id = id;
                this.prenom = prenom;
                this.nom = nom;
                this.adresse = adresse;
                this.telephone = telephone;
                this.email = email;
            }

            public void afficherDetails() {
                System.out.println("Membre: " + prenom + " " + nom + " - Adresse: " + adresse + " - Téléphone: " + telephone + " - Email: " + email);
            }

            public int getId() {
                return id;
            }
        }

        // Classe Compte
        public static class Compte {
            private int id;
            private Membre membre;
            private List<Article> articlesEmpruntes;

            public Compte(int id, Membre membre) {
                this.id = id;
                this.membre = membre;
                this.articlesEmpruntes = new ArrayList<>();
            }

            public void emprunterArticle(Article article) {
                if (article.getStatut().equals("disponible")) {
                    articlesEmpruntes.add(article);
                    article.setStatut("emprunté");
                    System.out.println("Article emprunté: " + article.getTitre());
                } else {
                    System.out.println("Article non disponible: " + article.getTitre());
                }
            }

            public void retournerArticle(Article article) {
                if (articlesEmpruntes.contains(article)) {
                    articlesEmpruntes.remove(article);
                    article.setStatut("disponible");
                    System.out.println("Article retourné: " + article.getTitre());
                } else {
                    System.out.println("Cet article n'a pas été emprunté par ce membre.");
                }
            }

            public void afficherEmprunts() {
                System.out.println("Emprunts de " + membre.prenom + " " + membre.nom + ":");
                for (Article article : articlesEmpruntes) {
                    article.afficherDetails();
                }
            }

            public int getId() {
                return id;
            }
        }
    }


