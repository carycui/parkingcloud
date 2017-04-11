CREATE DATABASE `db_parking_could2`;

USE `db_parking_could2`;

CREATE TABLE `t_user` (
  `uid` varchar(36) NOT NULL,
  `phoneNumber` varchar(16) NOT NULL,
  `firstName` varchar(45) NOT NULL DEFAULT '',
  `lastName` varchar(45) NOT NULL DEFAULT '',
  `email` varchar(45) NOT NULL DEFAULT '',
  `sex` tinyint(1) NOT NULL DEFAULT '0',
  `birthday` date DEFAULT NULL,
  `address` varchar(256) NOT NULL DEFAULT '',
  `imageUrl` varchar(256) NOT NULL DEFAULT '',
  `password` varchar(128) DEFAULT NULL,
  `passwordResetTime` datetime NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  `locked` tinyint(1) NOT NULL DEFAULT '0',
  `updateTime` datetime NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`uid`,`phoneNumber`),
  UNIQUE KEY `uuid_UNIQUE` (`uid`),
  UNIQUE KEY `phoneNumber_UNIQUE` (`phoneNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;