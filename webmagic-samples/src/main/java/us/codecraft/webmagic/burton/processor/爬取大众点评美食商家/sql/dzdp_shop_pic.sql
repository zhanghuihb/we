/*
Navicat MySQL Data Transfer

Source Server         : hui_project
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : hui_project

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2018-06-04 16:58:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dzdp_shop_pic
-- ----------------------------
DROP TABLE IF EXISTS `dzdp_shop_pic`;
CREATE TABLE `dzdp_shop_pic` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `shop_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '大众点评shopId',
  `category` varchar(64) NOT NULL DEFAULT '' COMMENT '图片分类',
  `picName` varchar(64) NOT NULL DEFAULT '' COMMENT '图片名称',
  `piUrl` varchar(255) NOT NULL DEFAULT '' COMMENT '图片url',
  `uploader` varchar(64) NOT NULL DEFAULT '' COMMENT '上传者',
  `uploadTime` varchar(128) NOT NULL DEFAULT '' COMMENT '上传时间',
  `uploadEquipment` varchar(64) NOT NULL DEFAULT '' COMMENT '上传设备',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
