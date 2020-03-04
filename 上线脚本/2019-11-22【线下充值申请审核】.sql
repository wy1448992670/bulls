CREATE TABLE `recharge_offline_apply` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`applyer_id`  int(11) NOT NULL COMMENT '申请人' ,
`create_time`  datetime NOT NULL COMMENT '申请时间' ,
`sourcer`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发起人' ,
`money`  decimal(12,2) NOT NULL COMMENT '充值金额' ,
`user_id`  int(11) NOT NULL COMMENT '充值人' ,
`bankcard_num`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡号' ,
`serial_number`  varchar(120) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行流水号' ,
`voucher_pic`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '线下转账凭证' ,
`auditor_id`  int(11) NULL DEFAULT NULL COMMENT '审核人' ,
`audit_time`  datetime NULL DEFAULT NULL COMMENT '审核时间' ,
`audit_remark`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核内容' ,
`state`  int(11) NOT NULL DEFAULT 0 COMMENT '状态 0申请中 1审核通过 -1审核不通过' ,
`recharge_id`  int(11) NULL DEFAULT NULL COMMENT '对应的充值单号' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Dynamic
;

create or replace view project_view as
select 	project.*,
	project.deadline + INTERVAL project.limit_days DAY  AS due_time,
	cast(project_property_view.yue_ling as UNSIGNED) as yue_ling,
	investment_status.pay_time,
	investment_status.buy_back_time
from project
left join project_property_view on project_property_view.id=project.id
left join investment on investment.order_status in (1,2) and investment.project_id=project.id
left join (
	select 	order_no,
		max(case when order_status='pay' then create_date else null end)  pay_time,
		max(case when order_status='success' then create_date else null end) buy_back_time
	from order_done
	where order_type='investment'
	group by order_no
)investment_status on investment_status.order_no=investment.order_no;


INSERT INTO `resources` VALUES (null, '线下充值列表', 'recharge/rechargeOfflineApply/list', 'recharge:rechargeOfflineApply:list', 3, 1, NULL, NULL);

INSERT INTO `resources` 
select  null, '线下充值申请', 'recharge/rechargeOfflineApply/add', 'recharge:rechargeOfflineApply:add', resources.id,0,  NULL, NULL
from resources where permission='recharge:rechargeOfflineApply:list';

INSERT INTO `resources` 
select null, '线下充值审核', 'recharge/rechargeOfflineApply/audit', 'recharge:rechargeOfflineApply:audit', resources.id,0, NULL, NULL
from resources where permission='recharge:rechargeOfflineApply:list';

INSERT INTO `resources` 
SELECT NULL, '线下充值详情', 'recharge/rechargeOfflineApply/detail', 'recharge:rechargeOfflineApply:detail', resources.id,0, NULL, NULL
FROM resources WHERE permission='recharge:rechargeOfflineApply:list';






