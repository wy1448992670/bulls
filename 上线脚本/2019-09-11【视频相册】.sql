#视频相册

CREATE TABLE `video_album`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `video_page_url` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频封面地址',
  `video_url` varchar(800) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视频地址',
  `is_recommend` int(11) NULL DEFAULT 0 COMMENT '1 是 0 否  是否推荐',
  `status` int(4) NULL DEFAULT NULL COMMENT '1成功 0失败',
  `operater_id` int(11) NULL DEFAULT NULL COMMENT '操作人id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB   CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '视频相册' ROW_FORMAT = Dynamic;

CREATE TABLE `project_life_picture`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ear_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '耳标号',
  `upload_id` int(11) NULL DEFAULT NULL,
  `status` int(2) NULL DEFAULT NULL COMMENT '0可用1不可用',
  `create_user` int(11) NULL DEFAULT NULL COMMENT '上传用户',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ear_number`(`ear_number`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '牛只生活照' ;


INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '上传牛只生活照', 'project/addpic', 'project:lifePic:upload', 15, 0, NULL, NULL);
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '牛只生活照列表', 'project/lifePicList', 'project:lifePic:list', 15, 0, NULL, '');


#视频上传页面跳转菜单
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '视频上传', 'project/addVideoPage', 'project:addVideoPage', 4, 1, NULL, '');
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (297, '视频提交', 'project/addVideo', 'project:addVideo', 15, 0, NULL, NULL);

ALTER TABLE `bulls`.`project_life_picture` 
ADD COLUMN `is_read` int(4) NOT NULL DEFAULT 0 COMMENT '0未读 1已读' AFTER `create_user`;

