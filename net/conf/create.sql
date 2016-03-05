/*
Navicat MySQL Data Transfer

Source Server         : myself
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : service

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2015-04-08 13:46:00
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `grouptbl`
-- ----------------------------
DROP TABLE IF EXISTS `grouptbl`;
CREATE TABLE `grouptbl` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 NOT NULL,
  `father` int(12) DEFAULT '0',
  `task` int(11) DEFAULT '50',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of grouptbl
-- ----------------------------
INSERT INTO `grouptbl` VALUES ('1', '行政', '0', '50');

-- ----------------------------
-- Table structure for `teltbl`
-- ----------------------------
DROP TABLE IF EXISTS `teltbl`;
CREATE TABLE `teltbl` (
  `name` varchar(32) NOT NULL,
  `telephone` varchar(32) NOT NULL,
  `userId` int(12) NOT NULL DEFAULT '0',
  PRIMARY KEY (`telephone`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of teltbl
-- ----------------------------

-- ----------------------------
-- Table structure for `usertbl`
-- ----------------------------
DROP TABLE IF EXISTS `usertbl`;
CREATE TABLE `usertbl` (
  `id` int(15) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 NOT NULL,
  `password` varchar(16) CHARACTER SET utf8 NOT NULL,
  `post` varchar(32) CHARACTER SET utf8 DEFAULT '""',
  `father` int(11) DEFAULT '0',
  `task` int(11) DEFAULT '0',
  `telephone` varchar(32) CHARACTER SET utf8 DEFAULT '""',
  `firstName` varchar(32) CHARACTER SET utf8 DEFAULT '""',
  `lastName` varchar(32) CHARACTER SET utf8 DEFAULT '""',
  `address` varchar(128) CHARACTER SET utf8 DEFAULT '""',
  `age` smallint(3) DEFAULT '16',
  `email` varchar(32) CHARACTER SET utf8 DEFAULT '""',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of usertbl
-- ----------------------------
INSERT INTO `usertbl` VALUES ('1', 'admin', '1234', '\0\0\0\0å¤‡æ³¨\0æˆ‘æ˜¯ç¨‹åºå‘˜', null, null, '', '', '', '', null, '');
INSERT INTO `usertbl` VALUES ('2', '123', '123456', '', '1', '50', '', '', '', '', '16', '');
