drop view good_order_view;

create or replace view goods_order_view as 
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



