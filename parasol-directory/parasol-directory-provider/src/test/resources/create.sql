create database parasol_directory;
use parasol_directory;
CREATE TABLE `tb_directory_type` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `appId` bigint(20) NOT NULL COMMENT '应用ID',
  `name` varchar(250) NOT NULL COMMENT '分类名字 (人脉、组织、需求、知识），应用系统自己定义的分类',
  `updateAt` bigint(20) DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `appId_name` (`appId`,`name`) 
) ENGINE=InnoDB AUTO_INCREMENT=1307 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_directory` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `pid` bigint(20) NOT NULL COMMENT '父主键',
  `appId` bigint(20) NOT NULL COMMENT '应用ID',
  `userId` bigint(20) NOT NULL COMMENT '用户ID（Ower）',
  `name` varchar(50) NOT NULL COMMENT '目录名称',
  `nameIndex` varchar(250) DEFAULT NULL  COMMENT '拼音简评',
  `nameIndexAll` varchar(250) DEFAULT NULL COMMENT '全拼',
  `remark` varchar(250) DEFAULT NULL COMMENT '描述',
  `numberCode` varchar(250) DEFAULT NULL COMMENT '保存父亲爷爷。。。',
  `orderNo` int(4) DEFAULT '0' COMMENT '顺序',
  `typeId` bigint(20) NOT NULL COMMENT '分类名字 (人脉、组织、需求、知识），应用系统自己定义的分类',
  `updateAt` bigint(20) DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `appId_pid_userId_name` (`appId`, `pid`,`userId`,`name`) 
) ENGINE=InnoDB AUTO_INCREMENT=1307 DEFAULT CHARSET=utf8;


CREATE TABLE `tb_directory_source` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `directoryId` bigint(20) NOT NULL COMMENT '目录ID',
  `appId` bigint(20) NOT NULL COMMENT 'Source 的应用ID',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `sourceId` bigint(20) NOT NULL COMMENT '资源ID',
  `sourceType` int(20) NOT NULL COMMENT '资源类型 知识、人脉',
  `sourceUrl` varchar(250) DEFAULT NULL COMMENT '自己地址',
  `sourceTitle` varchar(100) DEFAULT NULL COMMENT '资源标题',
  `sourceData` varchar(250) DEFAULT NULL COMMENT '资源的关键数据，备用',   
  `invokeMethod` varchar(50) DEFAULT NULL COMMENT '资源可以使用的方法，备用',
  `createAt` bigint(20) DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `directoryId_appId_userId_sourceId_sourceType` (`directoryId`, `appId`,`userId`,`sourceId`,`sourceType`) 
) ENGINE=InnoDB AUTO_INCREMENT=1307 DEFAULT CHARSET=utf8;

INSERT INTO `tb_directory_type` VALUES ('2', '1', '人脉', '1456293335672');
INSERT INTO `tb_directory_type` VALUES ('3', '1', ' 客户', '1456383660925');
INSERT INTO `tb_directory_type` VALUES ('4', '1', '组织', '1456293335918');
INSERT INTO `tb_directory_type` VALUES ('7', '1', '需求', '1456293336450');
INSERT INTO `tb_directory_type` VALUES ('8', '1', '知识', '1456293336251');
INSERT INTO `tb_directory_type` VALUES ('9', '1', '事务', '1456293336251');
INSERT INTO `tb_directory_type` VALUES ('11', '1', '会面', '1456293336251');
