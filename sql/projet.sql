
-- Suppression des tables si elles existent déjà
DROP TABLE IF EXISTS Comptes;
DROP TABLE IF EXISTS Membres;
DROP TABLE IF EXISTS Articles;
DROP TABLE IF EXISTS Categories;

-- Création de la table Categories
CREATE TABLE Categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL
);

-- Création de la table Articles
CREATE TABLE Articles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reference VARCHAR(20) UNIQUE NOT NULL,
    titre VARCHAR(255) NOT NULL,
    statut ENUM('disponible', 'emprunté') NOT NULL,
    categorie_id INT,
    auteur VARCHAR(255),
    isbn VARCHAR(20),
    duree INT,
    FOREIGN KEY (categorie_id) REFERENCES Categories(id)
);

-- Création de la table Membres
CREATE TABLE Membres (
    id INT AUTO_INCREMENT PRIMARY KEY,
    prenom VARCHAR(100) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    adresse TEXT,
    telephone VARCHAR(20),
    email VARCHAR(100) UNIQUE NOT NULL,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Création de la table Comptes
CREATE TABLE Comptes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    membre_id INT,
    date_creation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    articles_empruntes TEXT,
    historique_emprunts TEXT,
    FOREIGN KEY (membre_id) REFERENCES Membres(id)
);



-- Insertion des catégories
INSERT INTO Categories (type) VALUES ('Livre'), ('CD'), ('DVD');

-- Insertion des articles
INSERT INTO Articles (reference, titre, statut, categorie_id, auteur, isbn, duree) VALUES
('REF001', 'Le Petit Prince', 'disponible', 1, 'Antoine de Saint-Exupéry', '978-3-16-148410-0', NULL),
('REF002', 'Best of 2023', 'disponible', 2, NULL, NULL, 75),
('REF003', 'Inception', 'emprunté', 3, NULL, NULL, 148);

-- Insertion des membres
INSERT INTO Membres (prenom, nom, adresse, telephone, email) VALUES
('Alice', 'Dupont', '123 Rue A', '0123456789', 'alice@example.com'),
('Bob', 'Martin', '456 Rue B', '0987654321', 'bob@example.com');

-- Insertion des comptes
INSERT INTO Comptes (membre_id, articles_empruntes, historique_emprunts) VALUES
(1, 'REF003', 'REF002'),
(2, '', '');

