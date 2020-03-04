INSERT INTO `bulls`.`tm_dict`(`id`, `t_name`, `t_key`, `t_value`, `memo`, `t_sort`, `tp`) VALUES (null, '充值是否显示IOS', 'RECHARGE_SHOW_IOS', 'yes', 'yes：是，no：否', NULL, NULL);
INSERT INTO `bulls`.`tm_dict`(`id`, `t_name`, `t_key`, `t_value`, `memo`, `t_sort`, `tp`) VALUES (null, '充值是否显示Android', 'RECHARGE_SHOW_ANDROID', 'yes', 'yes：是，no：否', NULL, NULL);
INSERT INTO `bulls`.`tm_dict`(`id`, `t_name`, `t_key`, `t_value`, `memo`, `t_sort`, `tp`) VALUES (null, '充值是否显示WAP', 'RECHARGE_SHOW_WAP', 'yes', 'yes：是，no：否', NULL, NULL);

update recharge set order_type='recharge' where order_type is null;
update recharge set other_id=id where order_type='recharge';
#--------公告---------
INSERT INTO `bulls`.`tm_dict`(`id`, `t_name`, `t_key`, `t_value`, `memo`, `t_sort`, `tp`) VALUES (null, '公告时间秒数', 'NOTICE_SHOW_TIME', '3', 'app公告滚动时间秒数', 2, NULL);

ALTER TABLE `bulls`.`app_notice` 
ADD COLUMN `title_color` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题颜色' AFTER `title`;

update app_notice set title_color = '#000000' where title_color is null