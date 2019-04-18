/*
Navicat MySQL Data Transfer

Source Server         : data-talent
Source Server Version : 50719
Source Host           : 10.122.139.25:3306
Source Database       : house_hub_fence

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-12-03 16:48:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_fence_sys_log_copy
-- ----------------------------
DROP TABLE IF EXISTS `tb_XX_sys_log.sql`;
CREATE TABLE `tb_XX_sys_log.sql` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `user_mobile` varchar(24) COLLATE utf8mb4_bin DEFAULT NULL,
  `username` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `level` varchar(24) COLLATE utf8mb4_bin DEFAULT 'INFO',
  `operation_code` varchar(64) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '请求接口名称',
  `module` varchar(255) COLLATE utf8mb4_bin DEFAULT 'web-apply' COMMENT '请求接口代码',
  `result_code` varchar(32) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '交互结果标识',
  `result_message` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '交互结果信息',
  `request_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `request_body` mediumtext COLLATE utf8mb4_bin COMMENT '请求参数',
  `request_header` mediumtext COLLATE utf8mb4_bin COMMENT '请求头',
  `response_body` mediumtext COLLATE utf8mb4_bin COMMENT '返回结果信息',
  `add_ip` varchar(25) COLLATE utf8mb4_bin DEFAULT NULL,
  `version` int(10) DEFAULT '1',
  `use_millisecond` int(10) DEFAULT '0' COMMENT '访问时长',
  `deleted` tinyint(2) DEFAULT '0',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `SYS_LOG_USERID_INDEX` (`user_id`) USING BTREE,
  KEY `SYS_LOG_LEVEL_INDEX` (`level`) USING BTREE,
  KEY `SYS_LOG_OP_CODE_INDEX` (`operation_code`) USING BTREE,
  KEY `SYS_LOG_CREATE_TIME_INDEX` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=288397 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='系统操作日志';
