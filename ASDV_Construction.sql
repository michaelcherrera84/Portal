-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Jan 06, 2025 at 07:06 PM
-- Server version: 8.0.35
-- PHP Version: 8.2.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ASDV_Construction`
--
CREATE DATABASE IF NOT EXISTS `ASDV_Construction` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `ASDV_Construction`;

-- --------------------------------------------------------

--
-- Table structure for table `part`
--

CREATE TABLE `part` (
  `id` int NOT NULL COMMENT 'Unique part number',
  `name` varchar(30) NOT NULL COMMENT 'Part name',
  `category` varchar(20) NOT NULL COMMENT 'Part catagory',
  `location` varchar(30) NOT NULL COMMENT 'Part production location'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `part`
--

INSERT INTO `part` (`id`, `name`, `category`, `location`) VALUES
(1, 'MDF 4ftx8ft', 'Lumber', 'Lafayette'),
(2, 'Hammer', 'Tool', 'Metairie'),
(3, 'Excavator', 'Heavy Equipment', 'Baton Rouge'),
(4, 'Table Saw', 'Machine', 'Denham Springs'),
(5, '12ft 2inx4in', 'Lumber', 'Lake Charles'),
(6, '12ft 2inx6in', 'Lumber', 'Lake Charles'),
(7, '20ft 2inx12in', 'Lumber', 'New Orleans'),
(8, 'Sheet Rock Screw', 'Supply', 'Lafayette'),
(9, 'Impact Driver', 'Machine', 'Denham Springs'),
(10, 'Concrete', 'Supply', 'New Orleans');

-- --------------------------------------------------------

--
-- Table structure for table `project`
--

CREATE TABLE `project` (
  `id` int NOT NULL COMMENT 'Unique project ID',
  `name` varchar(30) NOT NULL COMMENT 'Project name',
  `location` varchar(30) NOT NULL COMMENT 'Project location'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `project`
--

INSERT INTO `project` (`id`, `name`, `location`) VALUES
(1, 'Superdome', 'New Orleans'),
(2, 'Cajundome', 'Lafayette'),
(3, 'Coliseum', 'Alexandria');

-- --------------------------------------------------------

--
-- Table structure for table `spj`
--

CREATE TABLE `spj` (
  `sid` int NOT NULL COMMENT 'Supplier ID',
  `pid` int NOT NULL COMMENT 'Part ID',
  `jid` int NOT NULL COMMENT 'Project ID',
  `qty` int DEFAULT '0' COMMENT 'Quantity of part'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Supplier/Part/Job';

--
-- Dumping data for table `spj`
--

INSERT INTO `spj` (`sid`, `pid`, `jid`, `qty`) VALUES
(1, 1, 2, 20),
(1, 8, 2, 15),
(2, 4, 1, 7),
(2, 4, 3, 5),
(2, 9, 1, 10),
(3, 2, 1, 35),
(3, 7, 1, 22),
(3, 10, 3, 12),
(4, 3, 2, 2),
(5, 5, 2, 41),
(5, 6, 3, 33);

-- --------------------------------------------------------

--
-- Stand-in structure for view `spj_view`
-- (See below for the actual view)
--
CREATE TABLE `spj_view` (
`CONCAT('s-', sid)` varchar(13)
,`concat('p-', pid)` varchar(13)
,`concat('j-', jid)` varchar(13)
,`qty` int
);

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `id` int NOT NULL COMMENT 'Unique supplier ID',
  `name` varchar(30) NOT NULL COMMENT 'Supplier name',
  `date` date NOT NULL COMMENT 'Date added',
  `count` int NOT NULL DEFAULT '0' COMMENT 'Part count',
  `location` varchar(30) NOT NULL COMMENT 'Location of supplier'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`id`, `name`, `date`, `count`, `location`) VALUES
(1, 'Main Street Hardware', '2015-01-01', 16, 'Lafayette'),
(2, 'Big River Machines', '2012-03-15', 10, 'Baton Rouge'),
(3, 'Bob\'s Building Supply', '2017-06-06', 30, 'New Orleans'),
(4, 'Capital Equipment', '2018-10-24', 3, 'Baton Rouge'),
(5, 'Tom\'s Construction Supply', '2020-02-12', 21, 'Lake Charles');

-- --------------------------------------------------------

--
-- Structure for view `spj_view`
--
DROP TABLE IF EXISTS `spj_view`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `spj_view`  AS SELECT concat('s-',`spj`.`sid`) AS `CONCAT('s-', sid)`, concat('p-',`spj`.`pid`) AS `concat('p-', pid)`, concat('j-',`spj`.`jid`) AS `concat('j-', jid)`, `spj`.`qty` AS `qty` FROM `spj` ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `part`
--
ALTER TABLE `part`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `spj`
--
ALTER TABLE `spj`
  ADD PRIMARY KEY (`sid`,`pid`,`jid`),
  ADD KEY `spj_part_id_fk` (`pid`),
  ADD KEY `spj_project_id_fk` (`jid`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `part`
--
ALTER TABLE `part`
  MODIFY `id` int NOT NULL AUTO_INCREMENT COMMENT 'Unique part number', AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `project`
--
ALTER TABLE `project`
  MODIFY `id` int NOT NULL AUTO_INCREMENT COMMENT 'Unique project ID', AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `supplier`
--
ALTER TABLE `supplier`
  MODIFY `id` int NOT NULL AUTO_INCREMENT COMMENT 'Unique supplier ID', AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `spj`
--
ALTER TABLE `spj`
  ADD CONSTRAINT `spj_part_id_fk` FOREIGN KEY (`pid`) REFERENCES `part` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `spj_project_id_fk` FOREIGN KEY (`jid`) REFERENCES `project` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `spj_supplier_id_fk` FOREIGN KEY (`sid`) REFERENCES `supplier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
