CREATE DATABASE IF NOT EXISTS `parasol_sensitive`  DEFAULT CHARACTER SET utf8 ;

USE `parasol_sensitive`;

drop table if exists tb_sensitive_word;

/*==============================================================*/
/* Table: tb_sensitive_word                                     */
/*==============================================================*/
create table tb_sensitive_word
(
   id                   bigint not null,
   word                 varchar(50),
   level                int,
   type                 int,
   createrId            bigint,
   appId                bigint,
   primary key (id)
)  ENGINE=INNODB DEFAULT CHARSET=utf8;

alter table tb_sensitive_word comment 'sensitive word database';