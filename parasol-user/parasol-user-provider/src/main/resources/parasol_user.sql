/*
Navicat MySQL Data Transfer

Source Server         : 192.168.101.131
Source Server Version : 50517
Source Host           : 192.168.101.131:3306
Source Database       : parasol_user

Target Server Type    : MYSQL
Target Server Version : 50517
File Encoding         : 65001

Date: 2016-01-14 14:29:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_login_third`
-- ----------------------------
DROP TABLE IF EXISTS `tb_login_third`;
CREATE TABLE `tb_login_third` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '个人用户id和组织用户id',
  `open_id` varchar(100) NOT NULL COMMENT 'openid即为tb_user表中的通行证。',
  `login_type` int(3) NOT NULL DEFAULT '0' COMMENT '登录类型 100:QQ登录，200:新浪微博登录',
  `expiresday` int(10) DEFAULT NULL COMMENT '过期时间',
  `accesstoken` varchar(100) NOT NULL COMMENT '访问会话',
  `head_pic` varchar(200) NOT NULL,
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '修改时间',
  `ip` varchar(16) NOT NULL DEFAULT '' COMMENT '用户IP',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`open_id`) USING BTREE,
  KEY `Index_1` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='第三方登录';


-- ----------------------------
-- Table structure for `tb_organ_basic`
-- ----------------------------
DROP TABLE IF EXISTS `tb_organ_basic`;
CREATE TABLE `tb_organ_basic` (
  `user_id` bigint(20) NOT NULL COMMENT '组织用户id',
  `name` varchar(100) NOT NULL COMMENT '若为组织则为全称，不可更改；若为个人则为昵称，可修改。',
  `pic_id` bigint(20) NOT NULL COMMENT '组织LOGO图像ID',
  `auth` tinyint(4) NOT NULL COMMENT '审核是否通过: 认证状态-1 未进行认证 0认证进行中 1认证失败 2认证成功',
  `nameIndex` varchar(20) NOT NULL COMMENT '简拼音',
  `status` tinyint(4) NOT NULL COMMENT '1：正常；0：锁定；-1：注销 ；2： 删除',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  KEY `Index_1` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织用户基本信息';


-- ----------------------------
-- Table structure for `tb_organ_ext`
-- ----------------------------
DROP TABLE IF EXISTS `tb_organ_ext`;
CREATE TABLE `tb_organ_ext` (
  `user_id` bigint(20) NOT NULL COMMENT '组织用户id',
  `regFrom` varchar(20) DEFAULT NULL COMMENT '1. gintongweb、2.gintongapp',
  `brief` varchar(100) DEFAULT '' COMMENT '若为组织则为全称，不可更改；若为个人则为昵称，可修改。',
  `business_licence_pic_id` bigint(20) NOT NULL COMMENT '营业职照id',
  `shortName` varchar(50) NOT NULL COMMENT '简称：组织用户的简称或个人用户简称，组织用户可修改。',
  `stock_code` varchar(20) DEFAULT NULL COMMENT '证券代码',
  `phone` varchar(15) DEFAULT '',
  `orgType` varchar(20) DEFAULT '' COMMENT '组织类型 金融机构 一般企业 中介机构 政府机构 期刊报纸 研究机构 电视广播 互联网媒体',
  `nameFirst` varchar(20) NOT NULL COMMENT '组织首字母',
  `nameIndexAll` varchar(50) NOT NULL COMMENT '全拼音',
  `company_contacts_mobile` varchar(15) NOT NULL COMMENT '联系人电话 ',
  `company_contacts` varchar(20) NOT NULL COMMENT '联系人姓名',
  `idcard_front_pic_id` bigint(20) NOT NULL COMMENT '联系人身份证正面照片id',
  `idcard_back_pic_id` bigint(20) NOT NULL COMMENT '联系人身份证背面照片id',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '修改时间',
  `ip` varchar(16) NOT NULL COMMENT '用户IP',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织用户基本信息';


-- ----------------------------
-- Table structure for `tb_user_basic`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_basic`;
CREATE TABLE `tb_user_basic` (
  `user_id` bigint(20) NOT NULL COMMENT '个人用户id',
  `name` varchar(100) NOT NULL COMMENT '若为组织则为全称，不可更改；若为个人则为昵称，不可修改。',
  `sex` tinyint(4) DEFAULT NULL COMMENT '性别 男：1，女：2，0：保密',
  `mobile` varchar(15) DEFAULT NULL COMMENT '手机号',
  `auth` tinyint(4) DEFAULT NULL COMMENT '是否验证邮箱,1,验证,0,未验证',
  `pic_id` bigint(20) NOT NULL COMMENT '用户头像id',
  `company_name` varchar(50) DEFAULT '' COMMENT '公司',
  `status` tinyint(4) NOT NULL COMMENT '1：正常；0：锁定；-1：注销 ；2： 删除',
  `nameIndex` varchar(20) NOT NULL COMMENT '简拼音',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`),
  KEY `Index_1` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人用户基本资料';


-- ----------------------------
-- Table structure for `tb_user_contact_way`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_contact_way`;
CREATE TABLE `tb_user_contact_way` (
  `user_id` bigint(20) NOT NULL COMMENT '个人用户id',
  `cellphone` varchar(16) NOT NULL COMMENT '手机',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `weixin` varchar(50) DEFAULT '' COMMENT '微信',
  `qq` varchar(16) DEFAULT '' COMMENT 'QQ',
  `weibo` varchar(50) DEFAULT '' COMMENT '微博',
  `is_visible` tinyint(4) NOT NULL COMMENT '好友可见 1.公开，2.好友可见',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '修改时间',
  `ip` varchar(16) NOT NULL COMMENT '用户IP',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系方式';

-- ----------------------------
-- Records of tb_user_contact_way
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_user_defined`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_defined`;
CREATE TABLE `tb_user_defined` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `user_defined_model` varchar(255) DEFAULT '' COMMENT '自定义模块名',
  `user_defined_filed` varchar(255) DEFAULT '' COMMENT '自定义字段名',
  `user_defined_value` varchar(1000) NOT NULL COMMENT '自定义字段内容',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '修改时间',
  `ip` varchar(16) NOT NULL COMMENT '用户IP',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户自定义';


-- ----------------------------
-- Table structure for `tb_user_education_history`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_education_history`;
CREATE TABLE `tb_user_education_history` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '个人用户id',
  `school` varchar(255) DEFAULT '' COMMENT '学校',
  `major` varchar(255) DEFAULT '' COMMENT '专业',
  `degree` char(1) NOT NULL COMMENT '学历  0小学 1初中 2高中 3中专 4专科 5本科 6硕士 7博士',
  `begin_time` varchar(255) NOT NULL COMMENT '开始时间',
  `end_time` varchar(255) NOT NULL COMMENT '结束时间',
  `description` varchar(2024) DEFAULT '' COMMENT '描述',
  `is_visible` tinyint(4) DEFAULT '1' COMMENT '好友可见 1.公开，2.好友可见',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '修改时间',
  `ip` varchar(16) NOT NULL COMMENT '用户IP',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教育经历';


-- ----------------------------
-- Table structure for `tb_user_ext`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_ext`;
CREATE TABLE `tb_user_ext` (
  `user_id` bigint(20) NOT NULL COMMENT '个人用户id',
  `province_id` bigint(20) DEFAULT '0' COMMENT '省份id',
  `city_id` bigint(20) DEFAULT '0' COMMENT '城市id',
  `county_id` bigint(20) DEFAULT '0' COMMENT '县id',
  `third_industry_id` bigint(20) DEFAULT '0',
  `company_job` varchar(100) DEFAULT '' COMMENT '职位',
  `shortName` varchar(50) DEFAULT '' COMMENT '简称：组织用户的简称或个人用户简称，组织用户可修改。',
  `description` varchar(300) DEFAULT '',
  `nameFirst` varchar(20) NOT NULL COMMENT '姓名首字母',
  `nameIndexAll` varchar(50) NOT NULL COMMENT '全拼音',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '修改时间',
  `ip` varchar(16) NOT NULL DEFAULT '' COMMENT '用户IP',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人用户基本资料';


-- ----------------------------
-- Table structure for `tb_user_friendly`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_friendly`;
CREATE TABLE `tb_user_friendly` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `friend_id` bigint(20) NOT NULL COMMENT '组织/用户ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0:待审核 1:同意',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '成为好友时间',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`user_id`),
  KEY `Index_2` (`friend_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='好友人脉组织客户关系';


-- ----------------------------
-- Table structure for `tb_user_info`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_info`;
CREATE TABLE `tb_user_info` (
  `user_id` bigint(20) NOT NULL COMMENT '个人用户id',
  `birthday` date NOT NULL COMMENT '若为组织则为全称，不可更改；若为个人则为昵称，可修改。',
  `province_id` bigint(20) NOT NULL COMMENT '省份id',
  `city_id` bigint(20) NOT NULL COMMENT '城市id',
  `county_id` bigint(20) NOT NULL COMMENT '县id',
  `is_visible` tinyint(4) DEFAULT '1' COMMENT '好友可见 1.公开，2.好友可见',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '修改时间',
  `ip` varchar(16) NOT NULL COMMENT '用户IP',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个人情况';

-- ----------------------------
-- Records of tb_user_info
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_user_interest_industry`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_interest_industry`;
CREATE TABLE `tb_user_interest_industry` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '个人用户id和组织用户id',
  `first_industry_id` bigint(20) DEFAULT NULL COMMENT '一级行业ID',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '修改时间',
  `ip` varchar(16) NOT NULL DEFAULT '' COMMENT '用户IP',
  PRIMARY KEY (`id`),
  KEY `user_id_index` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户感兴趣行业';


-- ----------------------------
-- Table structure for `tb_user_login_register`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_login_register`;
CREATE TABLE `tb_user_login_register` (
  `id` bigint(20) NOT NULL,
  `passport` varchar(50) NOT NULL COMMENT '通行证：用户注册时使用的登录名',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机号',
  `email` varchar(100) DEFAULT '' COMMENT '邮箱',
  `user_name` varchar(32) DEFAULT '' COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `user_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1 组织用户，0.个人用户',
  `salt` varchar(40) DEFAULT NULL COMMENT '密码加密码',
  `source` varchar(20) NOT NULL COMMENT '来源于哪个APP注册的1. gintongweb、2.gintongapp',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '修改时间',
  `ip` varchar(16) NOT NULL DEFAULT '' COMMENT '用户IP',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_1` (`passport`),
  UNIQUE KEY `Index_4` (`user_name`),
  UNIQUE KEY `Index_3` (`email`),
  UNIQUE KEY `Index_2` (`mobile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户注册登录表';


-- ----------------------------
-- Table structure for `tb_user_org_per_cus_rel`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_org_per_cus_rel`;
CREATE TABLE `tb_user_org_per_cus_rel` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `friend_id` bigint(20) NOT NULL COMMENT '组织/用户/客户/人脉ID',
  `releation_type` tinyint(4) NOT NULL COMMENT '1.个人好友，2.组织好友，3.收藏的人脉，4.保存的人脉，5.好友转人脉，6.自己创建的人脉，7.保存的客户，8.收藏的客户，9.组织转客户，10.自己创建的客户',
  `name` varchar(100) DEFAULT '' COMMENT '好友备注名',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '成为好友时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `Index_2` (`friend_id`) USING BTREE,
  KEY `Index_3` (`name`),
  KEY `Index_1` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='好友人脉组织客户关系';


-- ----------------------------
-- Table structure for `tb_user_work_history`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_work_history`;
CREATE TABLE `tb_user_work_history` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '个人用户id',
  `inc_name` varchar(255) DEFAULT '' COMMENT '单位名称',
  `position` varchar(255) DEFAULT '' COMMENT '职务',
  `begin_time` varchar(255) DEFAULT '' COMMENT '开始时间',
  `end_time` varchar(255) DEFAULT '' COMMENT '结束时间',
  `description` varchar(2024) DEFAULT '' COMMENT '描述',
  `is_visible` tinyint(4) DEFAULT '1' COMMENT '好友可见 1.公开，2.好友可见',
  `ctime` bigint(20) NOT NULL COMMENT '创建时间',
  `utime` bigint(20) NOT NULL COMMENT '修改时间',
  `ip` varchar(16) NOT NULL COMMENT '用户IP',
  PRIMARY KEY (`id`),
  KEY `Index_1` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作经历';

-- ----------------------------
-- Records of tb_user_work_history
-- ----------------------------
