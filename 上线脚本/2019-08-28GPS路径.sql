create table prairie(
   id                   bigint not null,
   sequence       		int not NULL,
   name                 varchar(128),
   short_name           varchar(128),
   lobby_page           varchar(1024),
   primary key (id)
)ENGINE=InnoDB COMMENT='牧场';

create table prairie_area(
   id                   bigint not null,
   prairie_id           bigint not NULL COMMENT 'prairie.id',
   name					varchar(128),
   description			varchar(128),
   sequence       		int not NULL,
   primary key (id),
   KEY `prairie_id` (`prairie_id`) USING BTREE
)ENGINE=InnoDB COMMENT='牧场区域';

create table prairie_area_point(
   id                   bigint not null AUTO_INCREMENT,
   prairie_area_id      bigint not NULL COMMENT 'prairie_area.id',
   sequence  			int not NULL COMMENT '多边形的点的顺序',
   longitude            numeric(15,10) COMMENT '经度',
   latitude             numeric(15,10) COMMENT '纬度',
   primary key (id),
   KEY `prairie_area_id` (`prairie_area_id`) USING BTREE
)ENGINE=InnoDB COMMENT='牧场区域点(多边形的点)';

create table prairie_area_tactics(
   id                   bigint not null AUTO_INCREMENT,
   ear_number           varchar(128) COMMENT 'project.real_ear_number,针对哪头牛应用策略(优先)',
   prairie_id           bigint COMMENT 'prairie.id,针对哪个牧场应用策略',
   sequence     		int not NULL COMMENT '策略优先级',
   cron_expression      varchar(128) COMMENT 'cronExpression,策略时间范围,如:10月初到4月底的每日7点初到21点底:* * 7-21 * 10-4 ? *',
   prairie_area_id      bigint not NULL COMMENT '当前策略应用的区域id,prairie_area.id',
   primary key (id)
)ENGINE=InnoDB COMMENT='牧场区域策略';


insert into prairie 
select cast( t_value as signed) as id ,cast( t_value as signed) as sequence,tp as name,t_name as short_name,memo as lobby_page
from tm_dict
where t_key='prairie'
order by cast( t_value as signed);

ALTER TABLE t_track_device
ADD COLUMN `is_virtual` bit(1) NULL DEFAULT b'0' AFTER `nickname`;

ALTER TABLE project
ADD COLUMN `real_ear_number` varchar(50) NULL COMMENT '真实的耳标' AFTER `auto_enable_sale`;

update project set real_ear_number=ear_number;

update project set real_ear_number=SUBSTRING(real_ear_number, 3)  
where real_ear_number like '9A%';


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


create or replace view  track_device_view as
select 
	device.*, 
	project.id project_id,
	project.title,
	project.ear_number,
	project.real_ear_number,
	project.prairie_value,
	project.sex,
	project.weight,
	prairie.short_name as prairie_name,
	upload.path,
	property.pin_zhong,
	property.chan_di_lai_yuan,
	property.yue_ling,
	property.jian_kang_zhuang_kuang
from (
	select project.* 
	from project
	inner join (
		select max(id) id,project.real_ear_number 
		from project 
		where true
		group by project.real_ear_number 
	)ear_number_p on ear_number_p.id=project.id
	where true
)project
left join t_track_device device on device.nickname=project.real_ear_number
left join project_property_view property on property.id=project.id
#project on device.sn=project.gps_number
left join prairie  on  project.prairie_value = prairie.id
left join (
	select project_id,min(id) id
	from project_picture
	where type=1
	group by project_id
)project_picture_id on project_picture_id.project_id=project.id
left join project_picture on project_picture.id=project_picture_id.id
left join upload on upload.id=project_picture.upload_id;


/*围栏数据*/
/*
围栏东侧 （基地）
          coordtransform.wgs84togcj02(117.063066,45.421076),
          coordtransform.wgs84togcj02(117.046821,45.408298),
          coordtransform.wgs84togcj02(117.024055,45.390453),
          coordtransform.wgs84togcj02(117.046263,45.375618),
          coordtransform.wgs84togcj02(117.083748,45.407198)
*/

