/*
 Navicat Premium Data Transfer

 Source Server         : 1
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : timeless_chat_websocket

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 27/06/2023 03:15:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for timeless_chat_user
-- ----------------------------
DROP TABLE IF EXISTS `timeless_chat_user`;
CREATE TABLE `timeless_chat_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户昵称',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '用户头像',
  `user_state` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户状态(在线、离线)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of timeless_chat_user
-- ----------------------------
INSERT INTO `timeless_chat_user` VALUES (1, 'admin', '清风故人来', 'timeless', 'file:///F:/secret/%E4%BA%BA%E7%89%A9%E5%8F%82%E8%80%83/%E6%9A%97%E7%BD%91%E5%B0%8F%E4%BA%BA.jpg', '');
INSERT INTO `timeless_chat_user` VALUES (2, 'timeless', '梨花不等故人来', 'timeless', 'file:///F:/secret/%E4%BA%BA%E7%89%A9%E5%8F%82%E8%80%83/%E6%9A%97%E7%BD%91%E5%B0%8F%E4%BA%BA.jpg', '');
INSERT INTO `timeless_chat_user` VALUES (3, 'wwwjwwwj', '微我无酒', 'timeless', 'file:///F:/secret/%E4%BA%BA%E7%89%A9%E5%8F%82%E8%80%83/%E6%9A%97%E7%BD%91%E5%B0%8F%E4%BA%BA.jpg', '');
INSERT INTO `timeless_chat_user` VALUES (4, '1811512', '1811512', 'timeless', '', '');
INSERT INTO `timeless_chat_user` VALUES (5, '178260', '178260', 'timeless', '', '');

-- ----------------------------
-- Table structure for timeless_friend
-- ----------------------------
DROP TABLE IF EXISTS `timeless_friend`;
CREATE TABLE `timeless_friend`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `from_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主动加的人的用户名',
  `to_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '被加的人的用户名',
  `is_friend` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否是好友（0：否  1：是  2: 申请中）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of timeless_friend
-- ----------------------------
INSERT INTO `timeless_friend` VALUES (1, 'admin', 'timeless', 0);
INSERT INTO `timeless_friend` VALUES (2, 'wwwjwwwj', 'timeless', 0);

-- ----------------------------
-- Table structure for timeless_private_message
-- ----------------------------
DROP TABLE IF EXISTS `timeless_private_message`;
CREATE TABLE `timeless_private_message`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `from_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '发消息者',
  `to_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '接收消息者',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '消息内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 315 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of timeless_private_message
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
