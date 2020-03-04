#用户资金快照
#----------------------------------------init----------------------------------------
CREATE TABLE `trade_record_template` (
  `user_id` int(11) NOT NULL COMMENT 'user ID',
  `balance_amount` double(12,2) NOT NULL DEFAULT '0.00' COMMENT '可用余额',
  `frozen_amount` double(12,2) NOT NULL DEFAULT '0.00' COMMENT '冻结金额',
  `credit_amount` double(12,2) NOT NULL DEFAULT '0.00' COMMENT '授信金额',
  `freozen_credit_amount` double(12,2) NOT NULL DEFAULT '0.00' COMMENT '冻结授信额度',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账户表';

create table `trade_record_20190709` like trade_record_template;

insert into `trade_record_20190709`
select trade_record.*,now() as create_date
from (
	select user.id user_id,
		sum(case when account_type_id=1 then
			(case when account_operate_type_id=1 then amount
			when account_operate_type_id=2 then -amount
			when account_operate_type_id=5 then amount
			when account_operate_type_id=6 then -amount
			else 0 end)
		else 0 end) as balance_amount,
		sum(case when account_type_id=1 then
			(case when account_operate_type_id=3 then amount
			when account_operate_type_id=4 then -amount
			when account_operate_type_id=5 then -amount
			when account_operate_type_id=6 then amount
			else 0 end)
		else 0 end) as frozen_amount,
			sum(case when account_type_id=2 then
			(case when account_operate_type_id=1 then amount
			when account_operate_type_id=2 then -amount
			when account_operate_type_id=5 then amount
			when account_operate_type_id=6 then -amount
			else 0 end)
		else 0 end) as credit_amount,
		sum(case when account_type_id=2 then
			(case when account_operate_type_id=3 then amount
			when account_operate_type_id=4 then -amount
			when account_operate_type_id=5 then -amount
			when account_operate_type_id=6 then amount
			else 0 end)
		else 0 end) as freozen_credit_amount
	from user
	left join trade_record on user.id=trade_record.user_id
	where trade_record.create_date<'2019-07-09'
	group by user.id
)trade_record
where  trade_record.balance_amount!=0 or trade_record.frozen_amount!=0 or trade_record.credit_amount!=0 or trade_record.freozen_credit_amount!=0;


CREATE TABLE `account_operate_type`  (
  `id` int(11) NOT NULL COMMENT 'id',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'code',
  `description` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
  `sign_symbol` int(11) NOT NULL COMMENT '操作符',
  `sign_symbol_str` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作符str',
  `about_main_account` bit(1) NOT NULL COMMENT '主账户相关',
  `about_frozen_account` bit(1) NOT NULL COMMENT '冻结账户相关',
  `about_whole_account` bit(1) NULL DEFAULT NULL COMMENT '整体账户相关',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户资金账户的操作类型枚举' ROW_FORMAT = Dynamic;


INSERT INTO `account_operate_type` VALUES (1, 'add', '增加', 1, '+', b'1', b'0', b'1');
INSERT INTO `account_operate_type` VALUES (2, 'subtract', '减少', -1, '-', b'1', b'0', b'1');
INSERT INTO `account_operate_type` VALUES (3, 'frozen_add', '解冻返回', 1, '+', b'0', b'1', b'1');
INSERT INTO `account_operate_type` VALUES (4, 'frozen_subtract', '解冻扣除', -1, '-', b'0', b'1', b'1');
INSERT INTO `account_operate_type` VALUES (5, 'unfreeze', '解冻', 1, '+', b'1', b'1', b'0');
INSERT INTO `account_operate_type` VALUES (6, 'freeze', '冻结', -1, '-', b'1', b'1', b'0');

ALTER TABLE `bulls`.`account_operate`
ADD COLUMN `business_code` varchar(255) NULL AFTER `account_operate_type_id`;
update account_operate set business_code=feature_name;
update account_operate set business_code=replace(business_code,'_cash','');
update account_operate set business_code=replace(business_code,'_balance','');
update account_operate set business_code=replace(business_code,'_credit','');
update account_operate set business_code=replace(business_code,'_frozen_add','');
update account_operate set business_code=replace(business_code,'_add','');
update account_operate set business_code=replace(business_code,'_frozen_subtract','');
update account_operate set business_code=replace(business_code,'_subtract','');
update account_operate set business_code=replace(business_code,'_unfreeze','');
update account_operate set business_code=replace(business_code,'_freeze','');
update account_operate set business_code='use_cash_hongbao' where feature_name='use_cash_hongbao_balance_add';

create or replace view trade_record_view as
select  trade_record.id trade_record_id,
	trade_record.create_date,
	trade_record.aoe_type,
	trade_record.account_type_id,
	trade_record.account_operate_type_id,
	trade_record.amount*account_operate_type.sign_symbol credit_amount,
	trade_record.amount,
	trade_record.table_name,
	account_operate.business_code,
	account_operate.description operate_description,
	account_operate_type.code account_operate_type_code,
	account_operate_type.description account_operate_type_description,
	account_operate_type.sign_symbol,
	account_operate_type.about_main_account,
	account_operate_type.about_frozen_account,
	account_operate_type.about_whole_account,
	user.id user_id,
	user.true_name user_true_name,
	recharge.id recharge_id,
	recharge.order_no recharge_order_no,
	investment.id investment_id,
	investment.order_no investment_order_no,
	investment.amount investment_amount,
	t_goods_order.id goods_order_id,
	t_goods_order.order_no goods_order_no,
	t_goods_order.total_money goods_total_money
from trade_record
inner join account_operate on account_operate.feature_name=trade_record.aoe_type
inner join account_operate_type  on account_operate_type.id=account_operate.account_operate_type_id
left join user on user.id=trade_record.user_id
left join investment on trade_record.table_name='investment' and investment.id=trade_record.other_id
left join t_goods_order on trade_record.table_name='t_goods_order' and t_goods_order.id=trade_record.other_id
left join recharge on recharge.status=0  and recharge.other_id=trade_record.other_id
		and(
		(recharge.order_type='investment' and trade_record.table_name='investment' )
		or
		(recharge.order_type='goods' and trade_record.table_name='t_goods_order' )
		);
		
ALTER TABLE `bulls`.`trade_record` 
DROP INDEX `IDX_US_SO_TY`,
DROP INDEX `IDX_TABLE_NAME`,
ADD INDEX `IDX_TABLE_NAME`(`table_name`, `other_id`) USING BTREE;

ALTER TABLE `bulls`.`account_operate` 
ADD INDEX `account_operate_type_id`(`account_operate_type_id`) USING BTREE;

ALTER TABLE `bulls`.`project` 
ADD INDEX `ear_number`(`ear_number`) USING BTREE;

ALTER TABLE `bulls`.`order_done` 
ADD INDEX `order_type`(`order_type`, `order_no`, `order_status`) USING BTREE,
ADD INDEX `order_no`(`order_no`, `order_status`) USING BTREE,
ADD INDEX `create_date`(`create_date`) USING BTREE,
ADD INDEX `history_status`(`order_type`, `order_status`, `create_date`) USING BTREE;

create or replace view project_view as
select 	project.*,
	project.deadline + INTERVAL project.limit_days DAY  AS due_time,
	cast(project_property_view.yue_ling as UNSIGNED) as yue_ling
from project
left join project_property_view on project_property_view.id=project.id;


create or replace view investment_view as 
select investment.*,project.deadline,project.due_time,investment_status.pay_time,investment_status.buy_back_time, inviter.id as inviter_user_id
from investment
inner join project_view project on investment.project_id=project.id
inner join user operator on operator.id=investment.user_id
left join user inviter on inviter.invite_code=operator.invite_by_code
left join ( 
	select 	order_no,
		max(case when order_status='pay' then create_date else null end)  pay_time,
		max(case when order_status='success' then create_date else null end) buy_back_time
	from order_done
	where order_type='investment'
	group by order_no
)investment_status on investment_status.order_no=investment.order_no;


#财务报表-资产的销售/库存状态,历史节点报表
/*

select	property.row_num,
	property.ear_number,
	
	project.id current_project_id,
	project.deadline current_deadline,
	project.limit_days current_limit_days,
	project.due_time current_due_time,
	project.yue_ling current_yue_ling,
	project.unit_manage_price current_unit_manage_price,
	project.unit_feed_price current_unit_feed_price,
	
	investment.id current_investment_id,
	investment.order_no current_order_no,
	investment_status.pay_time current_pay_time,
	investment_status.buy_back_time current_buy_back_time,
	investment.amount current_amount,
	investment.interest_amount current_interest_amount,
	
	user.id current_user_id,
	user.true_name current_user_true_name,
	
	prior_project.id prior_project_id,
	prior_project.deadline prior_deadline,
	prior_project.limit_days prior_limit_days,
	prior_project.due_time prior_due_time,
	prior_project.yue_ling prior_yue_ling,
	prior_project.unit_manage_price prior_current_unit_manage_price,
	prior_project.unit_feed_price prior_current_unit_feed_price,
	
	prior_investment.id prior_investment_id,
	prior_investment.order_no prior_order_no,
	prior_investment_status.pay_time prior_pay_time,
	prior_investment_status.buy_back_time prior_buy_back_time,
	prior_investment.amount prior_amount,
	prior_investment.interest_amount prior_interest_amount,
	
	prior_user.id prior_user_id,
	prior_user.true_name prior_user_ture_name,
	
	if(project.deadline < date_point.date_point,true,false)  as is_sold,
	if(project.deadline < date_point.date_point and project.due_time > date_point.date_point,false,true)  as is_raised_by_us,
	if(project.deadline < date_point.date_point and (investment_status.buy_back_time is null or investment_status.buy_back_time>=date_point.date_point ),true,false)  as is_sale,#客户购买后,未回购

	if(investment.id is not null,project.deadline,prior_project.deadline) as last_deadline,
	if(investment.id is not null,project.limit_days,prior_project.limit_days) as last_limit_days,
	if(investment.id is not null,project.due_time,prior_project.due_time) as last_due_time,
	
	if(investment.id is not null,investment.order_no,prior_investment.order_no) as last_order_no,
	if(investment.id is not null,investment_status.pay_time,prior_investment_status.pay_time) as last_pay_time,
	if(investment.id is not null,investment_status.buy_back_time,prior_investment_status.buy_back_time) as last_buy_back_time,
	if(investment.id is not null,investment.amount,prior_investment.amount) as last_amount,
	if(investment.id is not null,investment.interest_amount,prior_investment.interest_amount) as last_interest_amount,
	if(investment.id is not null,user.id,prior_user.id) as last_user_id,
	if(investment.id is not null,user.true_name,prior_user.true_name) as last_user_true_name,
	
	if(project.deadline is null or project.deadline >= date_point.date_point or project.due_time<date_point.begin_month , 0 ,
						TimeStampDiff(DAY,
							if(project.deadline > date_point.begin_month , project.deadline , date_point.begin_month),
							if(project.due_time > date_point.date_point , date_point.date_point , project.due_time)
						)
	) current_month_manager_day,
	if(project.deadline is null or project.deadline >= date_point.date_point or project.due_time<date_point.begin_month , 0 ,
						TimeStampDiff(DAY,
							if(project.deadline > date_point.begin_month , project.deadline , date_point.begin_month),
							if(project.due_time > date_point.date_point , date_point.date_point , project.due_time)
						)
	)*(project.unit_manage_price+project.unit_feed_price) current_month_manager,
	if(project.deadline is null or project.deadline >= date_point.date_point , 0 ,
						TimeStampDiff(DAY,project.deadline,
							if(project.due_time > date_point.date_point , date_point.date_point , project.due_time)
						)
	) sum_manager_day,
	if(project.deadline is null or project.deadline >= date_point.date_point , 0 ,
						TimeStampDiff(DAY,project.deadline,
							if(project.due_time > date_point.date_point , date_point.date_point , project.due_time)
						)
	) *(project.unit_manage_price+project.unit_feed_price) sum_manager_fee

from (
	SELECT  @rownum:=@rownum+1 as row_num,
			project.*
	from(select project.ear_number,max(id) project_id
			from project
			where create_date<'2019-07-10'
			group by project.ear_number
	)project,
	(SELECT @rownum:=0)r		
)property
inner join (
	select	date_format('2019-07-10','%y-%m-%d') date_point,
		date_add(date_add(date_format('2019-07-10','%y-%m-%d'),interval -1 day),interval -day(date_add(date_format('2019-07-10','%y-%m-%d'),interval -1 day))+1 day) as begin_month
	from dual
)date_point on true
inner join project_view project on property.project_id=project.id
left join investment on investment.order_status in (1,2) and investment.project_id=project.id and project.deadline < date_point.date_point
left join ( 
	select 	order_no,
		max(case when order_status='pay' then create_date else null end)  pay_time,
		max(case when order_status='success' then create_date else null end) buy_back_time
	from order_done
	where order_type='investment'
	and create_date<'2019-07-10'
	group by order_no
)investment_status on investment_status.order_no=investment.order_no
left join user on user.id=investment.user_id

left join project_view prior_project on prior_project.id=project.parent_id
left join investment prior_investment on prior_investment.order_status in (1,2) and prior_investment.project_id=prior_project.id
left join ( 
	select 	order_no,
		max(case when order_status='pay' then create_date else null end)  pay_time,
		max(case when order_status='success' then create_date else null end) buy_back_time
	from order_done
	where order_type='investment'
	and create_date<'2019-07-10'
	group by order_no
)prior_investment_status on prior_investment_status.order_no=prior_investment.order_no
left join user prior_user on prior_user.id=prior_investment.user_id;

*/


create or replace view property_sales_store_status_view as
select	1 row_num,
	property.ear_number,
	
	project.id current_project_id,
	project.deadline current_deadline,
	project.limit_days current_limit_days,
	project.due_time current_due_time,
	project.yue_ling current_yue_ling,
	project.unit_manage_price current_unit_manage_price,
	project.unit_feed_price current_unit_feed_price,
	
	investment.id current_investment_id,
	investment.order_no current_order_no,
	investment_status.pay_time current_pay_time,
	investment_status.buy_back_time current_buy_back_time,
	investment.amount current_amount,
	investment.interest_amount current_interest_amount,
	
	user.id current_user_id,
	user.true_name current_user_true_name,
	
	prior_project.id prior_project_id,
	prior_project.deadline prior_deadline,
	prior_project.limit_days prior_limit_days,
	prior_project.due_time prior_due_time,
	prior_project.yue_ling prior_yue_ling,
	prior_project.unit_manage_price prior_current_unit_manage_price,
	prior_project.unit_feed_price prior_current_unit_feed_price,
	
	prior_investment.id prior_investment_id,
	prior_investment.order_no prior_order_no,
	prior_investment_status.pay_time prior_pay_time,
	prior_investment_status.buy_back_time prior_buy_back_time,
	prior_investment.amount prior_amount,
	prior_investment.interest_amount prior_interest_amount,
	
	prior_user.id prior_user_id,
	prior_user.true_name prior_user_ture_name,
	
	if(project.deadline < date_point.date_point,true,false)  as is_sold,
	if(project.deadline < date_point.date_point and project.due_time > date_point.date_point,false,true)  as is_raised_by_us,
	if(project.deadline < date_point.date_point and (investment_status.buy_back_time is null or investment_status.buy_back_time>=date_point.date_point ),true,false)  as is_sale,#客户购买后,未回购

	if(investment.id is not null,project.deadline,prior_project.deadline) as last_deadline,
	if(investment.id is not null,project.limit_days,prior_project.limit_days) as last_limit_days,
	if(investment.id is not null,project.due_time,prior_project.due_time) as last_due_time,
	
	if(investment.id is not null,investment.order_no,prior_investment.order_no) as last_order_no,
	if(investment.id is not null,investment_status.pay_time,prior_investment_status.pay_time) as last_pay_time,
	if(investment.id is not null,investment_status.buy_back_time,prior_investment_status.buy_back_time) as last_buy_back_time,
	if(investment.id is not null,investment.amount,prior_investment.amount) as last_amount,
	if(investment.id is not null,investment.interest_amount,prior_investment.interest_amount) as last_interest_amount,
	if(investment.id is not null,user.id,prior_user.id) as last_user_id,
	if(investment.id is not null,user.true_name,prior_user.true_name) as last_user_true_name,
	
	if(project.deadline is null or project.deadline >= date_point.date_point or project.due_time<date_point.begin_month , 0 ,
						TimeStampDiff(DAY,
							if(project.deadline > date_point.begin_month , project.deadline , date_point.begin_month),
							if(project.due_time > date_point.date_point , date_point.date_point , project.due_time)
						)
	) current_month_manager_day,
	if(project.deadline is null or project.deadline >= date_point.date_point or project.due_time<date_point.begin_month , 0 ,
						TimeStampDiff(DAY,
							if(project.deadline > date_point.begin_month , project.deadline , date_point.begin_month),
							if(project.due_time > date_point.date_point , date_point.date_point , project.due_time)
						)
	)*(project.unit_manage_price+project.unit_feed_price) current_month_manager,
	if(project.deadline is null or project.deadline >= date_point.date_point , 0 ,
						TimeStampDiff(DAY,project.deadline,
							if(project.due_time > date_point.date_point , date_point.date_point , project.due_time)
						)
	) sum_manager_day,
	if(project.deadline is null or project.deadline >= date_point.date_point , 0 ,
						TimeStampDiff(DAY,project.deadline,
							if(project.due_time > date_point.date_point , date_point.date_point , project.due_time)
						)
	) *(project.unit_manage_price+project.unit_feed_price) sum_manager_fee

from (	
	select project.ear_number,max(id) project_id
	from project
	group by project.ear_number
)property
inner join (
	select	date_format(now(),'%y-%m-%d')+interval 1 day date_point,
		date_add(date_add(date_format(now(),'%y-%m-%d'),interval -1 day),interval -day(date_add(date_format(now(),'%y-%m-%d'),interval -1 day))+1 day) as begin_month
	from dual
)date_point on true
inner join project_view project on property.project_id=project.id
left join investment on investment.order_status in (1,2) and investment.project_id=project.id
left join ( 
	select 	order_no,
		max(case when order_status='pay' then create_date else null end)  pay_time,
		max(case when order_status='success' then create_date else null end) buy_back_time
	from order_done
	where order_type='investment'
	group by order_no
)investment_status on investment_status.order_no=investment.order_no
left join user on user.id=investment.user_id

left join project_view prior_project on prior_project.id=project.parent_id
left join investment prior_investment on prior_investment.order_status in (1,2) and prior_investment.project_id=prior_project.id
left join ( 
	select 	order_no,
		max(case when order_status='pay' then create_date else null end)  pay_time,
		max(case when order_status='success' then create_date else null end) buy_back_time
	from order_done
	where order_type='investment'
	group by order_no
)prior_investment_status on prior_investment_status.order_no=prior_investment.order_no
left join user prior_user on prior_user.id=prior_investment.user_id;



insert into resources values(null,'牛只销售库存状态','report/propertySalesStoreStatusHistory/list','report:propertySalesStoreStatusHistory:list',63,1,null,null);
insert into resources values(null,'牛只销售库存状态-导出','report/propertySalesStoreStatusHistory/export','report:propertySalesStoreStatusHistory:export',LAST_INSERT_ID(),0,null,null);


create or replace view good_order_view as 
select t_goods_order.*,goods_order_status.pay_time,goods_order_status.refund_finish_time,inviter.id as inviter_user_id
from t_goods_order
inner join user operator on operator.id=t_goods_order.user_id
left join user inviter on inviter.invite_code=operator.invite_by_code
left join ( 
	select 	order_no,
		max(case when order_status='pay' then create_date else null end)  pay_time,
		max(case when order_status='refund_finish' then create_date else null end)  refund_finish_time
	from order_done
	where order_type='goods'
	group by order_no
)goods_order_status on goods_order_status.order_no=t_goods_order.order_no;


INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (0, '回购查询', 'project/listBuyBack', 'project:buyBack:view', 4, 1, 1, '');
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (0, '导出回购查询', 'export/reportListBuyBack', 'project:reportListBuyBack:view', 4, 0, NULL, '');

INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (0, '回购统计', 'report/listBuyBackTJ', 'report:buyBack', 63, 1, NULL, '');
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (0, '导出回购统计', 'export/reportListBuyBackTJ', 'report:reportListBuyBackTJ', 63, 0, NULL, '');

INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (0, '授信资金查询', 'report/listCreditFunds', 'report:listCreditFunds', 63, 1, NULL, '');
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (0, '导出授信资金查询', 'report/reportListCreditFunds', 'report:reportListCreditFunds', 63, 0, NULL, '');
 
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '认养统计', 'report/investStatement', 'invest:investStatement', 63, 1, NULL, NULL);
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '认养统计-导出', 'report/reportInvestStatement', 'invest:reportInvestStatement', 63, 0, NULL, NULL);

