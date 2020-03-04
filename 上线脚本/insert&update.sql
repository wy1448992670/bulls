ALTER TABLE `bulls`.`user_address`
ADD COLUMN `p_id` int(16) NULL DEFAULT NULL COMMENT '省份ID' AFTER `remarks`,
ADD COLUMN `c_id` int(16) NULL DEFAULT NULL COMMENT '市ID' AFTER `p_id`;

INSERT INTO `account_operate` VALUES (10116, 'withdraw_balance_freeze', '提现','提现', 1, 6);
INSERT INTO `account_operate` VALUES (10114, 'withdraw_balance_frozen_subtract', '提现','提现', 1, 4);
INSERT INTO `account_operate` VALUES (10115, 'withdraw_balance_unfreeze', '提现','提现', 1, 5);
INSERT INTO `account_operate` VALUES (10101, 'withdraw_cash_add', '提现','提现', 0, 1);
INSERT INTO `account_operate` VALUES (10211, 'use_cash_hongbao_balance_add', '现金红包','现金红包', 1, 1);
INSERT INTO `account_operate` VALUES (10302, 'recharge_cash_subtract', '充值','充值', 0, 2);
INSERT INTO `account_operate` VALUES (10311, 'recharge_balance_add', '充值','充值', 1, 1);
INSERT INTO `account_operate` VALUES (20102, 'invest_cash_subtract', '购买畜牧','领养', 0, 2);
INSERT INTO `account_operate` VALUES (20112, 'invest_balance_subtract', '购买畜牧','领养', 1, 2);
INSERT INTO `account_operate` VALUES (20116, 'invest_balance_freeze', '购买畜牧','领养', 1, 6);
INSERT INTO `account_operate` VALUES (20114, 'invest_balance_frozen_subtract', '购买畜牧','领养', 1, 4);
INSERT INTO `account_operate` VALUES (20121, 'invest_credit_add', '购买畜牧','领养', 2, 1);
INSERT INTO `account_operate` VALUES (20215, 'invest_close_balance_unfreeze', '取消购买畜牧','领养订单取消', 1, 5);
INSERT INTO `account_operate` VALUES (20311, 'invest_refund_balance_add', '畜牧退单','领养订单退单', 1, 1);
INSERT INTO `account_operate` VALUES (20422, 'invest_buyback_interest_credit_subtract', '利息兑付', '出售(授信减少)',2, 2);
INSERT INTO `account_operate` VALUES (20411, 'invest_buyback_interest_balance_add', '利息兑付','出售(冻结利润)', 1, 1);
INSERT INTO `account_operate` VALUES (20511, 'invest_buyback_principal_balance_add', '本金兑付','出售(出售款项)', 1, 1);
INSERT INTO `account_operate` VALUES (30102, 'goodsorder_cash_subtract', '购买商品','购买', 0, 2);
INSERT INTO `account_operate` VALUES (30112, 'goodsorder_balance_subtract', '购买商品','购买', 1, 2);
INSERT INTO `account_operate` VALUES (30116, 'goodsorder_balance_freeze', '购买商品','购买', 1, 6);
INSERT INTO `account_operate` VALUES (30114, 'goodsorder_balance_frozen_subtract', '购买商品','购买', 1, 4);
INSERT INTO `account_operate` VALUES (30122, 'goodsorder_credit_subtract', '购买商品','购买', 2, 2);
INSERT INTO `account_operate` VALUES (30126, 'goodsorder_credit_freeze', '购买商品','购买', 2, 6);
INSERT INTO `account_operate` VALUES (30124, 'goodsorder_credit_frozen_subtract', '购买商品','购买', 2, 4);
INSERT INTO `account_operate` VALUES (30215, 'goodsorder_close_balance_unfreeze', '关闭商品订单','商城订单取消', 1, 5);
INSERT INTO `account_operate` VALUES (30225, 'goodsorder_close_credit_unfreeze', '关闭商品订单','商城订单取消', 2, 5);
INSERT INTO `account_operate` VALUES (30301, 'goodsorder_refund_cash_add', '商品退单','商城订单退款', 0, 1);
INSERT INTO `account_operate` VALUES (30311, 'goodsorder_refund_balance_add', '商品退单','商城订单退款', 1, 1);
INSERT INTO `account_operate` VALUES (30321, 'goodsorder_refund_credit_add', '商品退单','商城订单退款', 2, 1);


