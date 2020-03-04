ALTER TABLE `user` 
ADD COLUMN `department_id` int(11) NULL DEFAULT 0 COMMENT 'department.id' AFTER `migration_time`;
ALTER TABLE `user` 
MODIFY COLUMN `channel_id` varchar(50) NULL DEFAULT NULL COMMENT 'advertisement_channel.id' AFTER `data_source`;
ALTER TABLE `user_admin` 
ADD COLUMN `department_id` int(11) NULL DEFAULT 0 COMMENT 'department.id' AFTER `parent_id`;

update user set channel_id=null where channel_id='WAP';

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `id` int(11) NOT NULL COMMENT '部门id',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父部门id,null时为根部门',
  `name` varchar(255) NOT NULL COMMENT '部门名',
  `depth_level` int(11) NULL DEFAULT NULL COMMENT '部门层级',
  `is_show` bit(1) NOT NULL DEFAULT b'1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Compact COMMENT='部门表';

INSERT INTO `department` VALUES (0, NULL, '公司', NULL,true);
INSERT INTO `department` VALUES (1, 0, '新客', NULL,true);
INSERT INTO `department` VALUES (2, 0, '亿亿', NULL,true);
INSERT INTO `department` VALUES (3, 0, '分公司', NULL,true);
INSERT INTO `department` VALUES (4, 3, '宁德', NULL,false);
INSERT INTO `department` VALUES (5, 3, '温州', NULL,false);
INSERT INTO `department` VALUES (6, 3, '枣庄', NULL,false);

create table user_data_source (
  `id` int(11) NOT NULL COMMENT '数据来源id',
  `name` varchar(255) NOT NULL COMMENT '数据来源名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  ROW_FORMAT = Compact COMMENT='用户数据来源';

insert into user_data_source values(0,'平台');
insert into user_data_source values(1,'亿亿理财');
insert into user_data_source values(2,'阿里分发市场');
insert into user_data_source values(3,'应用宝');
insert into user_data_source values(4,'华为应用市场');
insert into user_data_source values(5,'vivo应用商店');
insert into user_data_source values(6,'oppo');
insert into user_data_source values(7,'魅族');
insert into user_data_source values(8,'联通wo');
insert into user_data_source values(9,'联想');
insert into user_data_source values(10,'百度');

INSERT INTO resources VALUES(NULL,'用户来源统计','user/dataSourceSum','user:dataSourceSum',2,1,NULL,NULL);

 drop TABLE IF EXISTS department_relation;
 CREATE TABLE department_relation (
  id int auto_increment COMMENT '部门关系id',
  department_id int COMMENT 'department.id',
  sub_department_id int COMMENT 'department的子部门id(后裔,子孙,也包含他自己)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `department_id`(`department_id`) USING BTREE,
  INDEX `sub_department_id`(`sub_department_id`) USING BTREE
 )ENGINE = InnoDB  ROW_FORMAT = Compact COMMENT='部门关系表';


drop PROCEDURE IF EXISTS  refreshDepartmentRelation;
CREATE PROCEDURE refreshDepartmentRelation()
BEGIN
  DECLARE `nowDepthLevel` int;

  Set nowDepthLevel=0 ;
  delete from department_relation;
  update department set depth_level=null;

  update department set depth_level=`nowDepthLevel`
  where parent_id is null;

  INSERT into department_relation
  SELECT null,department.id,department.id 
  FROM department WHERE depth_level=`nowDepthLevel`;
  
 
  WHILE ROW_COUNT()>0 DO

  SET nowDepthLevel=nowDepthLevel+1;

  update department son_depa
  inner join department pare_depa on son_depa.parent_id=pare_depa.id
  set son_depa.depth_level=`nowDepthLevel`
  where pare_depa.depth_level=nowDepthLevel-1;
 
  
  INSERT into department_relation
  SELECT null,pdr.department_id,d.id 
  FROM department_relation pdr
  inner join department d
    on d.parent_id=pdr.sub_department_id
  WHERE d.depth_level=nowDepthLevel;
  

  INSERT into department_relation
  SELECT null,department.id,department.id
  FROM department
  WHERE department.depth_level=nowDepthLevel;
  END WHILE;
END;


CALL refreshDepartmentRelation();

#select * from department_relation;

#区分亿亿和新客department_id
update user set department_id=(case when data_source='1' then 2 else 1 end);
#分公司员工部门初始化department_id
update user 
inner join employ on user.phone=employ.mobile
inner join department on department.name=employ.company
set user.department_id=department.id;

#更新分公司员工直属用户(employ_user)department_id
update user 
inner join employ on user.phone=employ.mobile
inner join employ_user on employ_user.emoloy_id=employ.id
inner join user invitee on invitee.id=employ_user.user_id
set invitee.department_id=user.department_id;

#更被推荐人的department_id到推荐人的department_id
update user inviter
inner join user invitee on inviter.invite_code=invitee.invite_by_code
set invitee.department_id=inviter.department_id;
update user inviter
inner join user invitee on inviter.invite_code=invitee.invite_by_code
set invitee.department_id=inviter.department_id;
update user inviter
inner join user invitee on inviter.invite_code=invitee.invite_by_code
set invitee.department_id=inviter.department_id;
update user inviter
inner join user invitee on inviter.invite_code=invitee.invite_by_code
set invitee.department_id=inviter.department_id;
###---------------------------------------------管理员部门关系视图
create or replace view admin_responsible as 
select user_admin.id admin_id
	,department_relation.sub_department_id as department_id
from user_admin 
inner join department_relation 
	on user_admin.department_id=department_relation.department_id;

###----------------------------------------------重置业务视图
create or replace view goods_order_view as 
select t_goods_order.*,goods_order_status.pay_time,goods_order_status.refund_finish_time
		,operator.department_id,inviter.id as inviter_user_id
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


create or replace view investment_view as
select investment.*,project.deadline,project.due_time
	,investment_status.pay_time,investment_status.buy_back_time
	,operator.department_id,inviter.id as inviter_user_id
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

