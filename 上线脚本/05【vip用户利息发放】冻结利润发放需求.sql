
ALTER TABLE `t_goods_order_invest_relation` ADD COLUMN `table_type`  int(11) NOT NULL DEFAULT 1 COMMENT '关联表 1：goods_order  2:利息发放纪录表' AFTER `id`;
ALTER TABLE `user` ADD COLUMN `give_out_date`  int(11) NULL DEFAULT NULL COMMENT '每月利息发放日期 （每月的哪一天发放）' AFTER `is_forbid_comment`;
ALTER TABLE `user` ADD COLUMN `give_scale`  decimal(11,2) NULL DEFAULT NULL COMMENT '利息发放比例  如10.5% 这边保存 10.5   ' AFTER `give_out_date`;
ALTER TABLE `user` MODIFY COLUMN `level`  int(11) NULL DEFAULT 0 COMMENT '用户等级0普通用户1会员用户 2vip用户' AFTER `app_version`;

CREATE TABLE `t_vip_dividend`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户ID',
  `dividend_date` datetime(0) NULL DEFAULT NULL COMMENT '应派息时间',
  `real_dividend_date` datetime(0) NULL DEFAULT NULL COMMENT '实际派息日期',
  `dividend_amount` decimal(16, 2) NULL DEFAULT NULL COMMENT '派息金额',
  `dividend_scale` decimal(10, 2) NULL DEFAULT NULL COMMENT '派息比例',
  `credit_amount` decimal(16, 2) NULL DEFAULT NULL COMMENT '授信金额',
  `is_dividend` bit(1) NULL DEFAULT NULL COMMENT '是否派息（0否 1是）',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;
SET FOREIGN_KEY_CHECKS = 1;


INSERT INTO `bulls`.`account_operate`(`feature_type`, `feature_name`, `description`, `app_description`, `account_type_id`, `account_operate_type_id`, `business_code`) VALUES (40122, 'vip_dividend_credit_subtract', '发放授信减少', '发放授信减少', 2, 2, 'vip_dividend');
INSERT INTO `bulls`.`account_operate`(`feature_type`, `feature_name`, `description`, `app_description`, `account_type_id`, `account_operate_type_id`, `business_code`) VALUES (40111, 'vip_dividend_balance_add', '授信发放余额增加', '授信发放余额增加', 1, 1, 'vip_dividend');

-- 设置 vip 权限
INSERT INTO resources(`name`, url, permission, parent_id, ismenu) VALUES('设置网站用户会员及发放日期和发放比例', 'user/list/app', 'user:setVip:app', 9, 0);
