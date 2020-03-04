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


create or replace view investment_view as
select investment.*,project.deadline,project.due_time,investment_status.pay_time,investment_status.buy_back_time, inviter.id as inviter_user_id
from investment
inner join user operator on operator.id=investment.user_id
left join user inviter on inviter.invite_code=operator.invite_by_code
left join (
	select 	order_no,
		max(case when order_status='pay' then create_date else null end)  pay_time,
		max(case when order_status='success' then create_date else null end) buy_back_time
	from order_done
	where order_type='investment'
	group by order_no
)investment_status on investment_status.order_no=investment.order_no
left join project_view project on investment.project_id=project.id and investment.order_status in (1,2) ;

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

INSERT INTO `resources` VALUES (null, '物权资产-导出', 'project/export', 'project:export', 15, 0, NULL, NULL);

INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '运营报表统计', 'report/operateReport', 'report:operateReport', 63, 1, NULL, NULL);
;


