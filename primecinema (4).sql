-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-09-2024 a las 06:35:55
-- Versión del servidor: 10.4.17-MariaDB
-- Versión de PHP: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `primecinema`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `butacas`
--

CREATE TABLE `butacas` (
  `id` int(11) NOT NULL,
  `fila` int(11) NOT NULL,
  `columna` int(11) NOT NULL,
  `estado` varchar(10) NOT NULL,
  `fecha_modificacion` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `butacas`
--

INSERT INTO `butacas` (`id`, `fila`, `columna`, `estado`, `fecha_modificacion`) VALUES
(1, 0, 0, 'Desocupada', '2024-09-13 23:39:12'),
(2, 1, 0, 'Desocupada', '2024-09-13 23:39:12'),
(3, 2, 0, 'Desocupada', '2024-09-13 23:39:12'),
(4, 3, 0, 'Desocupada', '2024-09-13 23:39:12'),
(6, 0, 1, 'Desocupada', '2024-09-13 23:39:12'),
(7, 0, 2, 'Desocupada', '2024-09-13 23:39:12'),
(8, 0, 3, 'Desocupada', '2024-09-13 23:39:12'),
(9, 0, 4, 'Desocupada', '2024-09-13 23:39:12'),
(11, 1, 1, 'Desocupada', '2024-09-13 23:39:12'),
(12, 1, 2, 'Desocupada', '2024-09-13 23:39:12'),
(13, 1, 3, 'Desocupada', '2024-09-13 23:39:12'),
(14, 1, 4, 'Desocupada', '2024-09-13 23:39:12'),
(16, 2, 1, 'Desocupada', '2024-09-13 23:39:12'),
(17, 2, 2, 'Desocupada', '2024-09-13 23:39:12'),
(18, 2, 3, 'Desocupada', '2024-09-13 23:39:12'),
(19, 2, 4, 'Desocupada', '2024-09-13 23:39:12'),
(21, 3, 1, 'Desocupada', '2024-09-13 23:39:12'),
(22, 3, 2, 'Desocupada', '2024-09-13 23:39:12'),
(23, 3, 3, 'Desocupada', '2024-09-13 23:39:12'),
(24, 3, 4, 'Desocupada', '2024-09-13 23:39:12'),
(25, 4, 0, 'Desocupada', '2024-09-13 23:39:12'),
(26, 4, 1, 'Desocupada', '2024-09-13 23:39:12'),
(27, 4, 2, 'Desocupada', '2024-09-13 23:39:12'),
(28, 4, 3, 'Desocupada', '2024-09-13 23:39:12'),
(29, 4, 4, 'Desocupada', '2024-09-13 23:39:12'),
(30, 5, 0, 'Desocupada', '2024-09-13 23:39:12'),
(31, 5, 1, 'Desocupada', '2024-09-13 23:39:12'),
(32, 5, 2, 'Desocupada', '2024-09-13 23:39:12'),
(33, 5, 3, 'Desocupada', '2024-09-13 23:39:12'),
(34, 5, 4, 'Desocupada', '2024-09-13 23:39:12'),
(35, 6, 0, 'Desocupada', '2024-09-13 23:39:12'),
(36, 6, 1, 'Desocupada', '2024-09-13 23:39:12'),
(37, 6, 2, 'Desocupada', '2024-09-13 23:39:12'),
(38, 6, 3, 'Desocupada', '2024-09-13 23:39:12'),
(39, 6, 4, 'Desocupada', '2024-09-13 23:39:12'),
(40, 7, 0, 'Desocupada', '2024-09-13 23:39:12'),
(41, 7, 1, 'Desocupada', '2024-09-13 23:39:12'),
(42, 7, 2, 'Desocupada', '2024-09-13 23:39:12'),
(43, 7, 3, 'Desocupada', '2024-09-13 23:39:12'),
(44, 7, 4, 'Desocupada', '2024-09-13 23:39:12');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `funcion`
--

CREATE TABLE `funcion` (
  `id_precio` int(50) NOT NULL,
  `formato_pelicula` varchar(30) NOT NULL,
  `tipo_cliente` text NOT NULL,
  `precio_funcion` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `peliculas`
--

CREATE TABLE `peliculas` (
  `id_pelicula` int(11) NOT NULL,
  `nombre_pelicula` varchar(255) NOT NULL,
  `genero` varchar(100) NOT NULL,
  `clasificacion` varchar(50) NOT NULL,
  `formato` enum('Tradicional','3D') NOT NULL,
  `valor_funcion` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `peliculas`
--

INSERT INTO `peliculas` (`id_pelicula`, `nombre_pelicula`, `genero`, `clasificacion`, `formato`, `valor_funcion`) VALUES
(1, 'sherk', 'comedia', 'infantil', 'Tradicional', '5.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `salas`
--

CREATE TABLE `salas` (
  `id_sala` int(11) NOT NULL,
  `numero_sala` int(11) NOT NULL,
  `id_sucursal` int(11) NOT NULL,
  `pelicula` varchar(100) DEFAULT NULL,
  `horario` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `salas`
--

INSERT INTO `salas` (`id_sala`, `numero_sala`, `id_sucursal`, `pelicula`, `horario`) VALUES
(1, 1, 1, 'sherk', '10.00 a 11.00'),
(2, 3, 2, 'camino', '12.00 a 2.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursales`
--

CREATE TABLE `sucursales` (
  `id_sucursal` int(10) NOT NULL,
  `nombre_sucursal` text NOT NULL,
  `gerente` text NOT NULL,
  `direccion_sucursal` varchar(50) NOT NULL,
  `telefono_sucursal` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `sucursales`
--

INSERT INTO `sucursales` (`id_sucursal`, `nombre_sucursal`, `gerente`, `direccion_sucursal`, `telefono_sucursal`) VALUES
(1, 'Sanjose', 'jose', 'calle a san jose', 12341234),
(2, 'mariasucursal', 'maria', 'callemaria', 98766789),
(3, 'marvinsucursal', 'marvin', 'calle alk', 123456788),
(5, 'marianasucursal', 'mariana', 'callemariana', 56748329);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `usuario` varchar(20) NOT NULL,
  `contrasena` varchar(30) NOT NULL,
  `dui` int(9) NOT NULL,
  `nombre_completo` text NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `correo` varchar(50) NOT NULL,
  `telefono` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`usuario`, `contrasena`, `dui`, `nombre_completo`, `direccion`, `correo`, `telefono`) VALUES
('Carla Bautista', '54321', 1234343, 'Carla Bautista Hernandez', 'Calle a don Bosco', 'carla3221@gmail.com', 22322323),
('Pedro', '321', 1236451, 'Pedro Antonio', 'Cuidadela don bosco', 'pedro321@gmail.com', 21342333),
('ana', '123', 2123444, 'ana maria', 'Don bosco', 'ana123@gmail.com', 1232323),
('waldo', '333', 213456789, 'Waldo Juan', 'calle', 'callealcanton', 12123434),
('ma', '1234', 987654321, 'mama', 'callala', 'ma@gmail.com', 23456754);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `butacas`
--
ALTER TABLE `butacas`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `unique_butaca` (`fila`,`columna`);

--
-- Indices de la tabla `funcion`
--
ALTER TABLE `funcion`
  ADD PRIMARY KEY (`id_precio`);

--
-- Indices de la tabla `peliculas`
--
ALTER TABLE `peliculas`
  ADD PRIMARY KEY (`id_pelicula`);

--
-- Indices de la tabla `salas`
--
ALTER TABLE `salas`
  ADD PRIMARY KEY (`id_sala`),
  ADD KEY `id_sucursal` (`id_sucursal`);

--
-- Indices de la tabla `sucursales`
--
ALTER TABLE `sucursales`
  ADD PRIMARY KEY (`id_sucursal`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`dui`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `butacas`
--
ALTER TABLE `butacas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=235;

--
-- AUTO_INCREMENT de la tabla `peliculas`
--
ALTER TABLE `peliculas`
  MODIFY `id_pelicula` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `salas`
--
ALTER TABLE `salas`
  MODIFY `id_sala` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `sucursales`
--
ALTER TABLE `sucursales`
  MODIFY `id_sucursal` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `salas`
--
ALTER TABLE `salas`
  ADD CONSTRAINT `salas_ibfk_1` FOREIGN KEY (`id_sucursal`) REFERENCES `sucursales` (`id_sucursal`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
