/*
Navicat MySQL Data Transfer

Source Server         : 192.168.101.131
Source Server Version : 50517
Source Host           : 192.168.101.131:3306
Source Database       : parasol_oauth2

Target Server Type    : MYSQL
Target Server Version : 50517
File Encoding         : 65001

Date: 2016-01-04 10:16:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `oauth_access_token`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `token_id` varchar(255) DEFAULT NULL COMMENT '该字段的值是将access_token的值通过MD5加密后存储的.',
  `token` blob COMMENT '存储将OAuth2AccessToken.java对象序列化后的二进制数据, 是真实的AccessToken的数据值.',
  `authentication_id` varchar(255) NOT NULL DEFAULT '' COMMENT '该字段具有唯一性, 其值是根据当前的username(如果有),client_id与scope通过MD5加密生成的. 具体实现请参考DefaultAuthenticationKeyGenerator.java类.',
  `user_name` varchar(255) DEFAULT NULL COMMENT '登录时的用户名, 若客户端没有用户名(如grant_type="client_credentials"),则该值等于client_id',
  `client_id` varchar(255) DEFAULT NULL,
  `authentication` blob COMMENT '存储将OAuth2Authentication.java对象序列化后的二进制数据.',
  `refresh_token` varchar(255) DEFAULT NULL COMMENT '该字段的值是将refresh_token的值通过MD5加密后存储的.',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `authentication_id_index` (`authentication_id`) USING BTREE,
  KEY `token_id_index` (`token_id`),
  KEY `user_name_index` (`user_name`),
  KEY `client_id_index` (`client_id`),
  KEY `refresh_token_index` (`refresh_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `oauth_approvals`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `lastModifiedAt` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `oauth_client_details`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `client_id` varchar(255) NOT NULL COMMENT '客户端(client), 在实际应用中的另一个名称叫appKey,与client_id是同一个概念',
  `resource_ids` varchar(255) DEFAULT NULL COMMENT '客户端所能访问的资源id集合,多个资源时用逗号(,)',
  `client_secret` varchar(255) DEFAULT NULL COMMENT '用于指定客户端(client)的访问密匙,由服务端自动生成',
  `company_name` varchar(50) DEFAULT NULL,
  `application_name` varchar(50) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL COMMENT '指定客户端申请的权限范围,可选值包括read,write,trust;若有多个权限范围用逗号(,)分隔,如: "read,write".',
  `authorized_grant_types` varchar(255) DEFAULT NULL COMMENT '指定客户端支持的grant_type,可选值包括authorization_code,password,refresh_token,implicit,client_credentials, 若支持多个grant_type用逗号(,)分隔,如: "authorization_code,password".',
  `web_server_redirect_uri` varchar(255) DEFAULT NULL COMMENT '客户端的重定向URI,在Oauth的流程中会使用并检查与注册时填写的redirect_uri是否一致',
  `authorities` varchar(255) DEFAULT NULL COMMENT '指定客户端所拥有的Spring Security的权限值',
  `access_token_validity` int(11) DEFAULT NULL COMMENT '设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时). ',
  `refresh_token_validity` int(11) DEFAULT NULL COMMENT '设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天). ',
  `additional_information` text COMMENT '这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,',
  `autoapprove` varchar(255) DEFAULT 'false' COMMENT '设置用户是否自动Approval操作, 默认值为 ''false'', 可选值包括 ''true'',''false'', ''read'',''write''. ',
  `archived` tinyint(1) DEFAULT '0',
  `trusted` tinyint(1) DEFAULT '0' COMMENT '设置客户端是否为受信任的,默认为''0''(即不受信任的,1为受信任的). ',
  `ctime` bigint(20) NOT NULL,
  `utime` bigint(20) NOT NULL,
  `ip` varchar(16) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_client_id` (`client_id`) USING BTREE,
  UNIQUE KEY `index_client__security` (`client_secret`) USING BTREE,
  UNIQUE KEY `index_application_name` (`application_name`) USING BTREE,
  KEY `index_company_name` (`company_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `oauth_code`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
  `code` varchar(255) DEFAULT NULL COMMENT '存储服务端系统生成的code的值(未加密).',
  `authentication` blob COMMENT '存储将AuthorizationRequestHolder.java对象序列化后的二进制数据.',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)',
  KEY `code_index` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `oauth_refresh_token`
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `token_id` varchar(255) DEFAULT NULL COMMENT '该字段的值是将refresh_token的值通过MD5加密后存储的.',
  `token` blob COMMENT '存储将OAuth2RefreshToken.java对象序列化后的二进制数据.',
  `authentication` blob COMMENT '存储将OAuth2Authentication.java对象序列化后的二进制数据.',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据的创建时间,精确到秒,由数据库在插入数据时取当前系统时间自动生成(扩展字段)',
  PRIMARY KEY (`id`),
  KEY `token_id_index` (`token_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

