delete from device_token where token is null and user_id is null and uuid is null;

ALTER TABLE `device_token` 
ADD COLUMN `is_uninstall` bit(1) NULL DEFAULT b'0' COMMENT '是否更换设备或重装app(数据失效) 0 否 1 是' AFTER `data_source`;

delete device_token
from device_token
inner join (
	select user_id,max(id) id
	from device_token 
	where user_id is not null
	group by user_id
	having count(1)>1
)true_dt on true_dt.user_id=device_token.user_id
where device_token.id!=true_dt.id;

ALTER TABLE `device_token` 
DROP INDEX `user_id`,
ADD UNIQUE INDEX `user_id`(`user_id`) USING BTREE;

update device_token set token=null where token='';

delete device_token
from device_token
inner join (
	select token,max(id) id
	from device_token
	where token is not null
	group by token
	having count(1)>1
)true_dt on true_dt.token=device_token.token
where device_token.id!=true_dt.id;




