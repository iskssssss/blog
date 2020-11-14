/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 03/11/2020 20:50:43
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for b_article_basic
-- ----------------------------
DROP TABLE IF EXISTS `b_article_basic`;
CREATE TABLE `b_article_basic`  (
  `ab_aid` bigint(11) NOT NULL COMMENT '博文编号',
  `ab_title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博文标题',
  `ab_synopsis` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '简介',
  `ab_cover_image` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面图',
  `ab_uid` bigint(11) NOT NULL COMMENT '发表用户',
  `ab_publish_date` datetime NOT NULL COMMENT '发表时间',
  `ab_update_date` datetime NOT NULL COMMENT '更新时间',
  `ab_publish` tinyint(1) NOT NULL COMMENT '是否发布',
  PRIMARY KEY (`ab_aid`) USING BTREE,
  INDEX `a_uid`(`ab_uid`) USING BTREE,
  CONSTRAINT `用户-博文` FOREIGN KEY (`ab_uid`) REFERENCES `u_users` (`u_uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for b_article_detailed
-- ----------------------------
DROP TABLE IF EXISTS `b_article_detailed`;
CREATE TABLE `b_article_detailed`  (
  `ad_aid` bigint(11) NOT NULL COMMENT '博文编号',
  `ad_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '博文内容',
  `ad_type` tinyint(1) NOT NULL COMMENT '文章类型',
  `ad_open_comment` tinyint(1) NOT NULL COMMENT '是否开启评论',
  `ad_open_recommend` tinyint(1) NOT NULL COMMENT '是否推荐',
  `ad_like_count` bigint(10) NOT NULL COMMENT '点赞数',
  `ad_views` bigint(10) NOT NULL COMMENT '浏览数   评论数ad_comment_count',
  `ad_top` tinyint(1) NOT NULL COMMENT '是否置顶',
  PRIMARY KEY (`ad_aid`) USING BTREE,
  CONSTRAINT `博文` FOREIGN KEY (`ad_aid`) REFERENCES `b_article_basic` (`ab_aid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for b_article_labels
-- ----------------------------
DROP TABLE IF EXISTS `b_article_labels`;
CREATE TABLE `b_article_labels`  (
  `al_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `al_lid` bigint(11) NOT NULL COMMENT '标签编号',
  `al_aid` bigint(11) NOT NULL COMMENT '博文编号',
  PRIMARY KEY (`al_id`) USING BTREE,
  INDEX `al_aid`(`al_aid`) USING BTREE,
  INDEX `标签-标签`(`al_lid`) USING BTREE,
  CONSTRAINT `标签-博文` FOREIGN KEY (`al_aid`) REFERENCES `b_article_basic` (`ab_aid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `标签-标签` FOREIGN KEY (`al_lid`) REFERENCES `b_labels` (`l_lid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for b_article_sort
-- ----------------------------
DROP TABLE IF EXISTS `b_article_sort`;
CREATE TABLE `b_article_sort`  (
  `as_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `as_sid` bigint(11) NOT NULL COMMENT '分类编号',
  `as_aid` bigint(11) NOT NULL COMMENT '博文编号',
  PRIMARY KEY (`as_id`) USING BTREE,
  INDEX `as_aid`(`as_aid`) USING BTREE,
  INDEX `分类-分类`(`as_sid`) USING BTREE,
  CONSTRAINT `分类-分类` FOREIGN KEY (`as_sid`) REFERENCES `b_sorts` (`s_sid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `分类-博文` FOREIGN KEY (`as_aid`) REFERENCES `b_article_basic` (`ab_aid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 58 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for b_comments
-- ----------------------------
DROP TABLE IF EXISTS `b_comments`;
CREATE TABLE `b_comments`  (
  `c_cid` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `c_uid` bigint(11) NOT NULL COMMENT '用户id',
  `c_aid` bigint(11) NOT NULL COMMENT '博文id',
  `c_like_count` bigint(11) NOT NULL COMMENT '点赞数',
  `c_date` datetime NOT NULL COMMENT '评论日期',
  `c_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `c_father_cid` bigint(11) NULL DEFAULT NULL COMMENT '评论父id',
  `c_reply_uid` bigint(11) NULL DEFAULT NULL COMMENT '被回复用户id',
  PRIMARY KEY (`c_cid`) USING BTREE,
  INDEX `c_uid`(`c_uid`) USING BTREE,
  INDEX `c_aid`(`c_aid`) USING BTREE,
  CONSTRAINT `博文-评论` FOREIGN KEY (`c_aid`) REFERENCES `b_article_basic` (`ab_aid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for b_comments_user
-- ----------------------------
DROP TABLE IF EXISTS `b_comments_user`;
CREATE TABLE `b_comments_user`  (
  `cu_uid` bigint(11) NOT NULL,
  `cu_username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `cu_email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `cu_website` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '网址',
  PRIMARY KEY (`cu_uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for b_labels
-- ----------------------------
DROP TABLE IF EXISTS `b_labels`;
CREATE TABLE `b_labels`  (
  `l_lid` bigint(11) NOT NULL COMMENT '标签id',
  `l_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名',
  `l_alias` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `l_description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '标签描述',
  `l_add_date` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`l_lid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for b_sorts
-- ----------------------------
DROP TABLE IF EXISTS `b_sorts`;
CREATE TABLE `b_sorts`  (
  `s_sid` bigint(10) NOT NULL COMMENT '分类id',
  `s_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标签名',
  `s_alias` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `s_description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '分类描述',
  `s_add_date` datetime NOT NULL COMMENT '添加时间',
  PRIMARY KEY (`s_sid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for u_auths
-- ----------------------------
DROP TABLE IF EXISTS `u_auths`;
CREATE TABLE `u_auths`  (
  `a_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `a_uid` bigint(11) NOT NULL COMMENT '用户id',
  `a_type` tinyint(1) NOT NULL COMMENT '登录类型(0：uid 1：手机)',
  `a_identifier` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份标识',
  `a_credential` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录凭证',
  `a_salt` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐值',
  `a_verification` tinyint(1) NOT NULL COMMENT '是否验证',
  PRIMARY KEY (`a_id`) USING BTREE,
  INDEX `登录`(`a_uid`) USING BTREE,
  CONSTRAINT `登录` FOREIGN KEY (`a_uid`) REFERENCES `u_users` (`u_uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 84 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for u_login_log
-- ----------------------------
DROP TABLE IF EXISTS `u_login_log`;
CREATE TABLE `u_login_log`  (
  `ll_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `ll_uid` bigint(11) NOT NULL COMMENT '账号',
  `ll_date` datetime NOT NULL COMMENT '登陆时间',
  `ll_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登陆地址',
  `ll_type` tinyint(2) NOT NULL COMMENT '登陆方式',
  PRIMARY KEY (`ll_id`) USING BTREE,
  INDEX `ll_uid`(`ll_uid`) USING BTREE,
  CONSTRAINT `用户登录` FOREIGN KEY (`ll_uid`) REFERENCES `u_users` (`u_uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 234 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for u_register_log
-- ----------------------------
DROP TABLE IF EXISTS `u_register_log`;
CREATE TABLE `u_register_log`  (
  `rl_id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '自增序号',
  `rl_uid` bigint(11) NOT NULL COMMENT '注册用户',
  `rl_date` datetime NOT NULL COMMENT '注册时间',
  `rl_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '注册地址',
  PRIMARY KEY (`rl_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for u_users
-- ----------------------------
DROP TABLE IF EXISTS `u_users`;
CREATE TABLE `u_users`  (
  `u_uid` bigint(11) NOT NULL COMMENT '账号',
  `u_nickname` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '昵称',
  `u_gender` tinyint(1) NOT NULL COMMENT '性别',
  `u_birthday` datetime NULL DEFAULT NULL COMMENT '生日',
  `u_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机',
  `u_email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `u_location` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在地',
  `u_hometown` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '故乡',
  `u_company` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司',
  `u_school` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '学校',
  `u_occupation` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职业',
  `u_status` tinyint(1) NOT NULL COMMENT '用户状态',
  PRIMARY KEY (`u_uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for u_users_online
-- ----------------------------
DROP TABLE IF EXISTS `u_users_online`;
CREATE TABLE `u_users_online`  (
  `uo_session_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会话id',
  `uo_uid` bigint(11) NOT NULL COMMENT '用户ID',
  `uo_ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登陆地址',
  `uo_browser` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器类型',
  `uo_os` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `uo_start_date` datetime NOT NULL COMMENT '创建时间',
  `uo_last_date` datetime NOT NULL COMMENT '最后访问时间',
  `uo_expire_time` bigint(8) NOT NULL COMMENT '过期时间',
  `uo_status` tinyint(1) NOT NULL COMMENT '帐号状态',
  PRIMARY KEY (`uo_session_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