insert into prairie_area values(1,1,'东侧','基地东侧',1);

insert into prairie_area_point values(null,1,1,117.063066,45.421076);
insert into prairie_area_point values(null,1,2,117.046821,45.408298);
insert into prairie_area_point values(null,1,3,117.024055,45.390453);
insert into prairie_area_point values(null,1,4,117.046263,45.375618);
insert into prairie_area_point values(null,1,5,117.083748,45.407198);
/*
基地西侧
        coordtransform.wgs84togcj02(117.022209,45.388284),
        coordtransform.wgs84togcj02(117.014013,45.380753),
        coordtransform.wgs84togcj02(117.009646,45.375705),
        coordtransform.wgs84togcj02(117.008199,45.370295),
        coordtransform.wgs84togcj02(117.003414,45.361162),
        coordtransform.wgs84togcj02(117.007083,45.331025),
        coordtransform.wgs84togcj02(117.033411,45.335756),
        coordtransform.wgs84togcj02(117.032315,45.361538),
        coordtransform.wgs84togcj02(117.039406,45.368636),
        coordtransform.wgs84togcj02(117.044952,45.374408),
*/
insert into prairie_area values(2,1,'西侧','基地西侧',2);

insert into prairie_area_point values(null,2,1,117.022209,45.388284);
insert into prairie_area_point values(null,2,2,117.014013,45.380753);
insert into prairie_area_point values(null,2,3,117.009646,45.375705);
insert into prairie_area_point values(null,2,4,117.008199,45.370295);
insert into prairie_area_point values(null,2,5,117.003414,45.361162);

insert into prairie_area_point values(null,2,6,117.007083,45.331025);
insert into prairie_area_point values(null,2,7,117.033411,45.335756);
insert into prairie_area_point values(null,2,8,117.032315,45.361538);
insert into prairie_area_point values(null,2,9,117.039406,45.368636);
insert into prairie_area_point values(null,2,10,117.044952,45.374408);

#1号牧场["118.507578,45.77205", "118.516247,45.77636", "118.526161,45.782346", "118.533842,45.769415", "118.533714,45.769116", "118.513501,45.762859", "118.512642,45.762769", "118.507063,45.771481"]

insert into prairie_area values(3,2,'牧场','1号牧场',1);
insert into prairie_area_point values(null,3,1,118.507578,45.77205);
insert into prairie_area_point values(null,3,2,118.516247,45.77636);
insert into prairie_area_point values(null,3,3,118.526161,45.782346);
insert into prairie_area_point values(null,3,4,118.533842,45.769415);
insert into prairie_area_point values(null,3,5,118.533714,45.769116);

insert into prairie_area_point values(null,3,6,118.513501,45.762859);
insert into prairie_area_point values(null,3,7,118.512642,45.762769);
insert into prairie_area_point values(null,3,8,118.507063,45.771481);

#2号牧场 东侧	["117.394731,46.29403", "117.398765,46.298774", "117.404344,46.302391", "117.423141,46.291539", "117.414729,46.283294", "117.394473,46.293081"]
insert into prairie_area values(4,3,'东侧','2号牧场 东侧',1);
insert into prairie_area_point values(null,4,1,117.394731,46.29403);
insert into prairie_area_point values(null,4,2,117.398765,46.298774);
insert into prairie_area_point values(null,4,3,117.404344,46.302391);
insert into prairie_area_point values(null,4,4,117.423141,46.291539);
insert into prairie_area_point values(null,4,5,117.414729,46.283294);

insert into prairie_area_point values(null,4,6,117.394473,46.293081);


#2号牧场 西侧 	["117.340572,46.348386", "117.352245,46.347912", "117.375762,46.324089", "117.374904,46.320888", "117.376277,46.306897", "117.355678,46.318636", "117.339885,46.346964"]
insert into prairie_area values(5,3,'西侧','2号牧场 西侧',2);
insert into prairie_area_point values(null,5,1,117.340572,46.348386);
insert into prairie_area_point values(null,5,2,117.352245,46.347912);
insert into prairie_area_point values(null,5,3,117.375762,46.324089);
insert into prairie_area_point values(null,5,4,117.374904,46.320888);
insert into prairie_area_point values(null,5,5,117.376277,46.306897);

