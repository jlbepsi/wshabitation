-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le : lun. 04 juil. 2022 à 11:34
-- Version du serveur :  8.0.27
-- Version de PHP : 7.4.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `rhhabitation`
--

-- --------------------------------------------------------

--
-- Structure de la table `habitation`
--

CREATE TABLE `habitation` (
  `id` int NOT NULL,
  `typehabitat_id` int NOT NULL,
  `libelle` varchar(100) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  `adresse` varchar(100) NOT NULL,
  `id_ville` int NOT NULL,
  `image` varchar(250) DEFAULT NULL,
  `habitantsmax` int NOT NULL,
  `chambres` int NOT NULL,
  `lits` int NOT NULL,
  `sdb` int NOT NULL,
  `superficie` int NOT NULL,
  `prixmois` decimal(10,0) NOT NULL
) ;

--
-- Déchargement des données de la table `habitation`
--

INSERT INTO `habitation` (`id`, `typehabitat_id`, `libelle`, `description`, `adresse`, `id_ville`, `image`, `habitantsmax`, `chambres`, `lits`, `sdb`, `superficie`, `prixmois`) VALUES
(1, 1, 'Maison provençale', 'bla bla', '12 Rue du Coq qui chante', 56, 'none', 8, 3, 3, 1, 92, '600'),
(2, 2, 'Appartement centre ville', 'bla bla', 'Rue de la soif', 99, 'none', 4, 1, 2, 1, 50, '555');

-- --------------------------------------------------------

--
-- Structure de la table `habitation_item`
--

CREATE TABLE `habitation_item` (
  `habitation_id` int NOT NULL,
  `item_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `habitation_item`
--

INSERT INTO `habitation_item` (`habitation_id`, `item_id`) VALUES
(1, 1),
(2, 1),
(1, 2);

-- --------------------------------------------------------

--
-- Structure de la table `habitation_optionpayante`
--

CREATE TABLE `habitation_optionpayante` (
  `habitation_id` int NOT NULL,
  `optionpayante_id` int NOT NULL,
  `prix` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `habitation_optionpayante`
--

INSERT INTO `habitation_optionpayante` (`habitation_id`, `optionpayante_id`, `prix`) VALUES
(1, 1, 60),
(1, 2, 30),
(1, 3, 20),
(2, 1, 40);

-- --------------------------------------------------------

--
-- Structure de la table `item`
--

CREATE TABLE `item` (
  `id` int NOT NULL,
  `libelle` varchar(100) NOT NULL,
  `description` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `item`
--

INSERT INTO `item` (`id`, `libelle`, `description`) VALUES
(1, 'Internet', 'Wifi et Fibre'),
(2, 'Lac', 'Base de loisirs à 2 km');

-- --------------------------------------------------------

--
-- Structure de la table `locationro`
--

CREATE TABLE `locationro` (
  `id` int NOT NULL,
  `idutilisateur` int NOT NULL,
  `habitation_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `locationro`
--

INSERT INTO `locationro` (`id`, `idutilisateur`, `habitation_id`) VALUES
(1, 5, 1),
(2, 6, 2);

-- --------------------------------------------------------

--
-- Structure de la table `optionpayante`
--

CREATE TABLE `optionpayante` (
  `id` int NOT NULL,
  `libelle` varchar(100) NOT NULL,
  `description` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `optionpayante`
--

INSERT INTO `optionpayante` (`id`, `libelle`, `description`) VALUES
(1, 'Ménage', 'A la fin du séjour'),
(2, 'Drap de lit', 'Pour l\'ensemble des lits'),
(3, 'Linge de maison', 'Linge de toilette pour la salle de bain');

-- --------------------------------------------------------

--
-- Structure de la table `typehabitat`
--

CREATE TABLE `typehabitat` (
  `id` int NOT NULL,
  `libelle` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- Déchargement des données de la table `typehabitat`
--

INSERT INTO `typehabitat` (`id`, `libelle`) VALUES
(1, 'Maison'),
(2, 'Appartement');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `habitation`
--
ALTER TABLE `habitation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_typehabitat` (`typehabitat_id`);

--
-- Index pour la table `habitation_item`
--
ALTER TABLE `habitation_item`
  ADD PRIMARY KEY (`habitation_id`,`item_id`),
  ADD KEY `item_id` (`item_id`);

--
-- Index pour la table `habitation_optionpayante`
--
ALTER TABLE `habitation_optionpayante`
  ADD PRIMARY KEY (`habitation_id`,`optionpayante_id`),
  ADD KEY `optionpayante_id` (`optionpayante_id`);

--
-- Index pour la table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `locationro`
--
ALTER TABLE `locationro`
  ADD PRIMARY KEY (`id`),
  ADD KEY `habitation_id` (`habitation_id`);

--
-- Index pour la table `optionpayante`
--
ALTER TABLE `optionpayante`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `typehabitat`
--
ALTER TABLE `typehabitat`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `habitation`
--
ALTER TABLE `habitation`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `item`
--
ALTER TABLE `item`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `optionpayante`
--
ALTER TABLE `optionpayante`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `typehabitat`
--
ALTER TABLE `typehabitat`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `habitation`
--
ALTER TABLE `habitation`
  ADD CONSTRAINT `habitation_ibfk_1` FOREIGN KEY (`typehabitat_id`) REFERENCES `typehabitat` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `habitation_item`
--
ALTER TABLE `habitation_item`
  ADD CONSTRAINT `habitation_item_ibfk_1` FOREIGN KEY (`habitation_id`) REFERENCES `habitation` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `habitation_item_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `habitation_optionpayante`
--
ALTER TABLE `habitation_optionpayante`
  ADD CONSTRAINT `habitation_optionpayante_ibfk_1` FOREIGN KEY (`habitation_id`) REFERENCES `habitation` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `habitation_optionpayante_ibfk_2` FOREIGN KEY (`optionpayante_id`) REFERENCES `optionpayante` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `locationro`
--
ALTER TABLE `locationro`
  ADD CONSTRAINT `locationro_ibfk_1` FOREIGN KEY (`habitation_id`) REFERENCES `habitation` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
