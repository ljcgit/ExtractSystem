/*
 Navicat Premium Data Transfer

 Source Server         : wampMysql
 Source Server Type    : MySQL
 Source Server Version : 50714
 Source Host           : localhost:3306
 Source Schema         : extract_system

 Target Server Type    : MySQL
 Target Server Version : 50714
 File Encoding         : 65001

 Date: 09/05/2019 00:07:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `url` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '超级管理员', '/');
INSERT INTO `permission` VALUES (2, '新建任务', '/NewTask');
INSERT INTO `permission` VALUES (3, '单组抽取', '/SingleExtract');
INSERT INTO `permission` VALUES (4, '多组抽取', '/MultipleExtract');
INSERT INTO `permission` VALUES (5, '人员导出', '/OutExcelServlet');
INSERT INTO `permission` VALUES (6, '任务视图', '/views/NewTask.jsp');

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stid` int(11) NOT NULL,
  `sqid` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (50, 10, 87);
INSERT INTO `record` VALUES (49, 7, 87);
INSERT INTO `record` VALUES (48, 9, 87);
INSERT INTO `record` VALUES (47, 6, 87);
INSERT INTO `record` VALUES (45, 11, 86);
INSERT INTO `record` VALUES (46, 12, 86);

-- ----------------------------
-- Table structure for squad
-- ----------------------------
DROP TABLE IF EXISTS `squad`;
CREATE TABLE `squad`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` int(11) NOT NULL,
  `total` int(11) NOT NULL,
  `man` int(11) NULL DEFAULT NULL,
  `woman` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_id`(`id`) USING BTREE,
  INDEX `index_tid`(`tid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 90 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of squad
-- ----------------------------
INSERT INTO `squad` VALUES (89, 4, 2, 1, 1);
INSERT INTO `squad` VALUES (88, 4, 2, 1, 1);
INSERT INTO `squad` VALUES (87, 3, 4, 2, 2);

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '男' COMMENT '性别',
  `phone` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `status` int(3) NOT NULL DEFAULT 0 COMMENT '0：可选 1：已请假 2：已参加',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES (1, 'qwe', '男', '12347897', 1);
INSERT INTO `staff` VALUES (2, 'erf', '男', '564556', 1);
INSERT INTO `staff` VALUES (3, 'hhkj', '女', '5466', 1);
INSERT INTO `staff` VALUES (6, 'drg', '男', '5646', 2);
INSERT INTO `staff` VALUES (7, 'try', '女', '5646', 2);
INSERT INTO `staff` VALUES (8, 'cb', '男', '5646', 2);
INSERT INTO `staff` VALUES (9, 'ipo', '男', '5646', 2);
INSERT INTO `staff` VALUES (10, 'uo', '女', '5646', 2);
INSERT INTO `staff` VALUES (11, 'jhk', '男', '5646', 2);
INSERT INTO `staff` VALUES (12, 'zxc', '女', '5646', 2);
INSERT INTO `staff` VALUES (13, 'bnm', '男', '5646', 2);
INSERT INTO `staff` VALUES (20, '小明', '男', '5646', 2);
INSERT INTO `staff` VALUES (18, '小红', '女', '5646', 2);
INSERT INTO `staff` VALUES (19, '小李', '女', '5646', 2);
INSERT INTO `staff` VALUES (21, '小刚', '男', '5646', 2);

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `startDate` datetime(0) NULL DEFAULT NULL,
  `endDate` datetime(0) NULL DEFAULT NULL,
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '',
  `status` int(2) NULL DEFAULT 0 COMMENT '0：未抽取   1：已抽取',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES (1, '士大夫', '2018-04-25 00:00:00', '2018-10-01 00:00:00', '阿斯达', 0);
INSERT INTO `task` VALUES (2, '文化科技', '2019-04-24 00:00:00', '2020-01-01 00:00:00', '数据库', 0);
INSERT INTO `task` VALUES (3, '搜集和', '2020-01-01 00:00:00', '2020-10-01 00:00:00', '数控就', 1);
INSERT INTO `task` VALUES (4, '时代', '2020-10-01 00:00:00', '2021-10-01 00:00:00', '刷卡积分', 0);
INSERT INTO `task` VALUES (10, '志愿服务', '2018-10-01 00:00:00', '2019-10-01 00:00:00', 'sdfwes', 0);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin');
INSERT INTO `user` VALUES (2, 'administrator', '123456');

-- ----------------------------
-- Table structure for user_permission
-- ----------------------------
DROP TABLE IF EXISTS `user_permission`;
CREATE TABLE `user_permission`  (
  `uid` int(11) NOT NULL,
  `pid` int(11) NOT NULL
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of user_permission
-- ----------------------------
INSERT INTO `user_permission` VALUES (2, 1);
INSERT INTO `user_permission` VALUES (1, 2);
INSERT INTO `user_permission` VALUES (1, 3);
INSERT INTO `user_permission` VALUES (1, 4);
INSERT INTO `user_permission` VALUES (1, 5);
INSERT INTO `user_permission` VALUES (1, 6);

SET FOREIGN_KEY_CHECKS = 1;