insert into prairie_area_point values(null,5,6,117.355678,46.318636);
insert into prairie_area_point values(null,5,7,117.339885,46.346964);

#3号牧场 东侧	["117.369891,45.191543", "117.375813,45.193478", "117.392979,45.182227", "117.393323,45.181017", "117.410489,45.172123", "117.395898,45.165164", "117.37341,45.178657", "117.374869,45.18017", "117.369805,45.191301"]
insert into prairie_area values(6,4,'东侧','3号牧场 东侧',1);
insert into prairie_area_point values(null,6,1,117.369891,45.191543);
insert into prairie_area_point values(null,6,2,117.375813,45.193478);
insert into prairie_area_point values(null,6,3,117.392979,45.182227);
insert into prairie_area_point values(null,6,4,117.393323,45.181017);
insert into prairie_area_point values(null,6,5,117.410489,45.172123);

insert into prairie_area_point values(null,6,6,117.395898,45.165164);
insert into prairie_area_point values(null,6,7,117.37341,45.178657);
insert into prairie_area_point values(null,6,8,117.374869,45.18017);
insert into prairie_area_point values(null,6,9,117.369805,45.191301);

#3号牧场西侧	 ["117.376729,45.205599", "117.384111,45.194652", "117.376644,45.1932", "117.376043,45.204813"]
insert into prairie_area values(7,4,'西侧','3号牧场 西侧',2);
insert into prairie_area_point values(null,7,1,117.376729,45.205599);
insert into prairie_area_point values(null,7,2,117.384111,45.194652);
insert into prairie_area_point values(null,7,3,117.376644,45.1932);
insert into prairie_area_point values(null,7,4,117.376043,45.204813);

#4号牧场 	["117.668489,45.972976", "117.701962,45.986694", "117.720845,45.971902", "117.716554,45.96343", "117.710031,45.96534", "117.683595,45.946364", "117.673467,45.953764", "117.678273,45.961402", "117.666772,45.972498"]
insert into prairie_area values(8,5,'牧场','4号牧场',1);
insert into prairie_area_point values(null,8,1,117.668489,45.972976);
insert into prairie_area_point values(null,8,2,117.701962,45.986694);
insert into prairie_area_point values(null,8,3,117.720845,45.971902);
insert into prairie_area_point values(null,8,4,117.716554,45.96343);
insert into prairie_area_point values(null,8,5,117.710031,45.96534);

insert into prairie_area_point values(null,8,6,117.683595,45.946364);
insert into prairie_area_point values(null,8,7,117.673467,45.953764);
insert into prairie_area_point values(null,8,8,117.678273,45.961402);
insert into prairie_area_point values(null,8,9,117.666772,45.972498);

#5号牧场	["118.489261,45.883636", "118.495612,45.882083", "118.500419,45.874554", "118.501449,45.848254", "118.491492,45.846461", "118.488574,45.850406", "118.492694,45.853635", "118.483253,45.862362", "118.477931,45.872761", "118.487887,45.883397"]
insert into prairie_area values(9,6,'牧场','5号牧场',1);
insert into prairie_area_point values(null,9,1,118.489261,45.883636);
insert into prairie_area_point values(null,9,2,118.495612,45.882083);
insert into prairie_area_point values(null,9,3,118.500419,45.874554);
insert into prairie_area_point values(null,9,4,118.501449,45.848254);
insert into prairie_area_point values(null,9,5,118.491492,45.846461);

insert into prairie_area_point values(null,9,6,118.488574,45.850406);
insert into prairie_area_point values(null,9,7,118.492694,45.853635);
insert into prairie_area_point values(null,9,8,118.483253,45.862362);
insert into prairie_area_point values(null,9,9,118.477931,45.872761);
insert into prairie_area_point values(null,9,10,118.487887,45.883397);