insert into resources values(null,'客户资金汇总','report/assetsTradeHistorySum/list','report:assetsTradeHistorySum:list',63,1,null,null);
insert into resources values(null,'客户资金汇总-导出','report/assetsTradeHistorySum/export','report:assetsTradeHistorySum:export',LAST_INSERT_ID(),0,null,null);


#-----------------商城明细列表-------------------------------------------------------------------------
ALTER TABLE `bulls`.`t_goods_order_detail` 
ADD COLUMN `balance_pay_money` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '余额支付' AFTER `buy_price`,
ADD COLUMN `cash_pay_money` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '现金支付' AFTER `balance_pay_money`,
ADD COLUMN `credit_pay_money` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '授信支付' AFTER `cash_pay_money`,
ADD COLUMN `hongbao_money` decimal(10, 2) UNSIGNED NULL DEFAULT NULL COMMENT '红包支付' AFTER `credit_pay_money`;

update t_goods_order_detail set balance_pay_money = 0 where balance_pay_money is null;
update t_goods_order_detail set cash_pay_money = sale_price*count where cash_pay_money is null;
update t_goods_order_detail set credit_pay_money = 0 where credit_pay_money is null;
update t_goods_order_detail set hongbao_money = 0 where hongbao_money is null;

INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '明细查询', 'shop/orderDetailList', 'shop.orderDetailList', 209, 1, NULL, NULL);
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '导出商品明细列表', 'report/exportOrderDetailList', 'report:exportOrderDetailList', 258, 0, NULL, NULL);


