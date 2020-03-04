DROP TABLE IF EXISTS `account_operate`;
CREATE TABLE `account_operate`  (
  `feature_type` int(8) NOT NULL,
  `feature_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `app_description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `account_type_id` int(8) NOT NULL COMMENT 'see:AccountTypeEnum  0.cash 1.balance 2.credit',
  `account_operate_type_id` int(8) NOT NULL COMMENT 'see:AccountOperateTypeEnum 1.add 2.subtract 3.frozen_add 4.frozen_subtract 5.unfrozen 6.frozen',
  PRIMARY KEY (`feature_type`) USING BTREE,
  UNIQUE INDEX `feature_name`(`feature_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

ALTER TABLE `bulls`.`trade_record` 
ADD COLUMN `account_type_id` int(8) NULL COMMENT 'see:AccountTypeEnum  0.cash 1.balance 2.credit' AFTER `update_date`,
ADD COLUMN `account_operate_type_id` int(8) NULL COMMENT 'see:AccountOperateTypeEnum 1.add 2.subtract 3.frozen_add 4.frozen_subtract 5.unfrozen 6.frozen' AFTER `account_type_id`;



DROP TABLE IF EXISTS `activity_detail`;
CREATE TABLE `activity_detail`  (
  `id` int(11) NOT NULL COMMENT '一个活动可又多个活动详情,可以是分段活动(时间分段,条件分段),或同享活动',
  `activity_id` int(11) NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动详情名',
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `status` int(10) UNSIGNED NOT NULL COMMENT '0不执行 1执行',
  `trigger_type` int(10) UNSIGNED NOT NULL COMMENT '1.注册 2.投资 3.消费',
  `condition_value_type` int(10) UNSIGNED NOT NULL COMMENT '统计数据内容:1.当比金额 2.累计订单数量|注册数 3.累计金额 4.首投',
  `condition_target` int(10) UNSIGNED NOT NULL COMMENT '统计目标:1.操作人 2.被邀请人(有邀请人的操作人) 3.邀请人',
  `condition_date_type` int(10) UNSIGNED NOT NULL COMMENT '统计时间范围:1.全局 2.活动期间 3.活动详情期间(分段活动)',
  `threshold_value` decimal(12, 2) UNSIGNED NOT NULL COMMENT '条件值,阈值',
  `cycle_vertices` decimal(12, 2) UNSIGNED NOT NULL COMMENT '循环上限:等于0不循环,大于0循环享受',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `activity_id`(`activity_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `activity_detail_send_hongbao`;
CREATE TABLE `activity_detail_send_hongbao`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `activity_detail_id` int(11) NOT NULL,
  `target_type` int(10) NOT NULL DEFAULT 1 COMMENT '1.操作人 3.推荐人',
  `cnt` int(10) UNSIGNED NOT NULL COMMENT '赠送数量',
  `type` int(10) UNSIGNED NOT NULL COMMENT '红包类型:1.现金 2.投资 3.优惠券',
  `amount` decimal(12, 2) UNSIGNED NOT NULL COMMENT '红包金额',
  `random_amount` decimal(12, 2) UNSIGNED NOT NULL COMMENT '随机红包金额 amount-random_amount',
  `limit_amount` int(10) UNSIGNED NOT NULL COMMENT '起用金额',
  `limit_day` int(10) UNSIGNED NOT NULL COMMENT '起用期限',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '红包有效期:起',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '红包有效期:止',
  `effective_days` int(10) UNSIGNED NULL DEFAULT NULL COMMENT '有效期天数,起止和长度二选一',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `activity_detail_id`(`activity_detail_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

create view investment_view as 
select investment.*,project.deadline,inviter.id as inviter_user_id
from investment
inner join project on investment.project_id=project.id
inner join user operator on operator.id=investment.user_id
left join user inviter on inviter.invite_code=operator.invite_by_code;

create view login_record_invite_view as 
select login_record.*,inviter.id as inviter_user_id
from login_record
JOIN user operator ON operator.id = login_record.user_id
LEFT JOIN user inviter ON inviter.invite_code = operator.invite_by_code;



#增加商品库存单位
ALTER TABLE `t_goods` ADD COLUMN `stock_unit`  varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL AFTER `stock`;