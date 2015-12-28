CREATE TABLE `tb_tag` (
  `id` bigint(20) NOT NULL COMMENT '标签ID',
  `userId` bigint(20) NOT NULL COMMENT '用户ID',
  `appId` bigint(20) NOT NULL COMMENT '应用ID',
  `tagType` int(3) DEFAULT '0' COMMENT 'tag的分类（比如是：知识、组织、人、图片）默认0',
  `tagName` varchar(255) COLLATE utf8_unicode_ci NOT NULL COMMENT '标签名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `appId_userId_tagType_tagName` (`appId`,`userId`,`tagType`,`tagName`)
) ENGINE=InnoDB AUTO_INCREMENT=20504 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='用户标签表';

CREATE TABLE `tb_tag_source` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `tagId` bigint(20) NOT NULL COMMENT '标签ID',
  `appId` bigint(20) NOT NULL COMMENT 'Source 的应用ID',
  `userId` bigint(20) NOT NULL COMMENT '创建TagSource的人',
  `sourceId` bigint(20) NOT NULL COMMENT '资源ID',
  `sourceType` int(3) DEFAULT 0  COMMENT '资源类型 知识、人脉',
  `createAt` bigint(20) DEFAULT '0' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tagId_appId_sourceId_sourceType` (`tagId`,`appId`,`sourceId`,`sourceType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;