INSERT  INTO resources(NAME, url, permission, parent_id, ismenu) VALUES('导出支付列表', 'recharge/export', 'recharge:export', 13, 0);
INSERT  INTO resources(NAME, url, permission, parent_id, ismenu) VALUES('导出提现列表', 'withdraw/export', 'withdraw:export', 14, 0);
INSERT  INTO resources(NAME, url, permission, parent_id, ismenu) VALUES('导出客户资金明细', 'trade/exportDetail', 'trade:exportDetail', 110, 0);



ALTER TABLE `bulls`.`user`
MODIFY COLUMN `data_source` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '数据来源  0：平台，1：亿亿理财\r\n2：阿里分发市场 \r\n3：应用宝 \r\n4：华为应用市场 \r\n5：vivo应用商店 \r\n6：oppo \r\n7：魅族\r\n8：联通wo \r\n9：联想 \r\n10：百度 \r\n' AFTER `client`;


update tm_dict  td   set  td.memo='ranchBase.html' ,td.tp='奔富牧业繁育基地'  where   td.t_key='prairie' and td.t_value=1;
update tm_dict  td   set  td.memo='ranch.html?mcid=2' ,td.tp='奔富牧业001号牧场'  where   td.t_key='prairie' and td.t_value=2;
update tm_dict  td   set  td.memo='ranch.html?mcid=3' ,td.tp='奔富牧业002号牧场'  where   td.t_key='prairie' and td.t_value=3;
update tm_dict  td   set  td.memo='ranch.html?mcid=4' ,td.tp='奔富牧业003号牧场'  where   td.t_key='prairie' and td.t_value=4;
update tm_dict  td   set  td.memo='ranch.html?mcid=5' ,td.tp='奔富牧业004号牧场'  where   td.t_key='prairie' and td.t_value=5;
update tm_dict  td   set  td.memo='ranch.html?mcid=6' ,td.tp='奔富牧业005号牧场'  where   td.t_key='prairie' and td.t_value=6;
update tm_dict  td   set  td.memo='ranch.html?mcid=7' ,td.tp='奔富牧业006号牧场'  where   td.t_key='prairie' and td.t_value=7;
update tm_dict  td   set  td.memo='ranch.html?mcid=8' ,td.tp='奔富牧业007号牧场'  where   td.t_key='prairie' and td.t_value=8;
update tm_dict  td   set  td.memo='ranch.html?mcid=9' ,td.tp='奔富牧业008号牧场'  where   td.t_key='prairie' and td.t_value=9;
update tm_dict  td   set  td.memo='mc.html' ,td.tp='奔富牧业009号牧场'  where   td.t_key='prairie' and td.t_value=10;
update tm_dict  td   set  td.memo='mc.html' ,td.tp='奔富牧业010号牧场'  where   td.t_key='prairie' and td.t_value=11;
update tm_dict  td   set  td.memo='mc.html' ,td.tp='奔富牧业011号牧场'  where   td.t_key='prairie' and td.t_value=12;
update tm_dict  td   set  td.memo='mc.html' ,td.tp='奔富牧业012号牧场'  where   td.t_key='prairie' and td.t_value=13;
update tm_dict  td   set  td.memo='mc.html' ,td.tp='奔富牧业013号牧场'  where   td.t_key='prairie' and td.t_value=14;
update tm_dict  td   set  td.memo='mc.html' ,td.tp='奔富牧业014号牧场'  where   td.t_key='prairie' and td.t_value=15;
update tm_dict  td   set  td.memo='mc.html' ,td.tp='奔富牧业015号牧场'  where   td.t_key='prairie' and td.t_value=16;
update tm_dict  td   set  td.memo='mc.html' ,td.tp='奔富牧业016号牧场'  where   td.t_key='prairie' and td.t_value=17;


INSERT INTO `resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '订单列表查询-导出', 'investment/orderListReport', 'investment:export', 4, 0, NULL, NULL);
