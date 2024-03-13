/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : logistics_robot_manager

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 13/03/2024 16:33:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `goods_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '货物id',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '货物名称',
  `goods_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '货物状态标识，0为未运输，1为运输中，2为已在架',
  `goods_type_id` bigint UNSIGNED NOT NULL COMMENT '货物类型id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '货物的生产时间',
  `shelving_time` timestamp(0) NULL DEFAULT NULL COMMENT '货物的上架时间',
  `details` varchar(10000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '货物明细',
  `shelf_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '所在货架的id',
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '测试货物1', 0, 1, '2024-03-11 17:54:16', NULL, NULL, NULL);
INSERT INTO `goods` VALUES (2, '测试货物2', 0, 2, '2024-03-11 17:54:34', NULL, NULL, NULL);
INSERT INTO `goods` VALUES (3, '测试货物3', 0, 1, '2024-03-11 17:54:54', NULL, NULL, NULL);
INSERT INTO `goods` VALUES (4, 'test1', 0, 2, '2024-03-11 23:41:43', NULL, NULL, NULL);
INSERT INTO `goods` VALUES (5, '测试货物4', 0, 3, '2024-03-11 23:42:08', NULL, NULL, NULL);
INSERT INTO `goods` VALUES (6, '测试货物5', 0, 4, '2024-03-11 23:42:54', NULL, NULL, NULL);

-- ----------------------------
-- Table structure for goods_type
-- ----------------------------
DROP TABLE IF EXISTS `goods_type`;
CREATE TABLE `goods_type`  (
  `goods_type_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '货物类型id',
  `goods_type_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '货物类型名称',
  PRIMARY KEY (`goods_type_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_type
-- ----------------------------
INSERT INTO `goods_type` VALUES (1, '类型一');
INSERT INTO `goods_type` VALUES (2, '类型二');

-- ----------------------------
-- Table structure for shelf
-- ----------------------------
DROP TABLE IF EXISTS `shelf`;
CREATE TABLE `shelf`  (
  `shelf_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '货架id',
  `shelf_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '货架名称',
  `shelf_status` tinyint(1) NULL DEFAULT 0 COMMENT '货架状态，0为无货物，1为有货物，2为已满',
  `shelf_type` int NULL DEFAULT NULL COMMENT '货架类型，1为small，2为middle，3为large',
  `goods_amount` int NULL DEFAULT 0 COMMENT '货架存放的货物数量',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '激活时间',
  PRIMARY KEY (`shelf_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of shelf
-- ----------------------------
INSERT INTO `shelf` VALUES (1, 'test1', 1, 1, 0, '2024-03-06 16:18:07');
INSERT INTO `shelf` VALUES (2, 'test2', 1, 2, 0, '2024-03-06 16:18:34');
INSERT INTO `shelf` VALUES (3, 'test3', 1, 2, 0, '2024-03-06 16:18:50');
INSERT INTO `shelf` VALUES (4, '11111', 0, 2, 0, '2024-03-06 21:11:37');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `user_type` tinyint(1) NULL DEFAULT 1 COMMENT '用户类型标识，1为普通用户，2为管理员',
  `is_active` tinyint(1) NULL DEFAULT 1 COMMENT '用户状态标识，0为被禁用，1为活跃',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `login_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '最近一次登录时间',
  `task_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '最近一次执行任务的任务id',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'test', '$2a$10$F9jzm.TB2uUmH4PEwv08XuDVqNu9dzwZA5WCLAHAVHOvVrOGXl2Ii', '475364602@qq.com', 1, 1, '2024-03-07 22:32:43', '2024-03-07 22:38:51', NULL);
INSERT INTO `user` VALUES (2, 'test2', '$2a$10$hX7rJembgpnZe4BT3apo3u00k9.0KVBW1xmK8hlnyh5HAlMcH/mi.', 'ysx1461459810@163.com', 1, 1, '2024-03-11 16:46:37', '2024-03-11 16:47:29', NULL);

SET FOREIGN_KEY_CHECKS = 1;
