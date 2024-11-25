package Gestionbibliotheque;

    public class TestBibliotheque {

        public static void main(String[] args) {

            // Création de la bibliothèque
            Bibliotheque bibliotheque = new Bibliotheque();

            // Ajouter des articles au catalogue
            Bibliotheque.Livre livre1 = new Bibliotheque.Livre(1, "Java pour les Nuls", "disponible", "John Doe", 123456);
            Bibliotheque.CD cd1 = new Bibliotheque.CD(2, "Best of Jazz", "disponible", 120);
            Bibliotheque.DVD dvd1 = new Bibliotheque.DVD(3, "Inception", "disponible", 148);

            bibliotheque.ajouterArticle(livre1);
            bibliotheque.ajouterArticle(cd1);
            bibliotheque.ajouterArticle(dvd1);

            // Afficher tous les articles dans le catalogue
            System.out.println("===== Liste des Articles =====");
            bibliotheque.afficherArticles();

            // Ajouter des membres à la bibliothèque
            Bibliotheque.Membre membre1 = new Bibliotheque.Membre(1, "Mohamed", "Bah", "123 Rue Exemple", "123456789", "batobad@yahoo.fr");
            bibliotheque.ajouterMembre(membre1);

            // Afficher les détails des membres
            System.out.println("\n===== Liste des Membres =====");
            bibliotheque.afficherMembres();

            // Emprunter un article (par exemple, "Java pour les Nuls")
            Bibliotheque.Compte compte1 = new Bibliotheque.Compte(1, membre1);
            System.out.println("\n===== Emprunt d'un Article =====");
            compte1.emprunterArticle(livre1);

            // Afficher les emprunts du membre
            compte1.afficherEmprunts();

            // Retourner l'article emprunté
            System.out.println("\n===== Retour d'un Article =====");
            compte1.retournerArticle(livre1);

            // Afficher les emprunts après le retour
            compte1.afficherEmprunts();

            // Afficher à nouveau tous les articles du catalogue après le retour
            System.out.println("\n===== Liste des Articles Après Retour =====");
            bibliotheque.afficherArticles();



        }
}


