-- phpMyAdmin SQL Dump
-- version 5.1.3-1.fc35
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 17-03-2022 a las 10:29:00
-- Versión del servidor: 10.5.13-MariaDB
-- Versión de PHP: 8.0.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `Digimon`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Digimon`
--

CREATE TABLE `Digimon` (
  `NomDig` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `AtacDig` int(11) DEFAULT NULL,
  `DefDig` int(11) DEFAULT NULL,
  `TipoDig` enum('ANIMAL','PLANTA','VIRUS','VACUNA','ELEMENTAL') COLLATE utf8_spanish_ci DEFAULT NULL,
  `NivDig` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `Digimon`
--

INSERT INTO `Digimon` (`NomDig`, `AtacDig`, `DefDig`, `TipoDig`, `NivDig`) VALUES
('Felipomon', 65, 58, 'ANIMAL', 23),
('Josepomon', 28, 28, 'VIRUS', 23),
('Sambon', 69, 23, 'VACUNA', 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Tiene`
--

CREATE TABLE `Tiene` (
  `NomDig` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `NomUsu` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `EstaEquipo` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `Tiene`
--

INSERT INTO `Tiene` (`NomDig`, `NomUsu`, `EstaEquipo`) VALUES
('Felipomon', 'Antonio', 0),
('Felipomon', 'Dulsesico', 1),
('Felipomon', 'Manolo', 0),
('Josepomon', 'Antonio', 1),
('Josepomon', 'Manolo', 0),
('Sambon', 'Antonio', 0),
('Sambon', 'Dulsesico', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Usuario`
--

CREATE TABLE `Usuario` (
  `NomUsu` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `ContUsu` varchar(50) COLLATE utf8_spanish_ci DEFAULT NULL,
  `PartidasGan` int(11) DEFAULT NULL,
  `TokensEvo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `Usuario`
--

INSERT INTO `Usuario` (`NomUsu`, `ContUsu`, `PartidasGan`, `TokensEvo`) VALUES
('Antonio', 'cosas', 9, 1),
('Dulsesico', 'ososos', 777, 23),
('Manolo', 'bombo', 23, 4);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Digimon`
--
ALTER TABLE `Digimon`
  ADD PRIMARY KEY (`NomDig`);

--
-- Indices de la tabla `Tiene`
--
ALTER TABLE `Tiene`
  ADD PRIMARY KEY (`NomDig`,`NomUsu`),
  ADD KEY `fk_usuario_tiene` (`NomUsu`);

--
-- Indices de la tabla `Usuario`
--
ALTER TABLE `Usuario`
  ADD PRIMARY KEY (`NomUsu`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Tiene`
--
ALTER TABLE `Tiene`
  ADD CONSTRAINT `fk_digimon_tiene` FOREIGN KEY (`NomDig`) REFERENCES `Digimon` (`NomDig`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_usuario_tiene` FOREIGN KEY (`NomUsu`) REFERENCES `Usuario` (`NomUsu`) ON DELETE NO ACTION ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
