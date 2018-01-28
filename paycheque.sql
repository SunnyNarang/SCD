-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 30, 2017 at 07:32 AM
-- Server version: 5.5.49-0ubuntu0.14.04.1
-- PHP Version: 5.5.9-1ubuntu4.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `paycheque`
--

-- --------------------------------------------------------

--
-- Table structure for table `cheque_issue`
--

CREATE TABLE IF NOT EXISTS `cheque_issue` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `acc_from` varchar(500) NOT NULL,
  `acc_to` varchar(500) NOT NULL,
  `image_name` varchar(1000) NOT NULL,
  `qr_data` varchar(5000) NOT NULL,
  `amount` int(10) NOT NULL,
  `cheque_id` int(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=43 ;

--
-- Dumping data for table `cheque_issue`
--

INSERT INTO `cheque_issue` (`id`, `acc_from`, `acc_to`, `image_name`, `qr_data`, `amount`, `cheque_id`) VALUES
(1, '123456789', '007', 'kl', 'kl', 5000, 0),
(2, '007', '12345', 'a.jpg', 'dsfadf', 5000, 0),
(3, '007', '123', 'a.jpg', 'dsfadf', 5000, 0),
(4, '007', '12345', 'a.jpg', 'dsfadf', 100000, 0),
(5, '007', '12345', 'a.jpg', 'dsfadf', 500000, 0),
(6, '12345', '007', 'a.jpg', 'dsfadf', 1000, 0),
(7, '12345', '007', 'a.jpg', 'dsfadf', 1000, 0),
(8, '12345', '007', 'a.jpg', 'dsfadf', 10000, 0),
(9, '12345', '007', 'a.jpg', 'dsfadf', 999, 0),
(10, '007', '12345', 'a.jpg', 'dsfadf', 50000, 0),
(11, '007', '12345', 'a.jpg', 'dsfadf', 50000, 0),
(12, '12345', '007', 'a.jpg', 'dsfadf', 50000, 0),
(13, '007', '12345', 'a.jpg', 'dsfadf', 10000, 0),
(14, '007', '12345', 'a.jpg', 'dsfadf', 10000, 0),
(15, '007', '12345', 'a.jpg', 'dsfadf', 10000, 0),
(16, '007', '12345', 'a.jpg', 'dsfadf', 10000, 0),
(17, '007', '12345', 'a.jpg', 'dsfadf', 10000, 0),
(18, '007', '12345', 'a.jpg', 'dsfadf', 10000, 0),
(19, '007', '12345', 'a.jpg', 'dsfadf', 10000, 0),
(20, '007', '12345', 'a.jpg', 'dsfadf', 10000, 0),
(21, '007', '12345', 'a.jpg', 'dsfadf', 10000, 0),
(22, '007', '12345', 'a.jpg', 'dsfadf', 10000, 0),
(23, '007', '12345', 'a.jpg', 'dsfadf', 10000, 0),
(24, '007', '12345', '6789jpg', 'dsfadf', 10000, 6789),
(25, '007', '12345', '6789jpg', 'dsfadf', 10000, 6789),
(26, '007', '12345', '6789jpg', 'dsfadf', 10000, 6789),
(27, '007', '12345', 'image6789jpg', 'dsfadf', 10000, 6789),
(28, '007', '12345', '6789jpg', 'dsfadf', 10000, 6789),
(29, '007', '12345', '6789jpg', 'dsfadf', 10000, 6789),
(30, '007', '12345', '6789jpg', 'dsfadf', 10000, 6789),
(31, '007', '12345', '6789jpg', 'dsfadf', 10000, 6789),
(32, '007', '12345', '6789jpg', 'dsfadf', 10000, 6789),
(33, '007', '12345', '6789.jpg', 'dsfadf', 10000, 6789),
(34, '007', '12345', '6789.jpg', 'dsfadf', 10000, 6789),
(35, '007', '12345', '6789.jpg', 'dsfadf', 10000, 6789),
(36, '007', '12345', '6789.jpg', 'dsfadf', 10000, 6789),
(37, '007', '12345', '4554.jpg', 'dsfadf', 1000, 4554),
(38, '007', '12345', '4554.jpg', 'dsfadf', 500, 4554),
(39, '007', '12345', '4554.jpg', 'dsfadf', 2000, 4554),
(40, '007', '12345', '4554.jpg', 'dsfadf', 5000, 4554),
(41, '007', '12345', '4554.jpg', 'dsfadf', 50000, 4554),
(42, '007', '12345', '4554.jpg', 'dsfadf', 5000, 4554);

-- --------------------------------------------------------

--
-- Table structure for table `details`
--

CREATE TABLE IF NOT EXISTS `details` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `acc` varchar(500) NOT NULL,
  `name` varchar(500) NOT NULL,
  `email` varchar(500) NOT NULL,
  `phone` int(30) NOT NULL,
  `balance` int(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `details`
--

INSERT INTO `details` (`id`, `acc`, `name`, `email`, `phone`, `balance`) VALUES
(1, '12345', 'Sunny Narang', 'narangsunny65@gmail.com', 2147483647, 453500),
(2, '007', 'Shriram Choubey', 'Src.work2015@gmail.com', 2147483647, 657498),
(3, '123456789', 'Saurav Dwivedi', 'chill.saurav@gmail.com', 2147483647, 1000),
(4, '1234567891012345', 'SRC7', 'src.work2015@gmail.com', 2147483647, 999996),
(5, '567', 'a', 'a@a.com', 65, 999998),
(6, '897', 'shriram', 'src.work2015@gm.com', 123456, 500000),
(7, '0', 'a', 'a@a.com', 2147483647, 5000000);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `acc` varchar(500) NOT NULL,
  `password` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `acc`, `password`) VALUES
(1, '12345', '123'),
(2, '007', '123'),
(3, '123456789', 'Saurav'),
(4, '1234567891012345', 'src7'),
(5, '567', 'a'),
(6, '897', '123'),
(7, '0', '123');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