#六号牧场	["118.488792,45.968053", "118.506302,45.969604", "118.507675,45.966502", "118.515228,45.968889", "118.518146,45.958269", "118.49686,45.931292", "118.488106,45.966383"]
insert into prairie_area values(10,7,'牧场','6号牧场',1);
insert into prairie_area_point values(null,10,1,118.488792,45.968053);
insert into prairie_area_point values(null,10,2,118.506302,45.969604);
insert into prairie_area_point values(null,10,3,118.507675,45.966502);
insert into prairie_area_point values(null,10,4,118.515228,45.968889);
insert into prairie_area_point values(null,10,5,118.518146,45.958269);

insert into prairie_area_point values(null,10,6,118.49686,45.931292);
insert into prairie_area_point values(null,10,7,118.488106,45.966383);

#七号牧场	["118.302256,45.76776", "118.317877,45.780691", "118.328091,45.761772", "118.309981,45.752251", "118.301827,45.767161"]
insert into prairie_area values(11,8,'牧场','7号牧场',1);
insert into prairie_area_point values(null,11,1,118.302256,45.76776);
insert into prairie_area_point values(null,11,2,118.317877,45.780691);
insert into prairie_area_point values(null,11,3,118.328091,45.761772);
insert into prairie_area_point values(null,11,4,118.309981,45.752251);
insert into prairie_area_point values(null,11,5,118.301827,45.767161);

#八号牧场	["118.40224,45.839101", "118.387992,45.859069", "118.394172,45.859906", "118.400953,45.861938", "118.405845,45.854048", "118.424127,45.86409", "118.4249,45.859428", "118.40327,45.838503"]
insert into prairie_area values(12,9,'牧场','8号牧场',1);
insert into prairie_area_point values(null,12,1,118.40224,45.839101);
insert into prairie_area_point values(null,12,2,118.387992,45.859069);
insert into prairie_area_point values(null,12,3,118.394172,45.859906);
insert into prairie_area_point values(null,12,4,118.400953,45.861938);
insert into prairie_area_point values(null,12,5,118.405845,45.854048);

insert into prairie_area_point values(null,12,6,118.424127,45.86409);
insert into prairie_area_point values(null,12,7,118.4249,45.859428);
insert into prairie_area_point values(null,12,8,118.40327,45.838503);

#九号牧场 ["119.282814,46.5635", "119.317747,46.558896", "119.319206,46.556004", "119.317318,46.555001", "119.314657,46.553466", "119.314915,46.551932", "119.306589,46.54904", "119.303757,46.549099",

#"119.302469,46.549217", "119.301268,46.548862", "119.296118,46.545262", "119.290453,46.543963", "119.287535,46.545793", "119.286934,46.547092", "119.28393,46.548685", "119.281784,46.547977", "119.281355,46.549689", 

#"119.279381,46.550633", "119.282642,46.563323"]
insert into prairie_area values(13,10,'牧场','9号牧场',1);
insert into prairie_area_point values(null,13,1,119.282814,46.5635);
insert into prairie_area_point values(null,13,2,119.317747,46.558896);
insert into prairie_area_point values(null,13,3,119.319206,46.556004);
insert into prairie_area_point values(null,13,4,119.317318,46.555001);
insert into prairie_area_point values(null,13,5,119.314657,46.553466);

insert into prairie_area_point values(null,13,6,119.314915,46.551932);
insert into prairie_area_point values(null,13,7,119.306589,46.54904);
insert into prairie_area_point values(null,13,8,119.303757,46.549099);

insert into prairie_area_point values(null,13,9,119.302469,46.549217);
insert into prairie_area_point values(null,13,10,119.301268,46.548862);
insert into prairie_area_point values(null,13,11,119.296118,46.545262);
insert into prairie_area_point values(null,13,12,119.290453,46.543963);
insert into prairie_area_point values(null,13,13,119.287535,46.545793);

insert into prairie_area_point values(null,13,14,119.286934,46.547092);
insert into prairie_area_point values(null,13,15,119.28393,46.548685);
insert into prairie_area_point values(null,13,16,119.281784,46.547977);
insert into prairie_area_point values(null,13,17,119.281355,46.549689);

insert into prairie_area_point values(null,13,18,119.279381,46.550633);
insert into prairie_area_point values(null,13,19,119.282642,46.563323);

