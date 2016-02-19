create database parasol_favorite;
use parasol_favorite;
CREATE TABLE `tb_favorite_type` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `appId` bigint(20) NOT NULL COMMENT '应用ID',
  `name` varchar(250) NOT NULL COMMENT '分类名字 (人脉、组织、需求、知识），应用系统自己定义的分类',
  `updateAt` bigint(20) DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `appId_name` (`appId`,`name`) 
) ENGINE=InnoDB AUTO_INCREMENT=1307 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_favorite` (
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


CREATE TABLE `tb_favorite_source` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `favoriteId` bigint(20) NOT NULL COMMENT '目录ID',
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
  UNIQUE INDEX `favoriteId_appId_userId_sourceId_sourceType` (`favoriteId`, `appId`,`userId`,`sourceId`,`sourceType`) 
) ENGINE=InnoDB AUTO_INCREMENT=1307 DEFAULT CHARSET=utf8;


