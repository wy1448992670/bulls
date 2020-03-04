CREATE TABLE `payment_check` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`order_no` varchar(255) DEFAULT NULL COMMENT '订单号',
	`out_order_no` varchar(255) DEFAULT NULL COMMENT '渠道订单号',
	`pay_channel` varchar(20) DEFAULT NULL COMMENT '充值方式:OutPayEnum',
	`amount` double(12,2) DEFAULT NULL COMMENT '交易金额',
	`fee` double(12,2) DEFAULT NULL COMMENT '费用金额',
	`create_date` datetime DEFAULT NULL COMMENT '数据创建时间',
	`pay_date` datetime DEFAULT NULL COMMENT '支付时间',
	`deal_date` datetime DEFAULT NULL COMMENT '记账时间',
	`status` int(11) DEFAULT '1' COMMENT '交易状态:0成功 1处理中 2失败',
	`type` int(11) DEFAULT '0' COMMENT '交易类型:0支付 1退款 2未知',
	`remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
	`refund_order_no` varchar(255) DEFAULT NULL COMMENT '退款订单号,记录有退款的交易的退款单号外键',
	`refund_out_order_no` varchar(255) DEFAULT NULL COMMENT '渠道退款订单号,记录有退款的交易的渠道退款单号外键',
	PRIMARY KEY (`id`),
	UNIQUE KEY `order_no_type` (`order_no`,`type`) USING BTREE,
	UNIQUE KEY `out_order_no_type` (`out_order_no`,`type`) USING BTREE,
	UNIQUE KEY `refund_order_no_type` (`refund_order_no`,`type`) USING BTREE,
	UNIQUE KEY `refund_out_order_no_type` (`refund_out_order_no`,`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='支付核对表';


/*
INSERT INTO resources
select NULL,'查询全部回购权限','project/buyBack/viewall','project:buyBack:viewall',resources.id,0,NULL,NULL
from resources 
where permission='project:buyBack:view';
*/



create or replace view payment_check_view
as 
select  ifnull(recharge.order_no,payment_check.order_no) as order_no
	,recharge.id is null  recharge_lose_warning
	,payment_check.id is null check_lose_warning
	
	,recharge.create_date as recharge_create_date
	,payment_check.pay_date as check_create_date
	,ifnull(recharge.create_date,payment_check.pay_date) as create_date
	,CAST(recharge.create_date AS DATE)<>CAST(payment_check.pay_date AS DATE) as create_date_warning
	
	,recharge.update_date as recharge_update_date
	,payment_check.deal_date as check_update_date
	,ifnull(recharge.update_date,payment_check.deal_date) as update_date
	,CAST(recharge.update_date AS DATE)<>CAST(payment_check.deal_date AS DATE) as update_date_warning
	
	,recharge.amount as recharge_amount
	,payment_check.amount as check_amount
	,ifnull(recharge.amount,payment_check.amount) as amount
	,recharge.amount<>payment_check.amount as amount_warning
	
	,recharge.pay_channel as recharge_pay_channel
	,payment_check.pay_channel as check_pay_channel
	,ifnull(recharge.pay_channel,payment_check.pay_channel) as pay_channel
	,recharge.pay_channel<>payment_check.pay_channel as pay_channel_warning

	,recharge.status as recharge_status
	,payment_check.status as check_status
	,ifnull(recharge.status,payment_check.status) as status
	,recharge.status<>payment_check.status as status_warning
	
	,payment_check.fee
	,refund.amount as refund_amount 
	,refund.status as refund_status 
	,-refund.fee as refund_fee
	,recharge.id IS NULL or payment_check.id IS NULL or CAST( recharge.create_date AS DATE ) <> CAST( payment_check.pay_date AS DATE )
	or CAST( recharge.update_date AS DATE ) <> CAST( payment_check.deal_date AS DATE ) or recharge.amount <> payment_check.amount
	or recharge.pay_channel <> payment_check.pay_channel or recharge.STATUS <> payment_check.STATUS as warning_tag
from payment_check
left join recharge 
	on payment_check.order_no=recharge.order_no 
left join payment_check refund on refund.type=1 and refund.refund_order_no=payment_check.order_no
where  payment_check.type in (0,2)
union
select  ifnull(recharge.order_no,payment_check.order_no) as order_no
	,recharge.id is null  recharge_lose_warning
	,payment_check.id is null check_lose_warning

	,recharge.create_date as recharge_create_date
	,payment_check.pay_date as check_create_date
	,ifnull(recharge.create_date,payment_check.pay_date) as create_date
	,CAST(recharge.create_date AS DATE)<>CAST(payment_check.pay_date AS DATE) as create_date_warning
	
	,recharge.update_date as recharge_update_date
	,payment_check.deal_date as check_update_date
	,ifnull(recharge.update_date,payment_check.deal_date) as update_date
	,CAST(recharge.update_date AS DATE)<>CAST(payment_check.deal_date AS DATE) as update_date_warning
	
	,recharge.amount as recharge_amount
	,payment_check.amount as check_amount
	,ifnull(recharge.amount,payment_check.amount) as amount
	,recharge.amount<>payment_check.amount as amount_warning
	
	,recharge.pay_channel as recharge_pay_channel
	,payment_check.pay_channel as check_pay_channel
	,ifnull(recharge.pay_channel,payment_check.pay_channel) as pay_channel
	,recharge.pay_channel<>payment_check.pay_channel as pay_channel_warning

	,recharge.status as recharge_status
	,payment_check.status as check_status
	,ifnull(recharge.status,payment_check.status) as status
	,recharge.status<>payment_check.status as status_warning
	
	,payment_check.fee
	,refund.amount as refund_amount 
	,refund.status as refund_status 
	,-refund.fee as refund_fee
	,recharge.id IS NULL or payment_check.id IS NULL or CAST( recharge.create_date AS DATE ) <> CAST( payment_check.pay_date AS DATE )
	or CAST( recharge.update_date AS DATE ) <> CAST( payment_check.deal_date AS DATE ) or recharge.amount <> payment_check.amount
	or recharge.pay_channel <> payment_check.pay_channel or recharge.STATUS <> payment_check.STATUS as warning_tag
from payment_check
right join recharge on payment_check.order_no=recharge.order_no and payment_check.type in (0,2) 
left join payment_check refund on refund.type=1 and refund.refund_order_no=payment_check.order_no
where recharge.status in (0,1);

INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '支付对账查询', 'trade/checkPaymentList', 'trade:checkPaymentList', 3, 1, NULL, NULL);
