  /*
  Navicat MySQL Data Transfer

  Source Server         : hui_project
  Source Server Version : 50720
  Source Host           : localhost:3306
  Source Database       : hui_project

  Target Server Type    : MYSQL
  Target Server Version : 50720
  File Encoding         : 65001

  Date: 2018-06-04 16:58:09
  */

  SET FOREIGN_KEY_CHECKS=0;

  -- ----------------------------
  -- Table structure for dzdp_shop
  -- ----------------------------
  DROP TABLE IF EXISTS `dzdp_shop`;
  CREATE TABLE `dzdp_shop` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `shop_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '大众点评shopID',
    `city` varchar(64) NOT NULL DEFAULT '' COMMENT '城市',
    `category` varchar(64) NOT NULL DEFAULT '' COMMENT '类别',
    `shopName` varchar(128) NOT NULL DEFAULT '' COMMENT '店铺名称',
    `address` varchar(255) NOT NULL DEFAULT '' COMMENT '地址',
    `phone` varchar(255) NOT NULL DEFAULT '' COMMENT '联系电话',
    `stars` varchar(64) NOT NULL DEFAULT '' COMMENT '星级',
    `reviewCount` varchar(64) NOT NULL DEFAULT '' COMMENT '评论数',
    `avgPriceTitle` varchar(64) NOT NULL COMMENT '平均消费',
    `favors` varchar(64) NOT NULL DEFAULT '' COMMENT '口味',
    `envirment` varchar(64) NOT NULL DEFAULT '' COMMENT '环境',
    `service` varchar(64) NOT NULL DEFAULT '' COMMENT '服务',
    `openTime` varchar(255) NOT NULL DEFAULT '' COMMENT '营业时间',
    `introduce` text NOT NULL COMMENT '介绍',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
