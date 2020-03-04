ALTER TABLE `bulls`.`activity_detail` 
MODIFY COLUMN `trigger_type` int(10) UNSIGNED NOT NULL COMMENT '触发条件:1.注册 2.投资 3.消费 4.登录 5.邀请首投 6.回购' AFTER `status`;

INSERT INTO `bulls`.`activity_detail`(`id`, `activity_id`, `name`, `start_time`, `end_time`, `status`, `trigger_type`, `condition_value_type`, `condition_target`, `condition_date_type`, `threshold_value`, `cycle_vertices`) VALUES (8, 113, '卖牛红包', NULL, NULL, 1, 6, 1, 1, 1, 1.00, 0.00);
INSERT INTO `bulls`.`activity_detail_send_hongbao`(`id`, `activity_detail_id`, `target_type`, `cnt`, `type`, `amount`, `random_amount`, `limit_amount`, `limit_day`, `start_time`, `end_time`, `effective_days`) VALUES (24, 8, 1, 1, 2, 58.00, 0.00, 0, 90, NULL, NULL, 365);
