ALTER TABLE `bulls`.`video_album`
ADD COLUMN `show_time` datetime(0) NULL COMMENT '显示时间' AFTER `operater_id`;

ALTER TABLE `bulls`.`project_life_picture`
MODIFY COLUMN `status` int(2) NULL DEFAULT NULL COMMENT '1可用 0不可用' AFTER `upload_id`;

INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '牛只视频', 'project/videoAlbumList', 'project:video:list', 4, 1, NULL, NULL);
update resources set parent_id=15,ismenu=0 where permission ='project:addVideoPage';

INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '视频删除', 'project/videoDelete', 'project:videoDelete', 15, 0, NULL, NULL);

ALTER TABLE `bulls`.`video_album`
MODIFY COLUMN `status` int(4) NULL DEFAULT NULL COMMENT '1成功 0失败 2删除' AFTER `is_recommend`;

INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '视频推荐修改', 'project/videoRecommend', 'project:videoRecommend', 15, 0, NULL, NULL);
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '视频编辑', 'project/editVideo', 'project:editVideo', 15, 0, NULL, NULL);
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '视频提交编辑', 'project/submitEditVideo', 'project:submitEditVideo', 15, 0, NULL, NULL);

ALTER TABLE `bulls`.`project_life_picture`
MODIFY COLUMN `status` int(2) NULL DEFAULT NULL COMMENT '1可用 0不可用 2已删除' AFTER `upload_id`;

INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '牛只生活照删除', 'project/lifePictureDelete', 'project:lifePic:delete', 15, 0, NULL, NULL);

-- 是否显示奔富乐园入口 字典
INSERT INTO tm_dict(t_name, t_key, t_value, memo, t_sort) VALUES('是否显示奔富乐园入口', 'PARADISE_SHOW', 'no', 'yes：是，no：否', 4);
