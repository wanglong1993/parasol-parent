CREATE DATABASE `parasol_mapping`  /*!40100 DEFAULT CHARACTER SET utf8 */;
use parasol_mapping;
CREATE TABLE `tb_mapping` (
  `id` bigint(20) NOT NULL COMMENT '标签ID',
  `openId` bigint(20) NULL COMMENT '开放平台Id',
  `uId` bigint(20) NULL COMMENT '万能插座ID',
  `idType` int(11) DEFAULT '0' COMMENT '0: 用户ID',
  PRIMARY KEY (`id`),
  UNIQUE KEY `openId_uId_idType` (`openId`,`uId`,`idType`)
) ENGINE=InnoDB AUTO_INCREMENT=20504 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='新旧系统ID映射';

