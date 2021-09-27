-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 27-09-2021 a las 14:57:36
-- Versión del servidor: 5.7.24
-- Versión de PHP: 7.4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `facturador`
--
CREATE DATABASE IF NOT EXISTS `facturador` DEFAULT CHARACTER SET utf8 COLLATE utf8_spanish2_ci;
USE `facturador`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `baja`
--

CREATE TABLE `baja` (
  `id` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha` char(10) COLLATE utf8_spanish2_ci NOT NULL,
  `fechaComprobante` char(10) COLLATE utf8_spanish2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bajadet`
--

CREATE TABLE `bajadet` (
  `id` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `idBaja` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `tipoComprobante` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `idComprobante` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `motivo` varchar(50) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `boleta`
--

CREATE TABLE `boleta` (
  `id` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `idCliente` char(3) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha` char(10) COLLATE utf8_spanish2_ci NOT NULL,
  `horaEmision` varchar(10) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `fechaVencimiento` char(10) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `moneda` char(3) COLLATE utf8_spanish2_ci NOT NULL,
  `medioPago` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `totalVentasGravadas` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `totalGratuito` varchar(15) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `igv` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `importeTotal` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `hash` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `boletadet`
--

CREATE TABLE `boletadet` (
  `id` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `idBoleta` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `item` char(2) COLLATE utf8_spanish2_ci NOT NULL,
  `cantidad` varchar(23) COLLATE utf8_spanish2_ci NOT NULL,
  `tipoUnidad` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `codigo` varchar(30) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `descripcion` longtext COLLATE utf8_spanish2_ci NOT NULL,
  `tributo` varchar(10) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `montoTributo` varchar(15) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `tipoAfectacionTributo` varchar(5) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `valorUnitario` varchar(23) COLLATE utf8_spanish2_ci NOT NULL,
  `valorUnitarioGratuito` varchar(23) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `precioUnitarioItem` varchar(23) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id` char(3) COLLATE utf8_spanish2_ci NOT NULL,
  `tipoDocumento` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `numeroDocumento` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `nombreRazonSocial` varchar(200) COLLATE utf8_spanish2_ci NOT NULL,
  `direccion` varchar(500) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `config`
--

CREATE TABLE `config` (
  `id` char(1) COLLATE utf8_spanish2_ci NOT NULL,
  `ruc` varchar(11) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `razonSocial` longtext COLLATE utf8_spanish2_ci,
  `direccion` longtext COLLATE utf8_spanish2_ci,
  `telefono` varchar(100) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `correo` varchar(250) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `web` varchar(250) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `impresion` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `rutaSunat` longtext COLLATE utf8_spanish2_ci,
  `rutaPdf` longtext COLLATE utf8_spanish2_ci,
  `serieFactura` char(2) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `serieBoleta` char(2) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `serieNCreditoFactura` char(2) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `serieNCreditoBoleta` char(2) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `serieNDebitoFactura` char(2) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `serieNDebitoBoleta` char(2) COLLATE utf8_spanish2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Volcado de datos para la tabla `config`
--

INSERT INTO `config` (`id`, `ruc`, `razonSocial`, `direccion`, `telefono`, `correo`, `web`, `impresion`, `rutaSunat`, `rutaPdf`, `serieFactura`, `serieBoleta`, `serieNCreditoFactura`, `serieNCreditoBoleta`, `serieNDebitoFactura`, `serieNDebitoBoleta`) VALUES
('1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '01', '01', '01', '01', '01', '01');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `id` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `idCliente` char(8) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha` char(10) COLLATE utf8_spanish2_ci NOT NULL,
  `horaEmision` varchar(10) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `fechaVencimiento` char(10) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `moneda` char(3) COLLATE utf8_spanish2_ci NOT NULL,
  `medioPago` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `totalVentasGravadas` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `totalGratuito` varchar(15) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `igv` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `importeTotal` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `hash` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `facturadet`
--

CREATE TABLE `facturadet` (
  `id` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `idFactura` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `item` char(2) COLLATE utf8_spanish2_ci NOT NULL,
  `cantidad` varchar(23) COLLATE utf8_spanish2_ci NOT NULL,
  `tipoUnidad` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `codigo` varchar(30) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `descripcion` longtext COLLATE utf8_spanish2_ci NOT NULL,
  `tributo` varchar(10) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `montoTributo` varchar(15) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `tipoAfectacionTributo` varchar(5) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `valorUnitario` varchar(23) COLLATE utf8_spanish2_ci NOT NULL,
  `valorUnitarioGratuito` varchar(23) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `precioUnitarioItem` varchar(23) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notacredito`
--

CREATE TABLE `notacredito` (
  `id` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `idComprobante` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha` char(10) COLLATE utf8_spanish2_ci NOT NULL,
  `horaEmision` varchar(10) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `moneda` char(3) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `medioPago` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `motivo` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `totalVentasGravadas` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `totalGratuito` varchar(15) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `igv` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `importeTotal` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `hash` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notacreditodet`
--

CREATE TABLE `notacreditodet` (
  `id` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `idNotaCredito` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `item` char(2) COLLATE utf8_spanish2_ci NOT NULL,
  `cantidad` varchar(23) COLLATE utf8_spanish2_ci NOT NULL,
  `tipoUnidad` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `codigo` varchar(30) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `descripcion` longtext COLLATE utf8_spanish2_ci NOT NULL,
  `tributo` varchar(10) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `montoTributo` varchar(15) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `tipoAfectacionTributo` varchar(5) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `valorUnitario` varchar(23) COLLATE utf8_spanish2_ci NOT NULL,
  `valorUnitarioGratuito` varchar(23) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `precioUnitarioItem` varchar(23) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notadebito`
--

CREATE TABLE `notadebito` (
  `id` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `idComprobante` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `fecha` char(10) COLLATE utf8_spanish2_ci NOT NULL,
  `horaEmision` varchar(10) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `moneda` char(3) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `medioPago` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `motivo` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `totalVentasGravadas` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `totalGratuito` varchar(15) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `igv` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `importeTotal` varchar(20) COLLATE utf8_spanish2_ci NOT NULL,
  `hash` varchar(50) COLLATE utf8_spanish2_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notadebitodet`
--

CREATE TABLE `notadebitodet` (
  `id` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `idNotaDebito` varchar(50) COLLATE utf8_spanish2_ci NOT NULL,
  `item` char(2) COLLATE utf8_spanish2_ci NOT NULL,
  `cantidad` varchar(23) COLLATE utf8_spanish2_ci NOT NULL,
  `tipoUnidad` varchar(10) COLLATE utf8_spanish2_ci NOT NULL,
  `codigo` varchar(30) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `descripcion` longtext COLLATE utf8_spanish2_ci NOT NULL,
  `tributo` varchar(10) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `montoTributo` varchar(15) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `tipoAfectacionTributo` varchar(5) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `valorUnitario` varchar(23) COLLATE utf8_spanish2_ci NOT NULL,
  `valorUnitarioGratuito` varchar(23) COLLATE utf8_spanish2_ci DEFAULT NULL,
  `precioUnitarioItem` varchar(23) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` int(11) NOT NULL,
  `codigo` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `descripcion` varchar(500) COLLATE utf8_spanish2_ci NOT NULL,
  `precio1` decimal(11,2) DEFAULT NULL,
  `precio2` decimal(11,2) DEFAULT NULL,
  `estado` varchar(50) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicio`
--

CREATE TABLE `servicio` (
  `id` int(11) NOT NULL,
  `codigo` varchar(30) COLLATE utf8_spanish2_ci NOT NULL,
  `descripcion` varchar(500) COLLATE utf8_spanish2_ci NOT NULL,
  `precio1` decimal(11,2) DEFAULT NULL,
  `precio2` decimal(11,2) DEFAULT NULL,
  `estado` varchar(50) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` char(2) COLLATE utf8_spanish2_ci NOT NULL,
  `usuario` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `contrasena` varchar(100) COLLATE utf8_spanish2_ci NOT NULL,
  `rol` varchar(100) COLLATE utf8_spanish2_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish2_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `baja`
--
ALTER TABLE `baja`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `bajadet`
--
ALTER TABLE `bajadet`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `boleta`
--
ALTER TABLE `boleta`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `boletadet`
--
ALTER TABLE `boletadet`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `config`
--
ALTER TABLE `config`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `facturadet`
--
ALTER TABLE `facturadet`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `notacredito`
--
ALTER TABLE `notacredito`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `notacreditodet`
--
ALTER TABLE `notacreditodet`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `notadebito`
--
ALTER TABLE `notadebito`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `notadebitodet`
--
ALTER TABLE `notadebitodet`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `servicio`
--
ALTER TABLE `servicio`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `servicio`
--
ALTER TABLE `servicio`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
