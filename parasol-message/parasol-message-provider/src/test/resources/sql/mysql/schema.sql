CREATE DATABASE IF NOT EXISTS `parasol_message`  DEFAULT CHARACTER SET utf8 ;

USE `parasol_message`;

drop table if exists tb_message_entity;

/*==============================================================*/
/* Table: tb_message_entity                                     */
/*==============================================================*/
create table tb_message_entity 
(
   id                   bigint(20)                     not null,
   type                 tinyint                        null,
   createrId            bigint(20)                     null,
   content              varchar(255)                   null,
   sourceId             bigint(20)                     null,
   sourceType           varchar(10)                        null,
   sourceTitle          varchar(255)                   null,
   appId                bigint(20)                    null,
   PRIMARY KEY (`id`),
   UNIQUE INDEX `createrId_type_appId` (`createrId`, `type`,`appId`) 
) ENGINE=INNODB DEFAULT CHARSET=utf8;


drop table if exists tb_message_relation;

/*==============================================================*/
/* Table: MessageRelation                                       */
/*==============================================================*/
create table tb_message_relation 
(
   id                   bigint(20)                     not null,
   entityId             bigint(20)                     null,
   receiverId           bigint(20)                     null,
   status               tinyint                        null,
   isRead               tinyint                        null,
   dealTime             bigint(20)                     null,
   type                 tinyint                        null,
   appId				bigint(20)					   null,
   PRIMARY KEY (`id`),
   UNIQUE INDEX `receiverId_appId_type` (`receiverId`,`appId`, `type`) 
) ENGINE=INNODB DEFAULT CHARSET=utf8;

