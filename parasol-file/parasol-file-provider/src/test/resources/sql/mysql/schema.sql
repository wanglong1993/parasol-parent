CREATE DATABASE IF NOT EXISTS `parasol_file`  DEFAULT CHARACTER SET utf8 ;

USE `parasol_file`;

drop table if exists tb_file_index;

/*==============================================================*/
/* Table: tb_file_index                                     */
/*==============================================================*/
create table tb_file_index
(
   id                   bigint not null,
   file_path            varchar(50),
   server_host          varchar(50),
   file_title           varchar(100),
   file_size            bigint,
   status               tinyint,
   creater_id           bigint,
   md5                  varchar(255),
   task_id              varchar(255),
   module_type          tinyint,
   crc32                varchar(24),
   file_type            tinyint,
   transcoding          tinyint,
   thumbnails_path      varchar(100),
   primary key (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
