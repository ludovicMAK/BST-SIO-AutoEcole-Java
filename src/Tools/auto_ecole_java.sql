-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : db:3306
-- Généré le : dim. 02 avr. 2023 à 15:32
-- Version du serveur : 8.0.30
-- Version de PHP : 8.0.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `auto_ecole_phptojava`
--

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE `categorie` (
  `id` int NOT NULL,
  `libelle` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prix` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`id`, `libelle`, `prix`) VALUES
(1, 'Voiture', 23.2),
(2, 'Camion', 27.7),
(3, 'Moto', 25.4),
(4, 'Vélo', 18.3);

-- --------------------------------------------------------

--
-- Structure de la table `eleve`
--

CREATE TABLE `eleve` (
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `nom` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prenom` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sexe` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date_de_naissance` date NOT NULL,
  `adresse` varchar(75) COLLATE utf8mb4_unicode_ci NOT NULL,
  `code_postal` varchar(5) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ville` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telephone` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `eleve`
--

INSERT INTO `eleve` (`id`, `user_id`, `nom`, `prenom`, `sexe`, `date_de_naissance`, `adresse`, `code_postal`, `ville`, `telephone`, `email`) VALUES
(1, 1, 'Danielle', 'Fouquet', 'Homme', '1998-07-19', '102, boulevard Simon\n10556 Gaillard-sur-Mer', '72', 'Richardboeuf', '+33 1 80 71 54 19', 'danielle.fouquet@gmail.com'),
(2, 2, 'Honoré', 'Peltier', 'Homme', '2017-04-26', '66, chemin Normand\n33 182 Juliennec', '37', 'Rollanddan', '09 43 79 67 60', 'honore.peltier@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `lecon`
--

CREATE TABLE `lecon` (
  `id` int NOT NULL,
  `eleve_id` int NOT NULL,
  `moniteur_id` int NOT NULL,
  `vehicule_id` int NOT NULL,
  `date` date NOT NULL,
  `heure` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `payee` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `lecon`
--

INSERT INTO `lecon` (`id`, `eleve_id`, `moniteur_id`, `vehicule_id`, `date`, `heure`, `payee`) VALUES
(1, 1, 1, 26, '2023-03-15', '14:00', 0),
(2, 1, 1, 25, '2023-02-22', '17:00', 1),
(3, 1, 2, 28, '2023-02-04', '10:00', 1),
(4, 2, 2, 25, '2023-03-09', '10:00', 1),
(5, 2, 1, 29, '2023-02-28', '18:00', 0),
(6, 2, 2, 25, '2023-02-03', '17:00', 0),
(7, 1, 1, 25, '2023-01-26', '17:00', 1),
(8, 2, 1, 21, '2023-03-05', '12:00', 0),
(9, 1, 2, 23, '2023-01-05', '10:00', 0),
(10, 2, 1, 24, '2023-03-10', '16:00', 0),
(11, 2, 1, 24, '2023-03-23', '10:00', 0),
(12, 1, 2, 28, '2023-02-15', '12:00', 0),
(13, 1, 2, 29, '2023-02-23', '18:00', 1),
(14, 2, 2, 23, '2023-01-12', '14:00', 1),
(15, 2, 1, 25, '2023-03-17', '15:00', 1),
(16, 2, 2, 30, '2023-01-04', '14:00', 0),
(17, 2, 1, 28, '2023-03-26', '10:00', 1),
(18, 1, 1, 28, '2023-03-07', '11:00', 0),
(19, 2, 2, 27, '2023-01-04', '11:00', 1),
(20, 1, 2, 27, '2023-02-08', '17:00', 1),
(21, 1, 2, 25, '2023-02-19', '12:00', 1),
(22, 1, 1, 29, '2023-01-18', '10:00', 0),
(23, 1, 1, 30, '2023-01-06', '12:00', 0),
(24, 2, 2, 22, '2023-02-23', '18:00', 0),
(25, 1, 2, 30, '2023-01-25', '18:00', 1),
(26, 1, 2, 25, '2023-01-14', '15:00', 0),
(27, 1, 2, 29, '2023-03-25', '18:00', 1),
(28, 1, 2, 21, '2023-04-01', '17:00', 1),
(29, 1, 1, 22, '2023-04-02', '09:00', 1),
(30, 2, 1, 24, '2023-03-09', '13:00', 1),
(31, 2, 1, 27, '2023-03-10', '13:00', 1),
(32, 2, 1, 26, '2023-01-23', '10:00', 1),
(33, 2, 1, 26, '2023-03-23', '17:00', 1),
(34, 1, 1, 23, '2023-02-28', '10:00', 1),
(35, 1, 1, 21, '2023-02-18', '18:00', 0),
(36, 1, 2, 30, '2023-03-09', '16:00', 0),
(37, 1, 2, 23, '2023-02-18', '13:00', 0),
(38, 2, 1, 29, '2023-03-12', '15:00', 0),
(39, 2, 1, 22, '2023-01-12', '12:00', 0),
(40, 2, 2, 29, '2023-01-22', '14:00', 0),
(41, 1, 1, 38, '2023-03-29', '10:00', 1),
(42, 1, 2, 33, '2023-03-28', '10:00', 1),
(43, 2, 1, 33, '2023-02-28', '14:00', 0),
(44, 1, 2, 35, '2023-01-25', '14:00', 0),
(45, 1, 2, 35, '2023-01-09', '10:00', 0),
(46, 1, 2, 40, '2023-03-17', '16:00', 1),
(47, 1, 2, 40, '2023-02-12', '14:00', 1),
(48, 1, 2, 34, '2023-01-16', '10:00', 1),
(49, 1, 1, 33, '2023-02-14', '16:00', 0),
(50, 2, 2, 39, '2023-01-17', '13:00', 0),
(51, 1, 2, 39, '2023-03-06', '11:00', 1),
(52, 1, 2, 38, '2023-02-15', '13:00', 0),
(53, 1, 1, 32, '2023-02-01', '18:00', 1),
(54, 1, 2, 37, '2023-02-03', '10:00', 1),
(55, 2, 2, 37, '2023-01-24', '14:00', 0),
(56, 2, 2, 38, '2023-01-21', '10:00', 0),
(57, 2, 2, 33, '2023-02-16', '15:00', 0),
(58, 2, 2, 33, '2023-01-03', '10:00', 0),
(59, 1, 2, 34, '2023-01-28', '10:00', 1),
(60, 1, 1, 35, '2023-02-22', '12:00', 1),
(61, 1, 2, 38, '2023-02-12', '16:00', 1),
(62, 1, 1, 37, '2023-03-05', '15:00', 1),
(63, 1, 1, 36, '2023-01-28', '17:00', 1),
(64, 2, 1, 34, '2023-03-11', '15:00', 1),
(65, 2, 1, 40, '2023-02-01', '09:00', 1),
(66, 2, 1, 39, '2023-03-17', '14:00', 0),
(67, 1, 1, 40, '2023-02-16', '15:00', 1),
(68, 2, 2, 31, '2023-02-01', '10:00', 0),
(69, 1, 2, 33, '2023-01-22', '14:00', 1),
(70, 1, 2, 35, '2023-02-13', '15:00', 1),
(71, 1, 1, 39, '2023-03-02', '15:00', 0),
(72, 2, 2, 32, '2023-03-30', '12:00', 1),
(73, 1, 1, 33, '2023-03-06', '18:00', 0),
(74, 2, 2, 31, '2023-03-30', '11:00', 0),
(75, 1, 2, 31, '2023-01-04', '18:00', 1),
(76, 2, 2, 31, '2023-01-27', '15:00', 1),
(77, 2, 2, 32, '2023-03-30', '15:00', 0),
(78, 2, 2, 32, '2023-01-12', '14:00', 1),
(79, 2, 1, 35, '2023-02-25', '10:00', 1),
(80, 1, 2, 34, '2023-03-22', '11:00', 1),
(81, 1, 2, 12, '2023-01-17', '14:00', 0),
(82, 1, 1, 16, '2023-03-01', '15:00', 1),
(83, 2, 2, 20, '2023-01-25', '17:00', 0),
(84, 2, 2, 12, '2023-01-29', '11:00', 0),
(85, 1, 2, 15, '2023-02-13', '10:00', 0),
(86, 2, 2, 15, '2023-01-09', '11:00', 1),
(87, 2, 1, 11, '2023-01-26', '09:00', 0),
(88, 2, 1, 12, '2023-03-05', '12:00', 1),
(89, 1, 2, 20, '2023-01-08', '15:00', 1),
(90, 2, 1, 11, '2023-02-23', '13:00', 0),
(91, 2, 1, 12, '2023-03-25', '16:00', 1),
(92, 1, 1, 12, '2023-03-20', '14:00', 0),
(93, 1, 2, 20, '2023-01-08', '12:00', 1),
(94, 1, 2, 12, '2023-01-25', '10:00', 1),
(95, 1, 2, 19, '2023-03-11', '18:00', 0),
(96, 1, 1, 20, '2023-04-01', '13:00', 0),
(97, 2, 1, 17, '2023-02-18', '11:00', 0),
(98, 1, 2, 14, '2023-02-02', '16:00', 0),
(99, 2, 1, 18, '2023-02-17', '16:00', 1),
(100, 1, 2, 15, '2023-03-12', '18:00', 0),
(101, 2, 1, 15, '2023-02-02', '17:00', 0),
(102, 2, 1, 15, '2023-02-25', '15:00', 1),
(103, 1, 2, 19, '2023-01-13', '13:00', 0),
(104, 2, 1, 19, '2023-03-26', '10:00', 1),
(105, 2, 2, 17, '2023-01-05', '17:00', 1),
(106, 2, 2, 14, '2023-01-08', '14:00', 1),
(107, 1, 1, 15, '2023-02-15', '13:00', 1),
(108, 1, 1, 13, '2023-03-08', '18:00', 1),
(109, 2, 1, 17, '2023-02-11', '18:00', 0),
(110, 1, 1, 18, '2023-02-03', '13:00', 1),
(111, 1, 1, 20, '2023-03-14', '16:00', 0),
(112, 1, 1, 19, '2023-03-06', '17:00', 1),
(113, 2, 2, 15, '2023-01-15', '11:00', 0),
(114, 1, 1, 16, '2023-03-22', '16:00', 1),
(115, 1, 1, 14, '2023-01-24', '10:00', 1),
(116, 2, 1, 19, '2023-01-06', '14:00', 1),
(117, 2, 2, 15, '2023-01-23', '15:00', 1),
(118, 2, 1, 15, '2023-03-16', '12:00', 1),
(119, 2, 2, 14, '2023-03-06', '14:00', 1),
(120, 2, 2, 12, '2023-03-23', '15:00', 1),
(121, 1, 2, 31, '2023-02-26', '15:00', 1),
(122, 2, 2, 31, '2023-02-23', '09:00', 1),
(123, 1, 1, 40, '2023-03-14', '10:00', 0),
(124, 2, 2, 37, '2023-01-19', '17:00', 0),
(125, 2, 2, 32, '2023-02-16', '16:00', 0),
(126, 1, 2, 40, '2023-01-19', '17:00', 1),
(127, 1, 1, 39, '2023-03-04', '16:00', 0),
(128, 1, 1, 35, '2023-01-29', '11:00', 1),
(129, 1, 2, 35, '2023-02-15', '13:00', 1),
(130, 1, 2, 40, '2023-02-05', '16:00', 0),
(131, 1, 1, 35, '2023-02-11', '12:00', 1),
(132, 1, 1, 32, '2023-01-07', '18:00', 0),
(133, 1, 2, 39, '2023-01-26', '17:00', 0),
(134, 1, 2, 34, '2023-01-11', '18:00', 1),
(135, 2, 2, 40, '2023-03-19', '16:00', 1),
(136, 2, 2, 40, '2023-02-20', '15:00', 1),
(137, 2, 2, 36, '2023-02-28', '09:00', 0),
(138, 1, 2, 40, '2023-03-05', '14:00', 0),
(139, 1, 1, 40, '2023-03-28', '13:00', 0),
(140, 1, 2, 35, '2023-02-10', '15:00', 0),
(141, 1, 1, 36, '2023-01-21', '15:00', 1),
(142, 1, 2, 35, '2023-03-19', '09:00', 0),
(143, 1, 1, 34, '2023-01-18', '09:00', 1),
(144, 2, 1, 34, '2023-03-20', '12:00', 0),
(145, 2, 1, 37, '2023-03-10', '18:00', 1),
(146, 1, 2, 39, '2023-03-07', '16:00', 1),
(147, 1, 1, 37, '2023-02-10', '14:00', 0),
(148, 2, 1, 33, '2023-03-25', '18:00', 1),
(149, 2, 2, 34, '2023-01-22', '18:00', 1),
(150, 2, 1, 37, '2023-01-04', '16:00', 0),
(151, 2, 1, 40, '2023-03-26', '18:00', 0),
(152, 2, 1, 39, '2023-02-11', '12:00', 1),
(153, 1, 2, 34, '2023-01-01', '09:00', 1),
(154, 2, 1, 32, '2023-03-07', '11:00', 0),
(155, 1, 1, 38, '2023-02-10', '10:00', 1),
(156, 1, 2, 40, '2023-04-01', '13:00', 1),
(157, 2, 2, 34, '2023-03-07', '09:00', 1),
(158, 2, 2, 37, '2023-03-06', '15:00', 1),
(159, 2, 1, 40, '2023-02-26', '12:00', 1),
(160, 2, 2, 40, '2023-01-12', '10:00', 1),
(161, 2, 2, 24, '2023-01-12', '14:00', 1),
(162, 1, 2, 23, '2023-03-20', '12:00', 0),
(163, 1, 2, 24, '2023-02-22', '14:00', 1),
(164, 2, 1, 26, '2023-03-13', '18:00', 0),
(165, 2, 1, 22, '2023-03-12', '10:00', 1),
(166, 1, 2, 26, '2023-01-09', '11:00', 1),
(167, 2, 2, 28, '2023-03-04', '15:00', 0),
(168, 1, 2, 24, '2023-01-12', '16:00', 1),
(169, 2, 1, 25, '2023-03-07', '09:00', 0),
(170, 2, 2, 24, '2023-01-29', '10:00', 0),
(171, 1, 1, 25, '2023-02-24', '16:00', 0),
(172, 1, 1, 28, '2023-03-06', '12:00', 1),
(173, 1, 1, 21, '2023-01-12', '13:00', 0),
(174, 2, 2, 24, '2023-04-01', '16:00', 0),
(175, 1, 2, 27, '2023-01-11', '16:00', 0),
(176, 2, 2, 27, '2023-03-08', '16:00', 1),
(177, 1, 1, 30, '2023-01-11', '10:00', 1),
(178, 1, 1, 21, '2023-02-17', '17:00', 1),
(179, 1, 1, 22, '2023-02-01', '16:00', 1),
(180, 1, 2, 24, '2023-02-20', '15:00', 0),
(181, 1, 1, 22, '2023-03-24', '18:00', 1),
(182, 2, 2, 22, '2023-01-19', '10:00', 1),
(183, 1, 1, 27, '2023-01-06', '09:00', 0),
(184, 2, 1, 25, '2023-01-10', '15:00', 0),
(185, 1, 2, 21, '2023-02-02', '16:00', 1),
(186, 1, 1, 25, '2023-01-25', '09:00', 1),
(187, 1, 2, 28, '2023-01-23', '16:00', 0),
(188, 1, 1, 24, '2023-04-01', '12:00', 1),
(189, 1, 2, 24, '2023-01-19', '16:00', 1),
(190, 1, 2, 25, '2023-01-07', '10:00', 1),
(191, 2, 1, 25, '2023-01-21', '14:00', 0),
(192, 2, 2, 25, '2023-02-09', '13:00', 1),
(193, 1, 1, 21, '2023-01-23', '11:00', 0),
(194, 1, 2, 22, '2023-03-02', '11:00', 0),
(195, 1, 2, 28, '2023-01-24', '11:00', 1),
(196, 2, 1, 22, '2023-03-25', '13:00', 1),
(197, 2, 1, 30, '2023-02-07', '10:00', 1),
(198, 1, 1, 26, '2023-01-29', '13:00', 0),
(199, 1, 2, 24, '2023-01-21', '11:00', 1),
(200, 1, 2, 26, '2023-03-28', '12:00', 0);

-- --------------------------------------------------------

--
-- Structure de la table `licence`
--

CREATE TABLE `licence` (
  `id` int NOT NULL,
  `moniteur_id` int NOT NULL,
  `categorie_id` int NOT NULL,
  `date_obtention` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `licence`
--

INSERT INTO `licence` (`id`, `moniteur_id`, `categorie_id`, `date_obtention`) VALUES
(1, 1, 3, '1992-08-24'),
(2, 1, 4, '1987-03-31'),
(3, 2, 2, '1993-03-16'),
(4, 2, 4, '2008-11-19'),
(5, 2, 3, '2004-09-22');

-- --------------------------------------------------------

--
-- Structure de la table `moniteur`
--

CREATE TABLE `moniteur` (
  `id` int NOT NULL,
  `user_id` int DEFAULT NULL,
  `nom` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `prenom` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `sexe` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `date_de_naissance` date NOT NULL,
  `adresse` varchar(75) COLLATE utf8mb4_unicode_ci NOT NULL,
  `code_postal` varchar(5) COLLATE utf8mb4_unicode_ci NOT NULL,
  `ville` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `telephone` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `moniteur`
--

INSERT INTO `moniteur` (`id`, `user_id`, `nom`, `prenom`, `sexe`, `date_de_naissance`, `adresse`, `code_postal`, `ville`, `telephone`, `email`) VALUES
(1, 3, 'Amélie', 'Descamps', 'Femme', '2009-03-04', '63, rue Brunel\n43 363 Jacquot-sur-Roussel', '14', 'De Oliveira-les-Bains', '+33 (0)8 91 71 56 29', 'amelie.descamps@gmail.com'),
(2, 4, 'Patricia', 'Gilbert', 'Femme', '2002-06-15', '73, boulevard de Lebreton\n65 232 Laine-sur-Pierre', '65', 'Merle', '+33 1 95 69 48 22', 'patricia.gilbert@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int NOT NULL,
  `login` varchar(180) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `role` int NOT NULL DEFAULT '2'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `login`, `password`, `role`) VALUES
(1, 'eleve', '$2y$13$zHVF5JWXHnax7cB9/hy0HOUzA1Rdc1O6J08Qvn.gGmDG8LL/M8yZO', 2),
(2, 'eleve2', '$2y$13$K5iXXQmgmrkOjIg4N9dmFeOzHNKu6V6aL4E3eSoznE4b6kEjQVxT6', 2),
(3, 'moniteur', '$2y$13$Dkxt7bUpztiQVOFzlgNEfeIPOt2NcWTLgEejJv0tRXXlyOkuBW50e', 1),
(4, 'moniteur2', '$2y$13$5znFhcpun9G1y8MrAxSUt.B9X5I3mauvbHjpFpgfKIayxCTr7xaJy', 1),
(5, 'gerante', '$2y$13$CXj/TsUVohqzq6LCv/RZzuMwKTakHXFrGUJ4Qb/qm832IC7Ivb8WS', 0);

-- --------------------------------------------------------

--
-- Structure de la table `vehicule`
--

CREATE TABLE `vehicule` (
  `id` int NOT NULL,
  `categorie_id` int NOT NULL,
  `immatriculation` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `marque` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `modele` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `annee` varchar(4) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `vehicule`
--

INSERT INTO `vehicule` (`id`, `categorie_id`, `immatriculation`, `marque`, `modele`, `annee`) VALUES
(1, 1, 'ZY-860-BF', 'BMW', 'X series', '2018'),
(2, 1, 'XH-748-KZ', 'Triumph', 'Toledo', '2010'),
(3, 1, 'TR-211-BJ', 'Autobianchi', 'A 112', '1996'),
(4, 1, 'EN-359-DB', 'Shelby', 'Cobra', '1993'),
(5, 1, 'PI-141-RL', 'Humvee', 'Marshal', '2018'),
(6, 1, 'FV-731-PO', 'MPM Motors', 'PS 160', '1991'),
(7, 1, 'WN-673-TR', 'Altamarea', '2E', '2010'),
(8, 1, 'RY-592-OU', 'Dagger', 'GT', '2018'),
(9, 1, 'OM-276-VD', 'Fiat-Abarth', '695', '2017'),
(10, 1, 'SP-766-DR', 'Ravon', 'Matiz', '2011'),
(11, 2, 'JT-801-NA', 'Ram', 'Promaster', '1991'),
(12, 2, 'DG-136-FW', 'Peg-Perego', 'Gaucho', '2002'),
(13, 2, 'YC-655-FZ', 'Marshell', 'DN', '2020'),
(14, 2, 'LS-794-LE', 'Rover', 'Targa', '2021'),
(15, 2, 'OW-479-SR', 'Aero', '30', '2015'),
(16, 2, 'RL-954-NB', 'Chrysler', '200', '1997'),
(17, 2, 'AT-035-XO', 'FAW', 'V2', '2017'),
(18, 2, 'II-822-TX', 'Datsun', 'on-DO', '2022'),
(19, 2, 'FD-398-XG', 'Kirkham', '427 KMS', '2016'),
(20, 2, 'QO-562-MC', 'Daf', '46', '2005'),
(21, 3, 'WT-423-YG', 'Pontiac', 'Sunbird', '2007'),
(22, 3, 'ZY-243-PJ', 'Thunder Tiger', 'Gonow', '2018'),
(23, 3, 'MG-876-XB', 'Tarpan Honker', '237', '2014'),
(24, 3, 'BJ-036-LW', 'Rolls-Royce', 'Silver Cloud', '1995'),
(25, 3, 'QW-583-UE', 'Dacia', 'Duster', '2014'),
(26, 3, 'OQ-361-GQ', 'Zastava', '750', '2014'),
(27, 3, 'VW-629-VR', 'GMC', 'Acadia USA', '1997'),
(28, 3, 'EE-914-RG', 'Smart', 'Fortwo', '2007'),
(29, 3, 'PM-688-FP', 'Hyundai', 'Santa FE', '1998'),
(30, 3, 'VO-442-TN', 'Huabei', 'HC', '2021'),
(31, 4, 'HL-285-GV', 'Morris', 'Minor', '2014'),
(32, 4, 'ZI-470-GP', 'Suzuki', 'Dingo', '2006'),
(33, 4, 'CY-308-TO', 'Lancia', 'Prisma', '2016'),
(34, 4, 'JJ-607-MX', 'Land Rover', 'Defender', '1995'),
(35, 4, 'HY-130-BL', 'Aston Martin', 'Tick', '2011'),
(36, 4, 'KC-825-CO', 'Dr. Motor', 'DR5', '2016'),
(37, 4, 'XN-678-VT', 'Soyat', 'Yuejin', '2006'),
(38, 4, 'PL-863-XO', 'Infiniti', 'QX56', '2013'),
(39, 4, 'AG-431-KP', 'Luxgen', '7', '2003'),
(40, 4, 'KV-625-LZ', 'Proton', 'Saloon', '2000');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `eleve`
--
ALTER TABLE `eleve`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_ECA105F7A76ED395` (`user_id`);

--
-- Index pour la table `lecon`
--
ALTER TABLE `lecon`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_94E6242EA6CC7B2` (`eleve_id`),
  ADD KEY `IDX_94E6242EA234A5D3` (`moniteur_id`),
  ADD KEY `IDX_94E6242E4A4A3511` (`vehicule_id`);

--
-- Index pour la table `licence`
--
ALTER TABLE `licence`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_1DAAE648A234A5D3` (`moniteur_id`),
  ADD KEY `IDX_1DAAE648BCF5E72D` (`categorie_id`);

--
-- Index pour la table `moniteur`
--
ALTER TABLE `moniteur`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_B3EC8EBAA76ED395` (`user_id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_8D93D649AA08CB10` (`login`);

--
-- Index pour la table `vehicule`
--
ALTER TABLE `vehicule`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_292FFF1DBCF5E72D` (`categorie_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `categorie`
--
ALTER TABLE `categorie`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `eleve`
--
ALTER TABLE `eleve`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `lecon`
--
ALTER TABLE `lecon`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=201;

--
-- AUTO_INCREMENT pour la table `licence`
--
ALTER TABLE `licence`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `moniteur`
--
ALTER TABLE `moniteur`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `vehicule`
--
ALTER TABLE `vehicule`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=41;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `eleve`
--
ALTER TABLE `eleve`
  ADD CONSTRAINT `FK_ECA105F7A76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `lecon`
--
ALTER TABLE `lecon`
  ADD CONSTRAINT `FK_94E6242E4A4A3511` FOREIGN KEY (`vehicule_id`) REFERENCES `vehicule` (`id`),
  ADD CONSTRAINT `FK_94E6242EA234A5D3` FOREIGN KEY (`moniteur_id`) REFERENCES `moniteur` (`id`),
  ADD CONSTRAINT `FK_94E6242EA6CC7B2` FOREIGN KEY (`eleve_id`) REFERENCES `eleve` (`id`);

--
-- Contraintes pour la table `licence`
--
ALTER TABLE `licence`
  ADD CONSTRAINT `FK_1DAAE648A234A5D3` FOREIGN KEY (`moniteur_id`) REFERENCES `moniteur` (`id`),
  ADD CONSTRAINT `FK_1DAAE648BCF5E72D` FOREIGN KEY (`categorie_id`) REFERENCES `categorie` (`id`);

--
-- Contraintes pour la table `moniteur`
--
ALTER TABLE `moniteur`
  ADD CONSTRAINT `FK_B3EC8EBAA76ED395` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Contraintes pour la table `vehicule`
--
ALTER TABLE `vehicule`
  ADD CONSTRAINT `FK_292FFF1DBCF5E72D` FOREIGN KEY (`categorie_id`) REFERENCES `categorie` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
