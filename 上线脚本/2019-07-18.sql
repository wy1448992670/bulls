
create or replace view  track_device_view as
select 
	device.*, 
	project.id project_id,
	project.title,
	project.ear_number,
	project.prairie_value,
	project.sex,
	project.weight,
	dict.t_name as prairie_name,
	upload.path,
	property.pin_zhong,
	property.chan_di_lai_yuan,
	property.yue_ling,
	property.jian_kang_zhuang_kuang
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
left join project_property_view property on property.id=project.id
#project on device.sn=project.gps_number
left join tm_dict dict on dict.t_key='prairie' and project.prairie_value = dict.t_value
left join (
	select project_id,min(id) id
	from project_picture
	where type=1
	group by project_id
)project_picture_id on project_picture_id.project_id=project.id
left join project_picture on project_picture.id=project_picture_id.id
left join upload on upload.id=project_picture.upload_id


create or replace view  project_property_view as
select 
	project.id,
	GROUP_CONCAT(case when product_property.property_name='品种' then project_property_value.property_value  else '' end separator '') as pin_zhong, 
	GROUP_CONCAT(case when product_property.property_name='产地来源' then project_property_value.property_value  else '' end separator '') as chan_di_lai_yuan,
	GROUP_CONCAT(case when product_property.property_name='月龄' then project_property_value.property_value  else '' end separator '') as yue_ling,
	GROUP_CONCAT(case when product_property.property_name='健康状况' then project_property_value.property_value  else '' end  separator '') as jian_kang_zhuang_kuang
from project   
inner join  product_property    on   project.product_id=product_property.product_id
inner join  project_property_value  on  product_property.id= project_property_value.product_property_id  and project_property_value.project_id=project.id
group by  project.id
