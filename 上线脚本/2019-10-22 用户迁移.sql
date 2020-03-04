ALTER TABLE `bulls`.`user` 
ADD COLUMN `is_migration` bit(1) NULL DEFAULT 0 COMMENT '是否是迁移用户' AFTER `give_scale`,
ADD COLUMN `migration_time` datetime(0) NULL DEFAULT NULL COMMENT '迁移时间' AFTER `is_migration`;

CREATE TABLE `migration_investment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '投资ID',
  `user_id` bigint(20) DEFAULT '0' COMMENT '用户id(投资人)',
  `bid_id` bigint(20) DEFAULT '0' COMMENT '借款标id',
  `bid_title` varchar(200) DEFAULT NULL COMMENT 't_bids.title',#new
  `repayment_type_id` bigint(20) DEFAULT '0' COMMENT 't_bids.repayment_type_id',#new
  `period_unit` int(11) DEFAULT '0' COMMENT '借款期限-1: 年;0:月;1:日;',#new
  `period` int(11) DEFAULT '0' COMMENT '借款期限',#new
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '投资时间',
  `loan_time` datetime DEFAULT NULL COMMENT '放款时间',#new
  `apr` decimal(10,4) DEFAULT '0.0000' COMMENT '年利率',#new
  `is_increase_rate` bit(1) DEFAULT b'0' COMMENT '是否加息',#new
  `increase_rate` double(6,1) DEFAULT NULL COMMENT '加息利率',#new
  `amount` decimal(20,2) DEFAULT '0.00' COMMENT '投资金额',
  `red_amount` decimal(20,2) DEFAULT '0.00' COMMENT '使用红包金额',
  `interest` decimal(20,2) DEFAULT '0.00' COMMENT '投资利息',
  `increase_interest` decimal(20,2) DEFAULT '0.00' COMMENT '加息利息',
  `is_finished` bit(1) DEFAULT b'0' COMMENT '是否结清',
  `finished_time` datetime DEFAULT NULL COMMENT '结清时间',
  `version` int(10) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `index_inv_id` (`id`) USING BTREE,
  KEY `index_user_id` (`user_id`) USING BTREE,
  KEY `index_bid_id` (`bid_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1029978 DEFAULT CHARSET=utf8 COMMENT='迁移投资表' ROW_FORMAT=COMPACT;

CREATE TABLE `migration_investment_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账单id',
  `user_id` bigint(20) DEFAULT '0' COMMENT '用户id',
  `migration_investment_id` bigint(20) DEFAULT '0' COMMENT '投资记录id',
  `bid_id` bigint(20) DEFAULT '0' COMMENT '标id',
  `periods` tinyint(4) DEFAULT '0' COMMENT '期数',
  `receive_time` datetime DEFAULT NULL COMMENT '应收时间',
  `receive_corpus` decimal(20,2) DEFAULT '0.00' COMMENT '本期应收款金额',
  `receive_interest` decimal(20,2) DEFAULT '0.00' COMMENT '本期应收利息',
  `receive_increase_interest` decimal(20,2) DEFAULT '0.00' COMMENT '应收加息利息',
  `is_receive_before` bit(1) DEFAULT b'0' NOT NULL COMMENT '转移前已回款',
  `is_receive` bit(1) DEFAULT b'0' NOT NULL COMMENT '是否已回款',
  `real_receive_time` datetime DEFAULT NULL COMMENT '实际收款时间',
  `version` int(10) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `index_bill_inv_id` (`id`) USING BTREE,
  KEY `index_bill_inv_usr_id` (`user_id`) USING BTREE,
  KEY `index_bill_inv_bid_id` (`bid_id`) USING BTREE,
  KEY `index_receive_time` (`receive_time`) USING BTREE,
  KEY `index_invest_id` (`migration_investment_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10177778 DEFAULT CHARSET=utf8 COMMENT='迁移理财账单表';

create view migration_investment_view  as 
select
 mi.*,
 bill_sum.received_corpus,
 bill_sum.received_interest,
 bill_sum.not_received_corpus,
 bill_sum.not_received_interest,
 bill_sum.not_received_increase_interest,
 bill_sum.next_receive_time,
 bill_sum.last_receive_time,
 bill_sum.remain_periods,
 bill_sum.received_interest_before,
 bill_sum.receive_increase_interest_before,
 bill_sum.received_interest_after,
 bill_sum.receive_increase_interest_after
from migration_investment mi
left join (
 select
  mib.migration_investment_id,
  sum(case when mib.is_receive then mib.receive_corpus else 0 end ) as received_corpus,
  sum(case when mib.is_receive then mib.receive_interest else 0 end ) as received_interest,
  sum(case when !mib.is_receive then mib.receive_corpus else 0 end ) as not_received_corpus,
  sum(case when !mib.is_receive then mib.receive_interest else 0 end ) as not_received_interest,
	sum(case when !mib.is_receive then mib.receive_increase_interest else 0 end ) as not_received_increase_interest,
  min(case when !mib.is_receive then mib.receive_time else null end ) as next_receive_time,
	max(mib.receive_time) as last_receive_time,
	sum(!mib.is_receive) as remain_periods,
	sum(case when mib.is_receive_before then mib.receive_interest else 0 end ) as received_interest_before,
	sum(case when mib.is_receive_before then mib.receive_increase_interest else 0 end ) as receive_increase_interest_before,
	sum(case when !mib.is_receive_before and mib.is_receive then mib.receive_interest else 0 end ) as received_interest_after,
	sum(case when !mib.is_receive_before and mib.is_receive then mib.receive_increase_interest else 0 end ) as receive_increase_interest_after
 from migration_investment_bill mib
 group by mib.migration_investment_id
) bill_sum on bill_sum.migration_investment_id = mi.id;



INSERT INTO `bulls`.`account_operate`(`feature_type`, `feature_name`, `description`, `app_description`, `account_type_id`, `account_operate_type_id`, `business_code`) VALUES (40211, 'migration_balance_add', '迁移用户,余额迁移', '迁移用户,余额迁移', 1, 1, 'migration');
INSERT INTO `bulls`.`account_operate`(`feature_type`, `feature_name`, `description`, `app_description`, `account_type_id`, `account_operate_type_id`, `business_code`) VALUES (40311, 'migration_interest_balance_add', '迁移用户,回款利息', '迁移用户,回款利息', 1, 1, 'migration');
INSERT INTO `bulls`.`account_operate`(`feature_type`, `feature_name`, `description`, `app_description`, `account_type_id`, `account_operate_type_id`, `business_code`) VALUES (40411, 'migration_principal_balance_add', '迁移用户,回款本金', '迁移用户,回款本金', 1, 1, 'migration');


INSERT INTO resources VALUES(NULL,'迁移用户统计','report/mirgationUser/list','report:mirgationUser:list',63,1,NULL,NULL);
INSERT INTO resources VALUES(NULL,'迁移用户导入','user/migrationImport','user:migrationImport',2,0,NULL,NULL);

