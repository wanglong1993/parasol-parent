create database parasol_metadata;
use parasol_metadata;
CREATE TABLE `tb_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `pid` bigint(20) NOT NULL COMMENT '父主键',
  `name` varchar(20) DEFAULT '' COMMENT '类型名称',
  `codeNumber` varchar(20) DEFAULT '' COMMENT '编码',
  `nameIndex` varchar(50) DEFAULT '' COMMENT '简拼音',
  `nameIndexAll` varchar(200) DEFAULT '' COMMENT '全拼音',
  `remark` varchar(500) DEFAULT '' COMMENT '提示信息',
  `isRoot` varchar(20) DEFAULT '' COMMENT '是根吗',
  `levelType` int(11) DEFAULT '0' COMMENT '级别类型（标示这个级别是行业或者啥）',
  `useType` int(1) DEFAULT '0' COMMENT '作用类型 0 系统类型，1 自定义类型',
  `orderNo` int(11) DEFAULT '0' COMMENT '展示顺序号',
  `creator` bigint(20) DEFAULT '0' COMMENT '创建者',
  `ctime`   bigint(20) DEFAULT '0' COMMENT '创建时间',
  `createBy` varchar(50) DEFAULT '' COMMENT '创建人姓名',
  `hasChild` int(1) DEFAULT '0' COMMENT '是否有下级',
  `disabled` int(1) DEFAULT '0' COMMENT '是否可用',
  PRIMARY KEY (`id`),
  index pid (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=1307 DEFAULT CHARSET=utf8