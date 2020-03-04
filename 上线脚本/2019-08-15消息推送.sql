INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '新增/编辑推送消息', 'user/editMessagePush', 'user:editMessagePush', 94, 0, NULL, NULL);

ALTER TABLE `bulls`.`push_task` 
MODIFY COLUMN `push_id` int(11) NULL DEFAULT NULL COMMENT 'message_push的id' AFTER `user_id`,
ADD COLUMN `task_id` varchar(255) NULL COMMENT 'message_push的task_id' AFTER `params`;


ALTER TABLE `bulls`.`message_push` 
ADD COLUMN `type` int(10) NULL DEFAULT NULL COMMENT '枚举（1:H5 2:原生）' AFTER `create_date`,
ADD COLUMN `params` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原生推送参数' AFTER `type`;


ALTER TABLE `bulls`.`push_task` 
DROP COLUMN `type`;


ALTER TABLE `bulls`.`push_task` 
MODIFY COLUMN `send_time` datetime(0) NULL DEFAULT NULL COMMENT '推送时间(友盟推送时间)' AFTER `content`;
ALTER TABLE `bulls`.`message_push` 
MODIFY COLUMN `send_time` datetime(0) NULL DEFAULT NULL COMMENT '发送时间( 平台定时任务发送时间)' AFTER `content`;