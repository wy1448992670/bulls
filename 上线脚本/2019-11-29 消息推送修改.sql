ALTER TABLE `bulls`.`push_task` 
ADD COLUMN `device_token` varchar(255) NULL COMMENT 'device_token' AFTER `task_id`;