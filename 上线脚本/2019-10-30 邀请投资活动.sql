ALTER TABLE `activity_detail` 
MODIFY COLUMN `trigger_type` int(10) UNSIGNED NOT NULL COMMENT '触发条件:1.注册 2.投资 3.消费 4.登录 5.邀请首投' AFTER `status`;

#去除activity.name唯一索引
ALTER TABLE `activity` 
DROP INDEX `name`,
ADD INDEX `name`(`name`) USING BTREE;

update activity set status=0 where id =107;
update activity set status=0 where id =108;
insert into activity values(111,'邀请投资',564,'邀请投资','2019-11-01','2025-11-01','https://wap.bfmuchang.com/inviteNew.html',1);
#insert into activity values(111,'邀请投资',564,'邀请投资','2019-11-01','2025-11-01','http://192.168.1.6:3082/inviteNew.html',1);
insert into activity value(112,'注册送红包',925,'注册送红包','2019-11-01','2025-11-01','https://wap.bfmuchang.com/newUserGift.html',1);
#insert into activity value(112,'注册送红包',925,'注册送红包','2019-11-01','2025-11-01','http://192.168.1.6:3082/newUserGift.html',1);

insert into activity_detail values(6,111,'邀请投资',null,null,1,5,4,1,2,1,0);
insert into activity_detail_send_hongbao values(20,6,1,1,1,50,50,0,0,null,null,365);
insert into activity_detail_send_hongbao values(21,6,3,1,1,50,50,0,0,null,null,365);
insert into activity_detail values(7,112,'注册红包',null,null,1,4,4,1,1,1,0);
insert into activity_detail_send_hongbao values(22,7,1,1,2,20,20,0,0,null,null,7);
insert into activity_detail_send_hongbao values(23,7,1,1,2,50,50,0,90,null,null,7);

#maven setting 增加 <app.root>app.bfmuchang.com</app.root>


