
ALTER TABLE `project` 
MODIFY COLUMN `project_type` INT NULL DEFAULT NULL COMMENT 'ProjectTypeEnum 项目类型:0.养牛,1.拼牛',
MODIFY COLUMN `deadline` datetime(0) NULL DEFAULT NULL COMMENT '起息时间' ;

ALTER TABLE `project` 
ADD COLUMN `pay_time` datetime(0) NULL COMMENT '成交时间' AFTER `deadline`,
ADD COLUMN `buy_back_time` datetime(0) NULL COMMENT '回购时间' AFTER `pay_time`;

update project
left join investment on investment.order_status in (1,2) and investment.project_id=project.id
left join (
	select 	order_no,
		max(case when order_status='pay' then create_date else null end)  pay_time,
		max(case when order_status='success' then create_date else null end) buy_back_time
	from order_done
	where order_type='investment'
	group by order_no
)investment_status on investment_status.order_no=investment.order_no
set project.pay_time=investment_status.pay_time,project.buy_back_time=investment_status.buy_back_time;

ALTER TABLE `investment` 
MODIFY COLUMN `order_status` int NULL DEFAULT 0 COMMENT '0：未饲养，1：饲养期，2：已卖牛 3 已取消, 4 待成交' AFTER `pay_status`,
MODIFY COLUMN `type` int NULL DEFAULT 0 COMMENT 'ProjectTypeEnum 订单类型:0.养牛,1.拼牛' AFTER `order_no`;

update investment set `type`=0;

INSERT INTO `tm_dict` VALUES (NULL, '显示几条拼牛', 'PINGNIU_PROJECT_SHOW_NUM', '2', '0,不显示', NULL, NULL);

create or replace view project_view as
select 	project.*,
	project.deadline + INTERVAL project.limit_days DAY  AS due_time,
	cast(project_property_view.yue_ling as UNSIGNED) as yue_ling,
	10 as pin_total_point
from project
left join project_property_view on project_property_view.id=project.id;

INSERT INTO `tm_dict` VALUES (NULL, 'app v2.0 养牛是否显示IOS', 'V2_0:BULLS_SHOW_IOS', 'yes', 'yes：是，no：否', NULL, NULL);
INSERT INTO `tm_dict` VALUES (NULL, 'app v2.0 养牛是否显示android', 'V2_0:BULLS_SHOW_ANDROID', 'yes', 'yes：是，no：否', NULL, NULL);
INSERT INTO `tm_dict` VALUES (NULL, 'app v2.0 养牛是否显示WAP', 'V2_0:BULLS_SHOW_WAP', 'yes', 'yes：是，no：否', NULL, NULL);
INSERT INTO `tm_dict` VALUES (NULL, 'app v2.0 首页是否显示商城精选推荐', 'V2_0:SHOP_SHOW_RECOMMEND', 'yes', 'yes：是，no：否', NULL, NULL);

ALTER TABLE `user`
MODIFY COLUMN `username`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户名' AFTER `id`;
