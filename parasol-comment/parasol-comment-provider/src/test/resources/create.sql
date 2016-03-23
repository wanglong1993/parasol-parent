create database parasol_comment;
use parasol_comment;

CREATE TABLE `tb_comment_type` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `appId` bigint(20) NOT NULL COMMENT '应用ID',
  `name` varchar(250) NOT NULL COMMENT '分类名字 (人脉、组织、需求、知识），应用系统自己定义的分类',
  `updateAt` bigint(20) DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `appId_name` (`appId`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1307 DEFAULT CHARSET=utf8;

CREATE TABLE `tb_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
  `userId` bigint(20) NOT NULL COMMENT '用户ID（Ower）',
  `toUserId` bigint(20) NOT NULL COMMENT '回复谁，用户ID',
  `appId` bigint(20) NOT NULL COMMENT '应用ID',
  `sourceTypeId` bigint(20) NOT NULL COMMENT '表示知识, 人脉,组织，等资源类型',
  `sourceId` bigint(20) NOT NULL COMMENT '知识ID, 人脉ID,组织ID，等资源ID',
  `content` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '评论内容',
  `createAt` bigint(20) DEFAULT '0' COMMENT '创建时间', 
  `isVisable` tinyint(1) DEFAULT '1' COMMENT '是否显示',  
  PRIMARY KEY (`id`),
  index `appId_sourceTypeId_sourceId` (`appId`,`sourceTypeId`,`sourceId`)
) ENGINE=InnoDB AUTO_INCREMENT=1445 DEFAULT CHARSET=utf8 COMMENT='对象评论表'
