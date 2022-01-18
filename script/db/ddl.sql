/*
 Navicat Premium Data Transfer

 Source Server         : 47.241.161.188
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : 47.241.161.188:3306
 Source Schema         : city_mall

 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001

 Date: 18/01/2022 15:04:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_account_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_account_detail`;
CREATE TABLE `t_account_detail` (
                                    `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                    `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                                    `order_no` varchar(64) DEFAULT NULL COMMENT '订单编号',
                                    `event` int(11) NOT NULL COMMENT '事件：1->支付、2->退款',
                                    `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '金额',
                                    `remark` varchar(50) DEFAULT NULL,
                                    `extend_info` varchar(256) DEFAULT NULL,
                                    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                    `del_flag` int(1) NOT NULL DEFAULT '0',
                                    PRIMARY KEY (`id`),
                                    KEY `idx_user_id` (`user_id`) USING BTREE,
                                    KEY `idx_order_no` (`order_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账务明细表';

-- ----------------------------
-- Table structure for t_admin_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_admin_user_info`;
CREATE TABLE `t_admin_user_info` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                     `phone` varchar(64) NOT NULL COMMENT '登录手机号码',
                                     `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
                                     `password` varchar(64) NOT NULL COMMENT '密码',
                                     `status` int(1) NOT NULL DEFAULT '1' COMMENT '帐号启用状态:0-禁用；1-启用',
                                     `user_type` int(1) NOT NULL DEFAULT '1' COMMENT '管理员类型:0-超级管理员；1-普通管理员',
                                     `icon` varchar(512) DEFAULT NULL COMMENT '头像',
                                     `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                     `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                                     PRIMARY KEY (`id`),
                                     KEY `idx_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='管理员用户表';

-- ----------------------------
-- Table structure for t_area_info
-- ----------------------------
DROP TABLE IF EXISTS `t_area_info`;
CREATE TABLE `t_area_info` (
                               `id` int(11) NOT NULL,
                               `type` int(1) DEFAULT '0' COMMENT '类型 0省 1直辖市',
                               `code` varchar(100) NOT NULL COMMENT '编码',
                               `name` varchar(100) NOT NULL COMMENT '名称',
                               `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '父级ID',
                               `lng` double(19,16) DEFAULT '0.0000000000000000' COMMENT '经度',
                               `lat` double(19,16) DEFAULT '0.0000000000000000' COMMENT '纬度',
                               `level` int(1) NOT NULL DEFAULT '0' COMMENT '层级 0省、直辖市、港澳台 1市 2县/区 3镇 4村',
                               `sort_no` int(11) NOT NULL DEFAULT '0' COMMENT '排序序号',
                               `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='省市区字典表';

-- ----------------------------
-- Table structure for t_cart_info
-- ----------------------------
DROP TABLE IF EXISTS `t_cart_info`;
CREATE TABLE `t_cart_info` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
                               `merchant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商家ID',
                               `product_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
                               `bar_code` varchar(128) NOT NULL DEFAULT '' COMMENT '商品编号',
                               `product_name` varchar(127) NOT NULL COMMENT '商品名称',
                               `product_sku_id` bigint(20) DEFAULT NULL COMMENT '商品sku ID',
                               `price` decimal(10,2) DEFAULT '0.00' COMMENT '商品价格',
                               `number` int(4) DEFAULT '0' COMMENT '商品数量',
                               `spec` text COMMENT '商品规格值列表，采用JSON数组格式',
                               `checked` int(4) DEFAULT '0' COMMENT '购物车中商品是否选择状态',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                               `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                               PRIMARY KEY (`id`) USING BTREE,
                               KEY `user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- ----------------------------
-- Table structure for t_delivery_fee_config
-- ----------------------------
DROP TABLE IF EXISTS `t_delivery_fee_config`;
CREATE TABLE `t_delivery_fee_config` (
                                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                         `merchant_id` bigint(20) NOT NULL COMMENT '商家ID',
                                         `delivery_address` varchar(256) DEFAULT NULL COMMENT '配送地址',
                                         `address_lon` varchar(64) DEFAULT NULL COMMENT '配送地址经度',
                                         `address_lat` varchar(64) DEFAULT NULL COMMENT '配送地址纬度',
                                         `delivery_instruction` varchar(256) DEFAULT NULL COMMENT '配送说明',
                                         `min_order_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '免运费订单最小金额',
                                         `delivery_money` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '运费',
                                         `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                         `del_flag` int(1) NOT NULL DEFAULT '0',
                                         PRIMARY KEY (`id`),
                                         KEY `idx_merchant_id` (`merchant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流费配置表';

-- ----------------------------
-- Table structure for t_merchant_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant_user_info`;
CREATE TABLE `t_merchant_user_info` (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `phone` varchar(64) NOT NULL COMMENT '登录手机号码',
                                        `merchant_name` varchar(64) DEFAULT NULL COMMENT '商家名称',
                                        `password` varchar(64) NOT NULL COMMENT '密码',
                                        `merchant_address` varchar(256) NOT NULL COMMENT '商家地址',
                                        `logo` varchar(512) DEFAULT NULL COMMENT '商家logo',
                                        `introduction` text COMMENT '商家简介',
                                        `contact_name` varchar(64) DEFAULT NULL COMMENT '联系人名字',
                                        `contact_mobile` varchar(16) DEFAULT NULL COMMENT '联系人电话',
                                        `merchant_score` decimal(12,2) DEFAULT '4.00' COMMENT '商家评分',
                                        `merchant_weight` decimal(12,2) DEFAULT '0.00' COMMENT '商家权重',
                                        `status` int(1) NOT NULL DEFAULT '0' COMMENT '帐号启用状态:0-待审核；1-审核通过；2-审核拒绝；3-禁用',
                                        `province_name` varchar(64) DEFAULT NULL COMMENT '商家地址(省)',
                                        `province_id` int(16) DEFAULT NULL COMMENT '商家地址(省id)',
                                        `city_name` varchar(64) DEFAULT NULL COMMENT '商家地址(市)',
                                        `city_id` int(16) DEFAULT NULL COMMENT '商家地址(市id)',
                                        `district` varchar(45) DEFAULT NULL COMMENT '商家地址(区)',
                                        `district_id` int(16) DEFAULT NULL COMMENT '商家地址(区id)',
                                        `remark` text COMMENT '备注',
                                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                        `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                                        PRIMARY KEY (`id`),
                                        KEY `idx_user_name` (`merchant_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='商家用户表';

-- ----------------------------
-- Table structure for t_order_detail_info
-- ----------------------------
DROP TABLE IF EXISTS `t_order_detail_info`;
CREATE TABLE `t_order_detail_info` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
                                       `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                                       `order_no` varchar(64) DEFAULT NULL COMMENT '订单编号',
                                       `product_id` bigint(20) NOT NULL COMMENT '商品ID',
                                       `product_sku_id` bigint(20) NOT NULL COMMENT '商品sku ID',
                                       `merchant_id` bigint(20) NOT NULL COMMENT '商家ID',
                                       `product_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
                                       `buy_amount` int(4) NOT NULL DEFAULT '0' COMMENT '购买数量',
                                       `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                       `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                                       PRIMARY KEY (`id`),
                                       KEY `idx_user_id` (`user_id`) USING BTREE,
                                       KEY `idx_order_no` (`order_no`) USING BTREE,
                                       KEY `idx_product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COMMENT='订单详情表';

-- ----------------------------
-- Table structure for t_order_info
-- ----------------------------
DROP TABLE IF EXISTS `t_order_info`;
CREATE TABLE `t_order_info` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
                                `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                                `merchant_id` bigint(20) NOT NULL COMMENT '商家ID',
                                `order_no` varchar(64) DEFAULT NULL COMMENT '订单编号',
                                `total_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '订单总金额',
                                `real_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '实际支付金额',
                                `delivery_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '运费金额',
                                `source_type` int(1) NOT NULL DEFAULT '0' COMMENT '订单来源：0->PC订单；1->app订单',
                                `status` int(1) NOT NULL DEFAULT '0' COMMENT '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->已取消',
                                `order_type` int(1) NOT NULL DEFAULT '0' COMMENT '订单类型：0->正常订单；1->预约订单',
                                `delivery_type` int(1) DEFAULT NULL COMMENT '配送方式，0->快递；1->同城配送',
                                `delivery_company` varchar(64) DEFAULT NULL COMMENT '物流公司',
                                `delivery_no` varchar(64) DEFAULT NULL COMMENT '物流单号',
                                `receive_address_id` bigint(20) NOT NULL COMMENT '收货信息ID',
                                `reserve_time` datetime DEFAULT NULL COMMENT '预约时间',
                                `note` varchar(500) DEFAULT NULL COMMENT '订单备注',
                                `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
                                `delivery_time` datetime DEFAULT NULL COMMENT '发货时间',
                                `receive_time` datetime DEFAULT NULL COMMENT '确认收货时间',
                                `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                                PRIMARY KEY (`id`),
                                KEY `idx_user_id` (`user_id`) USING BTREE,
                                KEY `idx_order_no` (`order_no`) USING BTREE,
                                KEY `idx_status_create_time` (`status`,`create_time`) USING BTREE,
                                KEY `idx_status_delivery_time` (`status`,`delivery_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Table structure for t_order_process_info
-- ----------------------------
DROP TABLE IF EXISTS `t_order_process_info`;
CREATE TABLE `t_order_process_info` (
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                                        `order_no` varchar(64) DEFAULT NULL COMMENT '订单编号',
                                        `label` varchar(64) DEFAULT NULL COMMENT '标签',
                                        `content` text COMMENT '进程内容',
                                        `opt_user_name` varchar(64) DEFAULT NULL COMMENT '用户名称',
                                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                        `del_flag` int(1) NOT NULL DEFAULT '0',
                                        PRIMARY KEY (`id`),
                                        KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COMMENT='订单进程表';

-- ----------------------------
-- Table structure for t_order_product_version
-- ----------------------------
DROP TABLE IF EXISTS `t_order_product_version`;
CREATE TABLE `t_order_product_version` (
                                           `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单id',
                                           `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                                           `order_no` varchar(64) DEFAULT NULL COMMENT '订单编号',
                                           `product_id` bigint(20) NOT NULL COMMENT '商品ID',
                                           `product_sku_id` bigint(20) NOT NULL COMMENT '商品sku ID',
                                           `merchant_id` bigint(20) NOT NULL COMMENT '商家ID',
                                           `merchant_name` varchar(128) DEFAULT NULL COMMENT '商家名称',
                                           `product_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
                                           `buy_amount` int(4) NOT NULL DEFAULT '0' COMMENT '购买数量',
                                           `product_bar_code` varchar(128) NOT NULL DEFAULT '' COMMENT '商品编号',
                                           `product_name` varchar(127) NOT NULL COMMENT '商品名称',
                                           `product_spec` text COMMENT '商品规格值列表，采用JSON数组格式',
                                           `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                           `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                           `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                                           PRIMARY KEY (`id`),
                                           KEY `idx_user_id` (`user_id`) USING BTREE,
                                           KEY `idx_order_no` (`order_no`) USING BTREE,
                                           KEY `idx_product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COMMENT='订单商品版本表';

-- ----------------------------
-- Table structure for t_pay_info
-- ----------------------------
DROP TABLE IF EXISTS `t_pay_info`;
CREATE TABLE `t_pay_info` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
                              `merchant_id` bigint(20) DEFAULT NULL COMMENT '商家id',
                              `order_no` varchar(64) DEFAULT NULL COMMENT '订单编号',
                              `out_trade_no` varchar(128) DEFAULT NULL COMMENT '外部支付单号',
                              `pay_type` int(1) NOT NULL DEFAULT '0' COMMENT '支付方式：0->未支付；1->微信；2->支付宝;3->paypal',
                              `pay_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '支付金额',
                              `merchant_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商家应收金额',
                              `platform_amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '平台应收金额',
                              `pay_status` int(1) NOT NULL DEFAULT '0' COMMENT '支付状态：0->未支付；1->支付成功；2->支付失败；3->支付中；',
                              `payment_time` datetime DEFAULT NULL COMMENT '支付时间',
                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                              `del_flag` int(1) NOT NULL DEFAULT '0',
                              PRIMARY KEY (`id`),
                              KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='支付记录信息表';

-- ----------------------------
-- Table structure for t_product_attribute_info
-- ----------------------------
DROP TABLE IF EXISTS `t_product_attribute_info`;
CREATE TABLE `t_product_attribute_info` (
                                            `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                            `product_id` bigint(20) NOT NULL COMMENT '商品表的商品ID',
                                            `attribute` varchar(255) NOT NULL COMMENT '商品参数名称',
                                            `value` varchar(255) NOT NULL COMMENT '商品参数值',
                                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                            `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                                            PRIMARY KEY (`id`) USING BTREE,
                                            KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COMMENT='商品参数表';

-- ----------------------------
-- Table structure for t_product_brand
-- ----------------------------
DROP TABLE IF EXISTS `t_product_brand`;
CREATE TABLE `t_product_brand` (
                                   `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `name` varchar(128) NOT NULL COMMENT '品牌名称',
                                   `description` varchar(256) DEFAULT NULL COMMENT '品牌描述',
                                   `logo` varchar(512) DEFAULT NULL COMMENT 'LOGO地址',
                                   `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
                                   `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                   `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                                   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='商品品牌';

-- ----------------------------
-- Table structure for t_product_category
-- ----------------------------
DROP TABLE IF EXISTS `t_product_category`;
CREATE TABLE `t_product_category` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                      `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '上级分类的编号：0表示一级分类',
                                      `name` varchar(64) NOT NULL COMMENT '分类名称',
                                      `level` int(1) NOT NULL DEFAULT '0' COMMENT '分类级别：0->1级；1->2级',
                                      `sort` int(11) NOT NULL DEFAULT '0',
                                      `icon` varchar(255) DEFAULT NULL COMMENT '图标',
                                      `keywords` varchar(255) DEFAULT NULL,
                                      `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                      `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                                      PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='产品分类';

-- ----------------------------
-- Table structure for t_product_detail_info
-- ----------------------------
DROP TABLE IF EXISTS `t_product_detail_info`;
CREATE TABLE `t_product_detail_info` (
                                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                         `product_id` bigint(20) NOT NULL COMMENT '商品id',
                                         `images` text COMMENT '商品宣传图片列表，采用JSON数组格式',
                                         `introduction` varchar(255) DEFAULT NULL COMMENT '商品简介',
                                         `detail` text NOT NULL COMMENT '商品详细介绍，是富文本格式',
                                         `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                         `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                         `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                                         PRIMARY KEY (`id`) USING BTREE,
                                         KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='商品详细信息表';

-- ----------------------------
-- Table structure for t_product_info
-- ----------------------------
DROP TABLE IF EXISTS `t_product_info`;
CREATE TABLE `t_product_info` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `bar_code` varchar(128) NOT NULL DEFAULT '' COMMENT '商品编号',
                                  `name` varchar(127) NOT NULL COMMENT '商品名称',
                                  `category_id` int(4) NOT NULL DEFAULT '0' COMMENT '商品所属类目ID',
                                  `brand_id` int(11) NOT NULL DEFAULT '0' COMMENT '品牌ID',
                                  `merchant_id` bigint(20) NOT NULL COMMENT '商家ID',
                                  `keywords` varchar(255) DEFAULT NULL COMMENT '商品关键字，采用逗号间隔',
                                  `status` tinyint(1) NOT NULL DEFAULT '1' COMMENT '商品状态，1-待上架，2-上架，3-下架',
                                  `sort_order` int(4) NOT NULL DEFAULT '0' COMMENT '排序值',
                                  `is_new` tinyint(1) DEFAULT '0' COMMENT '是否新品首发',
                                  `is_hot` tinyint(1) DEFAULT '0' COMMENT '是否人气推荐',
                                  `advise_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '参考价格',
                                  `cost_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '成本价格',
                                  `sale_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '售卖价格',
                                  `up_time` datetime DEFAULT NULL COMMENT '上架时间',
                                  `sale_count` int(11) NOT NULL DEFAULT '10' COMMENT '销售总量',
                                  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                  `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  KEY `bar_code` (`bar_code`) USING BTREE,
                                  KEY `cat_id` (`category_id`) USING BTREE,
                                  KEY `brand_id` (`brand_id`) USING BTREE,
                                  KEY `merchant_id` (`merchant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='商品基本信息表';

-- ----------------------------
-- Table structure for t_product_sku_info
-- ----------------------------
DROP TABLE IF EXISTS `t_product_sku_info`;
CREATE TABLE `t_product_sku_info` (
                                      `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                      `product_id` bigint(20) NOT NULL COMMENT '商品表的商品ID',
                                      `spec` text NOT NULL COMMENT '商品规格值列表，采用JSON数组格式',
                                      `price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品货品价格',
                                      `stock` int(11) NOT NULL DEFAULT '0' COMMENT '商品货品数量',
                                      `image_url` text COMMENT '商品货品图片',
                                      `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                      `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                      `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                                      PRIMARY KEY (`id`) USING BTREE,
                                      KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COMMENT='商品货品表';

-- ----------------------------
-- Table structure for t_product_spec_info
-- ----------------------------
DROP TABLE IF EXISTS `t_product_spec_info`;
CREATE TABLE `t_product_spec_info` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `product_id` bigint(20) NOT NULL COMMENT '商品表的商品ID',
                                       `spec_name` varchar(255) NOT NULL COMMENT '商品规格名称',
                                       `spec_value` varchar(255) NOT NULL COMMENT '商品规格值',
                                       `image_url` varchar(255) NOT NULL COMMENT '商品规格图片',
                                       `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                       `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                                       PRIMARY KEY (`id`) USING BTREE,
                                       KEY `product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8mb4 COMMENT='商品规格表';

-- ----------------------------
-- Table structure for t_refund_info
-- ----------------------------
DROP TABLE IF EXISTS `t_refund_info`;
CREATE TABLE `t_refund_info` (
                                 `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `merchant_id` bigint(20) DEFAULT NULL COMMENT '商家id',
                                 `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
                                 `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
                                 `product_id` bigint(20) DEFAULT NULL COMMENT '商品id',
                                 `order_no` varchar(64) DEFAULT NULL COMMENT '订单编号',
                                 `refund_type` int(4) DEFAULT '1' COMMENT '退款类型，1-退款退货,2-仅退金额',
                                 `refund_amount` decimal(10,2) DEFAULT '0.00' COMMENT '退款金额',
                                 `status` int(1) DEFAULT '0' COMMENT '申请状态：0->待处理；1->退货中；2->已完成；3->已拒绝',
                                 `refund_reason` varchar(128) DEFAULT NULL COMMENT '退款原因',
                                 `refund_description` varchar(500) DEFAULT NULL COMMENT '描述描述',
                                 `refund_delivery_company` varchar(64) DEFAULT NULL COMMENT '退款物流公司',
                                 `refund_delivery_no` varchar(64) DEFAULT NULL COMMENT '退款物流单号',
                                 `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                                 `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                 `del_flag` int(1) NOT NULL DEFAULT '0',
                                 PRIMARY KEY (`id`),
                                 KEY `idx_order_no` (`order_no`) USING BTREE,
                                 KEY `idx_user_id` (`user_id`) USING BTREE,
                                 KEY `idx_product_id` (`product_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COMMENT='订单退货申请';

-- ----------------------------
-- Table structure for t_refund_reason_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_refund_reason_tag`;
CREATE TABLE `t_refund_reason_tag` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `name` varchar(100) DEFAULT NULL COMMENT '退货类型',
                                       `sort` int(11) DEFAULT '1',
                                       `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                       `del_flag` int(1) NOT NULL DEFAULT '0',
                                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='退款原因标签表';

-- ----------------------------
-- Table structure for t_user_address
-- ----------------------------
DROP TABLE IF EXISTS `t_user_address`;
CREATE TABLE `t_user_address` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                  `receive_user_name` varchar(63) NOT NULL COMMENT '收货人名称',
                                  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID',
                                  `province_id` int(11) NOT NULL DEFAULT '0' COMMENT '省ID',
                                  `province_name` varchar(16) DEFAULT NULL COMMENT '省名称',
                                  `city_id` int(11) NOT NULL DEFAULT '0' COMMENT '市ID',
                                  `city_name` varchar(16) DEFAULT NULL COMMENT '市名称',
                                  `area_id` int(11) NOT NULL DEFAULT '0' COMMENT '区县ID',
                                  `area_name` varchar(16) DEFAULT NULL COMMENT '区县名称',
                                  `address` varchar(127) NOT NULL DEFAULT '' COMMENT '具体收货地址',
                                  `mobile` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号码',
                                  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否默认地址',
                                  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                  `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                                  PRIMARY KEY (`id`) USING BTREE,
                                  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='收货地址表';

-- ----------------------------
-- Table structure for t_user_balance_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_balance_info`;
CREATE TABLE `t_user_balance_info` (
                                       `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                       `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
                                       `user_type` int(4) DEFAULT '1' COMMENT '用户类型，1-商家,2-个人',
                                       `available_balance` decimal(12,2) DEFAULT '0.00' COMMENT '可用余额',
                                       `total_balance` decimal(12,2) DEFAULT '0.00' COMMENT '总余额',
                                       `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                       `del_flag` int(1) NOT NULL DEFAULT '0',
                                       PRIMARY KEY (`id`),
                                       KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='用户余额信息表';

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT,
                               `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
                               `phone` varchar(64) NOT NULL COMMENT '手机号码',
                               `password` varchar(64) NOT NULL COMMENT '密码',
                               `status` int(1) NOT NULL DEFAULT '1' COMMENT '帐号启用状态:0-禁用；1-启用',
                               `icon` varchar(512) DEFAULT NULL COMMENT '头像',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                               `del_flag` bit(1) DEFAULT b'0' COMMENT '逻辑删除标志',
                               PRIMARY KEY (`id`),
                               KEY `idx_user_name` (`user_name`),
                               KEY `idx_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;