ALTER TABLE `bulls`.`trade_record`
ADD FOREIGN KEY (`aoe_type`) REFERENCES `bulls`.`account_operate` (`feature_name`) ON DELETE RESTRICT ON UPDATE CASCADE;

ALTER TABLE `hongbao`
ADD COLUMN `limit_day` int(11) NULL COMMENT '最低投资天数' AFTER `limit_amount`;

ALTER TABLE `hongbao_template`
ADD COLUMN `limit_day` int(11) NULL COMMENT '最低投资天数' AFTER `limit_amount`;


ALTER TABLE `hongbao`
ADD COLUMN `activity_detail_id` INT(11) NULL COMMENT 'activity_detail.id' AFTER `admin_id`;

ALTER TABLE `hongbao`
ADD COLUMN `trigger_type` INT(10) NULL COMMENT '1.注册 2.投资 3.消费' AFTER `activity_detail_id`;

ALTER TABLE `hongbao`
ADD COLUMN `other_id` INT(11) NULL COMMENT 'trigger_type[=1:user.id,=2:investment.id,=3:goods.id]' AFTER `trigger_type`;


#---------------------------------活动
insert into activity_detail
values(1,108,'注册红包',null,null,1,1,1,1,1,1,1);
insert into activity_detail
values(2,107,'邀请首投',null,null,1,2,4,2,1,1,0);
insert into activity_detail
values(3,107,'邀请用户投资',null,null,1,2,4,3,1,1,3);
insert into activity_detail
values(4,107,'邀请用户投资',null,null,1,2,4,3,1,2,3);
insert into activity_detail
values(5,107,'邀请用户投资',null,null,1,2,4,3,1,3,3);



#id,	活动id, 赠送对象(1,3推荐人), cnt, type(1现金,2投资,3购物),金额, 随机金额, 启用金额, 启用期限,有效期起止,有效天数
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 1, 1, 1, 2, 16.00, 16.00, 0, 30, NULL, NULL, 3);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 1, 1, 1, 2, 50.00, 50.00, 0, 60, NULL, NULL, 3);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 1, 1, 1, 2, 80.00, 80.00, 0, 90, NULL, NULL, 7);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 1, 1, 2, 2, 118.00, 118.00, 0, 180, NULL, NULL, 14);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 1, 1, 2, 2, 200.00, 200.00, 0, 360, NULL, NULL, 21);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 1, 1, 2, 3, 54.00, 54.00, 500, 0, NULL, NULL, 7);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 1, 1, 1, 3, 109.00, 109.00, 1000, 0, NULL, NULL, 14);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 2, 1, 2, 2, 200.00, 200.00, 0, 270, NULL, NULL, 21);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 2, 1, 2, 2, 100.00, 100.00, 0, 180, NULL, NULL, 14);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 2, 1, 1, 2, 88.00, 88.00, 0, 90, NULL, NULL, 7);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 2, 3, 2, 2, 200.00, 200.00, 0, 270, NULL, NULL, 21);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 2, 3, 2, 2, 100.00, 100.00, 0, 180, NULL, NULL, 14);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 2, 3, 1, 2, 88.00, 88.00, 0, 90, NULL, NULL, 7);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 3, 1, 1, 1, 10.00, 10.00, 0, 0, NULL, NULL, 365);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 3, 3, 1, 1, 10.00, 10.00, 0, 0, NULL, NULL, 365);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 4, 1, 1, 1, 5.00, 5.00, 0, 0, NULL, NULL, 365);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 4, 3, 1, 1, 5.00, 5.00, 0, 0, NULL, NULL, 365);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 5, 1, 1, 1, 5.00, 50.00, 0, 0, NULL, NULL, 365);
INSERT INTO `activity_detail_send_hongbao` VALUES (null, 5, 3, 1, 1, 5.00, 50.00, 0, 0, NULL, NULL, 365);




ALTER TABLE `account_operate`
ADD COLUMN `app_description` INT(8) NULL COMMENT '操作类型描述（app显示）' AFTER `description`;
