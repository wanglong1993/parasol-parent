-- auto Generated on 2017-07-10 14:19:08 
-- DROP TABLE IF EXISTS `user_file_category`; 
CREATE TABLE user_file_category(
    `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `user_id` BIGINT NOT NULL DEFAULT -1 COMMENT '用户ID',
    `server_filename` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '目录名称',
    `sort_id` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '目录排序标识',
    `ctime` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '创建时间',
    `parent_id` BIGINT NOT NULL DEFAULT -1 COMMENT '父目录ID',
    `fiel_id` BIGINT NOT NULL DEFAULT -1 COMMENT '文件ID',
    `is_dir` INTEGER(12) NOT NULL DEFAULT -1 COMMENT '是否目录（0：不是，1：是）',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT 'user_file_category';
