-- phpMyAdmin SQL Dump
-- version 4.6.5.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 15, 2018 at 12:10 PM
-- Server version: 10.1.21-MariaDB
-- PHP Version: 7.1.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `makechat_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE `messages` (
  `message_id` bigint(20) NOT NULL,
  `user_name` varchar(20) NOT NULL,
  `message` text NOT NULL,
  `date` varchar(50) NOT NULL,
  `server_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Dumping data for table `messages`
--

INSERT INTO `messages` (`message_id`, `user_name`, `message`, `date`, `server_id`) VALUES
(1, '@assistant', 'testing app', '2018-01-15, 02:45', 1),
(2, '@sarafinmahtab', 'is everything ok???', '2018-01-15, 02:45', 1),
(3, '@assistant', 'yaaahhh', '2018-01-15, 02:45', 1),
(4, '@sarafinmahtab', 'okay carry on!!', '2018-01-15, 02:46', 1),
(5, '@developer', 'do i need to fix any bug???', '2018-01-15, 02:46', 1),
(6, '@assistant', 'no need bro', '2018-01-15, 02:47', 1),
(7, '@assistant', '(y)', '2018-01-15, 02:48', 1),
(8, '@sarafinmahtab', 'hiiiiiii', '2018-01-15, 11:49', 3),
(9, '@professor', 'yooooooo', '2018-01-15, 11:49', 3),
(10, '@sarafinmahtab', '???', '2018-01-15, 11:50', 3),
(11, '@professor', ':D', '2018-01-15, 11:50', 3),
(12, '@professor', 'hahahahhahahha', '2018-01-15, 11:53', 3),
(13, '@sarafinmahtab', 'hey professor', '2018-01-15, 11:53', 3),
(14, '@risad', 'hello', '2018-01-15, 11:58', 3),
(15, '@hamayet', 'yoooo', '2018-01-15, 11:58', 3),
(16, '@risad', 'asasasas', '2018-01-15, 11:58', 3),
(17, '@sarafinmahtab', 'hiiiii', '2018-01-15, 13:59', 3),
(18, '@mahtab', 'hello', '2018-01-15, 13:59', 3);

-- --------------------------------------------------------

--
-- Table structure for table `server`
--

CREATE TABLE `server` (
  `server_id` int(11) NOT NULL,
  `server_url` varchar(50) NOT NULL,
  `port_number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

--
-- Dumping data for table `server`
--

INSERT INTO `server` (`server_id`, `server_url`, `port_number`) VALUES
(1, '192.168.0.63', 3000),
(2, '192.168.0.63', 3005),
(3, '10.100.166.138', 3000),
(4, '10.100.168.69', 3000);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `messages`
--
ALTER TABLE `messages`
  ADD PRIMARY KEY (`message_id`);

--
-- Indexes for table `server`
--
ALTER TABLE `server`
  ADD PRIMARY KEY (`server_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `messages`
--
ALTER TABLE `messages`
  MODIFY `message_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `server`
--
ALTER TABLE `server`
  MODIFY `server_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
