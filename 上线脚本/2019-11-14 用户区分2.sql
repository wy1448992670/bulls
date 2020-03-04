update user set department_id=1 where department_id=0;
insert into user_data_source values(-1,'其他');
insert into user_data_source values(11,'小米');
insert into user_data_source values(12,'360手机助手');
update user set data_source=-1 where data_source='其他';

insert into department values(7,0,'亿亿-分公司',1,0);
#select * from department_relation;
insert into department_relation values(null,7,2);
insert into department_relation values(null,7,3);
insert into department_relation values(null,7,4);
insert into department_relation values(null,7,5);
insert into department_relation values(null,7,6);
insert into department_relation values(null,7,7);