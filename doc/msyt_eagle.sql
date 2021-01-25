/*
Navicat MySQL Data Transfer

Source Server         : MariaDB
Source Server Version : 50505
Source Host           : 172.16.50.242:15026
Source Database       : msyt_eagle

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2021-01-25 11:22:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_base_vendor
-- ----------------------------
DROP TABLE IF EXISTS `tb_base_vendor`;
CREATE TABLE `tb_base_vendor` (
  `idx` bigint(19) NOT NULL COMMENT '供应商Id',
  `vendor_name` varchar(20) NOT NULL DEFAULT '' COMMENT '供应商名称',
  `country` varchar(20) NOT NULL DEFAULT '' COMMENT '国家',
  `city` varchar(20) NOT NULL DEFAULT '' COMMENT '城市',
  `address` varchar(50) NOT NULL DEFAULT '' COMMENT '地址',
  `version` bigint(19) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：删除（软删除）， 1：可用 , 2: 未激活',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `update_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人员',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商';

-- ----------------------------
-- Table structure for tb_base_vendor_contact
-- ----------------------------
DROP TABLE IF EXISTS `tb_base_vendor_contact`;
CREATE TABLE `tb_base_vendor_contact` (
  `idx` bigint(19) NOT NULL COMMENT '联系人Id',
  `vendor_idx` bigint(19) NOT NULL COMMENT '供应商Id',
  `contact_name` varchar(20) NOT NULL DEFAULT '' COMMENT '联系人名称',
  `mobile_phone` varchar(20) NOT NULL DEFAULT '' COMMENT '手机',
  `email` varchar(30) NOT NULL DEFAULT '' COMMENT '邮箱',
  `version` bigint(19) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：删除（软删除）， 1：可用 , 2: 未激活',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `update_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人员',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`idx`),
  KEY `vendor_id_index` (`vendor_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商联系人';

-- ----------------------------
-- Table structure for tb_base_vendor_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_base_vendor_item`;
CREATE TABLE `tb_base_vendor_item` (
  `idx` bigint(19) NOT NULL COMMENT 'Id',
  `vendor_idx` bigint(19) NOT NULL COMMENT '供应商Id',
  `item_idx` bigint(19) NOT NULL COMMENT '商品Id',
  `item_code` varchar(20) NOT NULL COMMENT '商品货号',
  `item_name` varchar(100) NOT NULL COMMENT '商品名称',
  `item_brand` varchar(20) NOT NULL DEFAULT '' COMMENT '品牌',
  `item_desc` varchar(200) NOT NULL DEFAULT '' COMMENT '描述',
  `item_spec` varchar(15) NOT NULL DEFAULT '' COMMENT '规格',
  `item_origin` varchar(15) NOT NULL DEFAULT '' COMMENT '原产地',
  `item_barcode` varchar(30) NOT NULL DEFAULT '' COMMENT '条码',
  `number_of_pieces` int(11) NOT NULL DEFAULT '0' COMMENT '箱规',
  `item_category` varchar(20) NOT NULL DEFAULT '' COMMENT '类目',
  `supply_price` int(10) NOT NULL DEFAULT '0' COMMENT '供货价',
  `currency` varchar(10) NOT NULL DEFAULT '' COMMENT '币种',
  `available_stock` int(11) NOT NULL DEFAULT '0' COMMENT '可供库存',
  `delivery_period` int(11) NOT NULL DEFAULT '0' COMMENT '运期(天)',
  `ship_method` varchar(10) NOT NULL DEFAULT '' COMMENT '货运方式',
  `expiration_date` date NOT NULL DEFAULT '1970-01-01' COMMENT '失效日期',
  `shelf_period` int(11) NOT NULL DEFAULT '0' COMMENT '保质期(天)',
  `version` bigint(19) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：删除（软删除）， 1：可用 , 2: 未激活',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `update_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人员',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`idx`),
  UNIQUE KEY `tb_vendor_item_unique_index` (`vendor_idx`,`item_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商商品表';

-- ----------------------------
-- Table structure for tb_pur_po
-- ----------------------------
DROP TABLE IF EXISTS `tb_pur_po`;
CREATE TABLE `tb_pur_po` (
  `idx` bigint(19) NOT NULL COMMENT '订单主键idx，分布式架构，全局唯一递增##@Cache(module = "eg-pur")@MQ(module = "eg-pur", moduleAlias = "pur", autoAck = false)@Pod(module = "eg-pur", isAutoIdxZKPath = false, isAutoIdxCodeZKPath = true, isAutoVersionZKPath = false)##',
  `order_no` varchar(20) NOT NULL COMMENT '订单编号## @NotNull(message = "订单编号不能为空")@Length(max = 35, message = "订单编号最大35个字符")##',
  `num_of_sub_order` int(11) NOT NULL DEFAULT '0' COMMENT '子单数',
  `num_of_pur_item` int(11) NOT NULL DEFAULT '0' COMMENT '采购总数',
  `total_amt` bigint(19) NOT NULL DEFAULT '0' COMMENT '总金额(RMB)',
  `biz_status` smallint(6) NOT NULL DEFAULT '10' COMMENT '10:待推送 20:已推送 30:待确认 40:已确认 50:待审核 90:已完成 ',
  `version` bigint(19) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：删除（软删除）， 1：可用 , 2: 未激活',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间##@EnYyyyMMddHHmmss##',
  `create_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间##@EnYyyyMMddHHmmss##',
  `update_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人员',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购单';

-- ----------------------------
-- Table structure for tb_pur_sub_po
-- ----------------------------
DROP TABLE IF EXISTS `tb_pur_sub_po`;
CREATE TABLE `tb_pur_sub_po` (
  `idx` bigint(19) NOT NULL COMMENT 'Id',
  `po_idx` bigint(19) NOT NULL COMMENT '采购单Id',
  `item_idx` bigint(19) NOT NULL COMMENT '商品Id',
  `item_code` varchar(20) NOT NULL COMMENT '商品货号',
  `item_name` varchar(100) NOT NULL COMMENT '商品名称',
  `item_desc` varchar(200) NOT NULL DEFAULT '' COMMENT '描述',
  `item_brand` varchar(20) NOT NULL DEFAULT '' COMMENT '品牌',
  `item_barcode` varchar(30) NOT NULL DEFAULT '' COMMENT '条码',
  `item_spec` varchar(15) NOT NULL DEFAULT '' COMMENT '规格',
  `item_origin` varchar(15) NOT NULL DEFAULT '' COMMENT '原产地',
  `number_of_pieces` int(11) NOT NULL DEFAULT '0' COMMENT '箱规',
  `item_category` varchar(20) NOT NULL DEFAULT '' COMMENT '类目',
  `qty_of_require` int(11) NOT NULL DEFAULT '0' COMMENT '需求采购数',
  `qty_of_need` int(11) NOT NULL DEFAULT '0' COMMENT '距采购需求数量',
  `qty_of_purchase` int(11) NOT NULL DEFAULT '0' COMMENT '采购数量',
  `num_of_vendor` int(11) NOT NULL DEFAULT '0' COMMENT '供应商数量',
  `avg_pur_price` int(10) DEFAULT '0' COMMENT '平均采购价(分)##@$2##',
  `delivery_period` int(11) NOT NULL DEFAULT '0' COMMENT '总运期',
  `sub_amt` bigint(19) NOT NULL DEFAULT '0' COMMENT '子单金额总和(分)##@$2##',
  `biz_status` smallint(6) NOT NULL DEFAULT '10' COMMENT '10 待确认 20:供应商有修改 30:商品部已修改 40:供应商已确认',
  `version` bigint(19) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：删除（软删除）， 1：可用 , 2: 未激活',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间##@EnYyyyMMddHHmmss##',
  `create_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间##@EnYyyyMMddHHmmss##',
  `update_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人员',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`idx`),
  UNIQUE KEY `tb_pur_sub_po_unique_index` (`po_idx`,`item_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购子单';

-- ----------------------------
-- Table structure for tb_pur_sub_po_vendor
-- ----------------------------
DROP TABLE IF EXISTS `tb_pur_sub_po_vendor`;
CREATE TABLE `tb_pur_sub_po_vendor` (
  `idx` bigint(19) NOT NULL COMMENT 'Id',
  `pidx` bigint(19) DEFAULT '0' COMMENT '父idx',
  `po_idx` bigint(19) NOT NULL COMMENT '采购单Id',
  `sub_po_idx` bigint(19) NOT NULL COMMENT '子单idx',
  `vendor_idx` bigint(19) NOT NULL COMMENT '供应商idx',
  `supply_price` int(10) NOT NULL DEFAULT '0' COMMENT '供货价',
  `currency` varchar(10) NOT NULL DEFAULT '' COMMENT '币种',
  `available_stock` int(11) NOT NULL DEFAULT '0' COMMENT '可供库存',
  `delivery_period` int(11) NOT NULL DEFAULT '0' COMMENT '运期(天)',
  `ship_method` varchar(10) NOT NULL DEFAULT '' COMMENT '货运方式',
  `exchange_rate` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '汇率',
  `pur_price` int(10) NOT NULL DEFAULT '0' COMMENT '采购价',
  `qty_of_pur` int(11) NOT NULL DEFAULT '0' COMMENT '采购数量',
  `amt` bigint(19) NOT NULL DEFAULT '0' COMMENT '金额(RMB)',
  `contact_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '联系人Id',
  `contact_name` varchar(20) NOT NULL DEFAULT '' COMMENT '联系人名称',
  `contact_phone` varchar(20) NOT NULL DEFAULT '' COMMENT '联系人手机',
  `contact_email` varchar(30) NOT NULL DEFAULT '' COMMENT '联系人邮箱',
  `biz_status` smallint(6) NOT NULL DEFAULT '10' COMMENT '10 待确认 20:供应商有修改 30:商品部已修改 40:供应商已确认',
  `version` bigint(19) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：删除（软删除）， 1：可用 , 2: 未激活',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `update_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人员',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`idx`),
  KEY `tb_pspv_po_idx_index` (`po_idx`),
  KEY `tb_pspv_sub_po_idx_index` (`sub_po_idx`),
  KEY `tb_pspv_vendor_idx_index` (`vendor_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购子单供应商';

-- ----------------------------
-- Table structure for tb_vendor_po
-- ----------------------------
DROP TABLE IF EXISTS `tb_vendor_po`;
CREATE TABLE `tb_vendor_po` (
  `idx` bigint(19) NOT NULL COMMENT 'Id',
  `pur_po_idx` bigint(19) NOT NULL COMMENT '原采购单Idx',
  `pur_po_no` varchar(20) NOT NULL COMMENT '原采购单编号',
  `order_no` varchar(20) NOT NULL COMMENT '订单编号',
  `vendor_idx` bigint(19) NOT NULL COMMENT '供应商idx',
  `num_of_pur_item` int(11) NOT NULL DEFAULT '0' COMMENT '采购总数',
  `total_amt` bigint(19) NOT NULL DEFAULT '0' COMMENT '总金额(RMB)',
  `biz_status` smallint(6) NOT NULL DEFAULT '10' COMMENT ' 10 待确认  40:已确认',
  `version` bigint(19) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：删除（软删除）， 1：可用 , 2: 未激活',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `update_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人员',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`idx`),
  UNIQUE KEY `tb_vendor_po_unique_index` (`pur_po_idx`,`vendor_idx`),
  KEY `tb_vendor_po_vendor_idx_index` (`vendor_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商采购单';

-- ----------------------------
-- Table structure for tb_vendor_po_line
-- ----------------------------
DROP TABLE IF EXISTS `tb_vendor_po_line`;
CREATE TABLE `tb_vendor_po_line` (
  `idx` bigint(19) NOT NULL COMMENT 'Id',
  `po_idx` bigint(19) NOT NULL COMMENT '供应商采购单Id',
  `item_idx` bigint(19) NOT NULL COMMENT '商品Id',
  `item_code` varchar(20) NOT NULL COMMENT '商品货号',
  `item_name` varchar(100) NOT NULL COMMENT '商品名称',
  `item_desc` varchar(200) NOT NULL DEFAULT '' COMMENT '描述',
  `item_brand` varchar(20) NOT NULL DEFAULT '' COMMENT '品牌',
  `item_barcode` varchar(30) NOT NULL DEFAULT '' COMMENT '条码',
  `item_spec` varchar(15) NOT NULL DEFAULT '' COMMENT '规格',
  `item_origin` varchar(15) NOT NULL DEFAULT '' COMMENT '原产地',
  `number_of_pieces` int(11) NOT NULL DEFAULT '0' COMMENT '箱规',
  `item_category` varchar(20) NOT NULL DEFAULT '' COMMENT '类目',
  `qty_of_purchase` int(11) NOT NULL DEFAULT '0' COMMENT '采购数量',
  `pur_price` int(10) NOT NULL DEFAULT '0' COMMENT '采购价',
  `currency` varchar(10) NOT NULL DEFAULT '' COMMENT '币种',
  `available_stock` int(11) NOT NULL DEFAULT '0' COMMENT '可供库存',
  `delivery_period` int(11) NOT NULL DEFAULT '0' COMMENT '运期(天)',
  `ship_method` varchar(10) NOT NULL DEFAULT '' COMMENT '货运方式',
  `exchange_rate` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '汇率',
  `supply_price` int(10) NOT NULL DEFAULT '0' COMMENT '供货价',
  `amt` bigint(19) NOT NULL DEFAULT '0' COMMENT '金额',
  `biz_status` smallint(6) NOT NULL DEFAULT '0',
  `version` bigint(19) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '0：删除（软删除）， 1：可用 , 2: 未激活',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `update_by_member_idx` bigint(19) NOT NULL DEFAULT '0' COMMENT '更新人员',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`idx`),
  UNIQUE KEY `tb_vendor_po_line_unique_index` (`po_idx`,`item_idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商采购单明细';
