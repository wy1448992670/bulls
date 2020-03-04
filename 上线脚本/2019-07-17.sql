create or replace view  track_device_view as
select 
	device.*, 
	project.id project_id,
	project.title,
	project.ear_number,
	project.prairie_value,
	dict.t_name as prairie_name,
	upload.path
from t_track_device device
inner join (
	select project.* 
	from project
	inner join (
		select max(id) id,project.ear_number 
		from project 
		where true
		group by project.ear_number 
	)ear_number_p on ear_number_p.id=project.id
	where true
)project on CONCAT('9A',device.nickname)=project.ear_number
left join tm_dict dict on dict.t_key='prairie' and project.prairie_value = dict.t_value
left join (
	select project_id,min(id) id
	from project_picture
	where type=1
	group by project_id
)project_picture_id on project_picture_id.project_id=project.id
left join project_picture on project_picture.id=project_picture_id.id
left join upload on upload.id=project_picture.upload_id;

ALTER TABLE `t_track_device` ADD COLUMN `sn`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设备编号' AFTER `sync_at`;
ALTER TABLE `t_track_device` ADD COLUMN `nickname`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '耳标号' AFTER `sn`;

ALTER TABLE `project`
ADD COLUMN `prairie_value` varchar(100) NULL COMMENT '牧场 value    tm_dict 表中' AFTER `gps_number`,
ADD COLUMN `expectant` int(11) NULL COMMENT '是否待产  0 是 1  否' AFTER `prairie_value`;


#注意生产表中id最大值
INSERT INTO `tm_dict`(`id`, `t_name`, `t_key`, `t_value`, `memo`, `t_sort`, `tp`) VALUES (43, '一号牧场', 'prairie', '1', NULL, NULL, NULL);
INSERT INTO `tm_dict`(`id`, `t_name`, `t_key`, `t_value`, `memo`, `t_sort`, `tp`) VALUES (44, '二号牧场', 'prairie', '2', NULL, NULL, NULL);
INSERT INTO `tm_dict`(`id`, `t_name`, `t_key`, `t_value`, `memo`, `t_sort`, `tp`) VALUES (45, '三号牧场', 'prairie', '3', NULL, NULL, NULL);