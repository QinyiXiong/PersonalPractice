/*
 Navicat MySQL Data Transfer

 Source Server         : 税务云平台
 Source Server Type    : MySQL
 Source Server Version : 50727
 Source Host           : 192.168.248.157:3306
 Source Schema         : cloudgo-business

 Target Server Type    : MySQL
 Target Server Version : 50727
 File Encoding         : 65001

 Date: 15/10/2019 21:29:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for CG_CREDIT_DETAIL
-- ----------------------------
DROP TABLE IF EXISTS `CG_CREDIT_DETAIL`;
CREATE TABLE `CG_CREDIT_DETAIL`  (
  `F_GUID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'GUID\r\n',
  `F_ZTLX` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主体类型（法人）',
  `F_ZZJGDM` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组织机构代码/身份证号',
  `F_XZXK` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行政许可（计数）',
  `F_XZCF` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '行政处罚（计数）',
  `F_SXHMD` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '守信红名单（计数）',
  `F_HMD` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '失信黑名单（计数）',
  `F_ZDGZMD` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '重点关注名单（计数）',
  `F_FROM` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据来源 eg:信用中国'
) ENGINE = InnoDB CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
