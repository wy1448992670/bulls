ALTER TABLE `bulls`.`project` 
CHANGE COLUMN `pay_time` `trade_time` datetime(0) NULL DEFAULT NULL COMMENT '成交时间' AFTER `deadline`;

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
set project.trade_time=investment_status.pay_time,project.buy_back_time=investment_status.buy_back_time;

create or replace view project_view as
select 	project.*,
	project.deadline + INTERVAL project.limit_days DAY  AS due_time,
	cast(project_property_view.yue_ling as UNSIGNED) as yue_ling,
	10 as pin_total_point
from project
left join project_property_view on project_property_view.id=project.id;

# 商品category_id设置为空之后数据维护,影响管理后台添加商品
INSERT INTO t_goods_property (category_id, property_name,create_date,update_date) 
select t.id as category_id ,'规格' as property_name ,NOW(), NOW() from t_goods_category t 
	where t.id <> 0 and t.id not in(select category_id from t_goods_property)
