-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 26-05-2025 a las 04:52:00
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `examenmod2cripto`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `codiProd` int(11) NOT NULL,
  `nombProd` varchar(100) NOT NULL,
  `descProd` text DEFAULT NULL,
  `precProd` double NOT NULL,
  `stockProd` int(11) NOT NULL DEFAULT 0,
  `fechProd` datetime DEFAULT current_timestamp() COMMENT 'CUMPLE REQUISITO DE FECHA'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`codiProd`, `nombProd`, `descProd`, `precProd`, `stockProd`, `fechProd`) VALUES
(1, 'Laptop Lenovo', 'Laptop 15\" i7 16GB RAM 512GB SSD', 1299.99, 14, '2025-05-25 19:23:56'),
(2, 'Mouse Gamer', 'Mouse inalámbrico 16000DPI RGB', 59.9, 42, '2025-05-25 18:12:09'),
(3, 'Teclado Mecánico', 'Teclado mecánico switches azules', 89.5, 23, '2025-05-25 18:12:09'),
(4, 'Monitor 27\"', 'Monitor QHD 144Hz FreeSync', 349, 8, '2025-05-25 18:12:09'),
(6, 'Celular Samsung', '255gb almacenamiento', 1499.99, 15, '2025-05-25 19:50:09');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `codiUsua` int(11) NOT NULL,
  `logiUsua` varchar(50) NOT NULL,
  `passUsua` varchar(255) NOT NULL COMMENT 'Contraseña cifrada con AES',
  `email` varchar(100) NOT NULL,
  `activo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`codiUsua`, `logiUsua`, `passUsua`, `email`, `activo`) VALUES
(1, 'jperez', '1234', 'juan.perez@example.com', 1),
(2, 'mgarcia', '1234', 'maria.garcia@example.com', 1),
(3, 'lrodrig', '1234', 'luis.rodriguez@example.com', 0),
(4, 'admin', '8cb2237d0679ca88db6464eac60da96345513964', 'admin@system.com', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`codiProd`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`codiUsua`),
  ADD UNIQUE KEY `logiUsua` (`logiUsua`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `productos`
--
ALTER TABLE `productos`
  MODIFY `codiProd` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `codiUsua` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
