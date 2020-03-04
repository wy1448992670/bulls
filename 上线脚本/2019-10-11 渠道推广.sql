CREATE TABLE advertisement_channel  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `channel_no` varchar(63) NOT NULL,
  `channel_type` int(255) NOT NULL COMMENT '1cps 2cpc',
  `channel_name` varchar(255) NOT NULL,
  `status` int(255) NOT NULL COMMENT '0关闭 1打开',
  `tongji_type` int(255) NULL COMMENT '1.百度统计',
  `tongji_key` varchar(255) NULL COMMENT '统计Key',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `top_image_id` int(0) NULL COMMENT '顶部图片的upload.id,',
  `guize_text` varchar(510) NULL COMMENT '规则文本',
  `button_text` varchar(255) NULL COMMENT '按钮文本',
  `version` INT(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE INDEX(`channel_no`) USING BTREE
);


-- 渠道推广权限
INSERT INTO resources VALUES(NULL,'推广渠道列表','advertisementChannel/list','advertisement:list', 94,1,NULL,NULL);
INSERT INTO resources VALUES(NULL,'推广渠道添加','advertisementChannel/add','advertisement:add', 307,0,NULL,NULL);
INSERT INTO resources VALUES(NULL,'推广渠道编辑','advertisementChannel/edit','advertisement:edit', 307,0,NULL,NULL);
INSERT INTO resources VALUES(NULL,'推广渠道详情','advertisementChannel/detail','advertisement:detail', 307,0,NULL,NULL);



ALTER TABLE `bulls`.`advertisement_channel` 
ADD COLUMN `success_text` varchar(255) NULL COMMENT '注册成功文本' AFTER `button_text`;


INSERT INTO resources
select NULL, '推广渠道状态更新', 'advertisementChannel/update', 'advertisement:update', id , 0, NULL, NULL
from resources
where permission='advertisement:list';

INSERT INTO resources 
select NULL, '推广渠道汇总', 'advertisementChannel/sum', 'advertisement:sum', id , 1, NULL, NULL
from resources
where permission='activity:all';

ALTER TABLE `advertisement_channel` 
MODIFY COLUMN `success_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注册成功文本,如果空字符串,注册成功,不提示' AFTER `button_text`,
ADD COLUMN `redirect_url` varchar(255) NULL COMMENT '跳转url,如果空字符串,跳转wap页' AFTER `success_text`;









