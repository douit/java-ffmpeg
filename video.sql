/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50725
Source Host           : 127.0.0.1:3306
Source Database       : video

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-12-18 09:37:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account`
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(50) NOT NULL COMMENT '用户密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', 'admin', 'fc3f5e19de306c05eac59f8d1d1ae702');

-- ----------------------------
-- Table structure for `video`
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video` (
  `v_id` char(36) NOT NULL COMMENT '视频id',
  `v_name` varchar(100) NOT NULL COMMENT '视频名称',
  `upload_time` datetime NOT NULL COMMENT '上传时间',
  `v_status` varchar(20) NOT NULL DEFAULT '0' COMMENT '状态: UPLOAD_SUCCESS-上传成功 TRANSCODING-转码中 TRANSCODING_SUCCESS-转码成功 TRANSCODING_FAILED-转码失败 UPLOAD_OSS_FAILED-上传oss失败 COMPLETE-完成 UPLOAD_OSS-上传oss中 ',
  `v_suffix` varchar(10) NOT NULL COMMENT '视频格式后缀名',
  PRIMARY KEY (`v_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of video
-- ----------------------------
INSERT INTO `video` VALUES ('d412245a-dccd-4b4d-ae87-b6d4b1ac3bc6', '(여자)아이들((G)I-DLE) - \'Uh-Oh\' Official Music Video', '2019-12-10 19:54:45', 'TRANSCODING', '.mp4');
