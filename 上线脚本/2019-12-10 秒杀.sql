ALTER TABLE `t_goods` ADD COLUMN `is_recommend`  int(255) NULL DEFAULT 0 COMMENT '是否推荐 0 不推荐 1推荐' AFTER `up_down`;
ALTER TABLE `t_goods` ADD COLUMN `actvity_stock`  int(11) NULL DEFAULT 0 COMMENT '活动库存（划拨）' AFTER `is_recommend`;

ALTER TABLE `t_goods_order_detail` ADD COLUMN `activity_type`  int(11) NULL DEFAULT NULL COMMENT '活动类型 1秒杀' AFTER `goods_id`;
ALTER TABLE `t_goods_order_detail` ADD COLUMN `activity_detail_id`  int(11) NULL DEFAULT NULL COMMENT '活动详情编号' AFTER `activity_type`;

ALTER TABLE `t_goods_picture` MODIFY COLUMN `type`  int(11) NULL DEFAULT NULL COMMENT '12 商品大图  14 商品小图 16 活动广告图片' AFTER `goods_id`;

CREATE TABLE `t_mall_activity` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`name`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '活动名称' ,
`start_date`  date NOT NULL COMMENT '开始日期' ,
`stop_date`  date NOT NULL COMMENT '结束日期' ,
`type`  int(11) NULL DEFAULT 1 COMMENT '活动类型：1秒杀' ,
`enable`  int(11) NULL DEFAULT 1 COMMENT '是否可用 1：可用 0：不可用' ,
`create_time`  datetime NULL DEFAULT CURRENT_TIMESTAMP ,
`admin_id`  int(11) NOT NULL ,
`remark`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Dynamic
;

CREATE TABLE `t_mall_activity_second_kill` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`activity_id`  int(11) NOT NULL ,
`good_id`  int(11) NOT NULL ,
`start_time`  time NOT NULL COMMENT '开始时间' ,
`kill_time`  int(11) NOT NULL COMMENT '秒杀时长（分钟）' ,
`week_day`  int(11) NOT NULL COMMENT '星期 周一1   周日7' ,
`price`  decimal(10,0) NOT NULL ,
`member_price`  decimal(10,0) NOT NULL COMMENT '会员价格' ,
`stock_count`  int(11) NOT NULL COMMENT '库存' ,
`saled_count`  int(11) NULL DEFAULT NULL COMMENT '已售出' ,
`limit_count`  int(11) NOT NULL COMMENT '每人限购' ,
`title`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '活动描述' ,
`create_time`  datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
`enable`  int(11) NULL DEFAULT 1 COMMENT '是否可用 1可用 0不可用' ,
`is_on_shelves`  int(11) NULL DEFAULT 1 COMMENT '是否可卖  1上架  0下架' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
ROW_FORMAT=Dynamic
;

-- CREATE TABLE `t_380` (
--   `id` int(11) NOT NULL AUTO_INCREMENT,
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB
-- DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
-- ;
-- -- 插入380条数据
-- CREATE  PROCEDURE `pro_i380`()
-- BEGIN
--  DECLARE a INT default 1;
--  while a<=380 do
--  insert into t_380 (id) value(a);
--  set a=a+1;
--  end while;
--  end
--
-- CALL pro_i380();






CREATE OR REPLACE VIEW  second_kill_activity_view as
SELECT * from
(
	SELECT * from
	(
	SELECT a.`name`,b.id, b.good_id,b.week_day,
		b.title,b.limit_count,b.stock_count,
		subdate(curdate(),date_format(curdate(),'%w') - b.week_day+7) date, b.start_time,b.kill_time,
		str_to_date(CONCAT(subdate(curdate(),date_format(curdate(),'%w') - b.week_day+7),b.start_time),'%Y-%m-%d%H:%i:%s') begin_time,
		date_add(str_to_date(CONCAT(subdate(curdate(),date_format(curdate(),'%w') - b.week_day+7),b.start_time),'%Y-%m-%d%H:%i:%s'),interval b.kill_time minute) end_time,
		b.price,b.member_price from t_mall_activity a
		INNER JOIN t_mall_activity_second_kill b on a.id = b.activity_id
		where now() >= a.start_date  and now() < date_add(a.stop_date, interval 1 day)
		and a.`enable`=1 and b.`enable` = 1
		and subdate(curdate(),date_format(curdate(),'%w') - b.week_day+7) >= a.start_date
		and subdate(curdate(),date_format(curdate(),'%w') - b.week_day+7) < date_add(a.stop_date, interval 1 day)
	UNION all

	SELECT a.`name`,b.id, b.good_id,b.week_day,
		b.title,b.limit_count,b.stock_count,
		subdate(curdate(),date_format(curdate(),'%w') - b.week_day) date, b.start_time,b.kill_time,
		str_to_date(CONCAT(subdate(curdate(),date_format(curdate(),'%w') - b.week_day),b.start_time),'%Y-%m-%d%H:%i:%s') begin_time,
		date_add(str_to_date(CONCAT(subdate(curdate(),date_format(curdate(),'%w') - b.week_day),b.start_time),'%Y-%m-%d%H:%i:%s'),interval b.kill_time minute) end_time,
		b.price,b.member_price from t_mall_activity a
		INNER JOIN t_mall_activity_second_kill b on a.id = b.activity_id
		where now() >= a.start_date  and now() < date_add(a.stop_date, interval 1 day)
		and a.`enable`=1 and b.`enable` = 1
		and subdate(curdate(),date_format(curdate(),'%w') - b.week_day) >= a.start_date
		and subdate(curdate(),date_format(curdate(),'%w') - b.week_day) < date_add(a.stop_date, interval 1 day)
		) t where t.date < DATE_FORMAT(NOW(), '%Y-%m-%d') ORDER BY date desc LIMIT 1
)t1
UNION
SELECT * from
(
SELECT a.`name`,b.id, b.good_id,b.week_day,
		b.title,b.limit_count,b.stock_count,
		subdate(curdate(),date_format(curdate(),'%w') - b.week_day+7) date, b.start_time,b.kill_time,
		str_to_date(CONCAT(subdate(curdate(),date_format(curdate(),'%w') - b.week_day+7),b.start_time),'%Y-%m-%d%H:%i:%s') begin_time,
		date_add(str_to_date(CONCAT(subdate(curdate(),date_format(curdate(),'%w') - b.week_day+7),b.start_time),'%Y-%m-%d%H:%i:%s'),interval b.kill_time minute) end_time,
		b.price,b.member_price from t_mall_activity a
		INNER JOIN t_mall_activity_second_kill b on a.id = b.activity_id
		where now() >= a.start_date  and now() < date_add(a.stop_date, interval 1 day)
		and a.`enable`=1 and b.`enable` = 1
		and subdate(curdate(),date_format(curdate(),'%w') - b.week_day+7) >= a.start_date
		and subdate(curdate(),date_format(curdate(),'%w') - b.week_day+7) < date_add(a.stop_date, interval 1 day)
	UNION all
	SELECT a.`name`,b.id, b.good_id,b.week_day,
		b.title,b.limit_count,b.stock_count,
		subdate(curdate(),date_format(curdate(),'%w') - b.week_day) date, b.start_time,b.kill_time,
		str_to_date(CONCAT(subdate(curdate(),date_format(curdate(),'%w') - b.week_day),b.start_time),'%Y-%m-%d%H:%i:%s') begin_time,
		date_add(str_to_date(CONCAT(subdate(curdate(),date_format(curdate(),'%w') - b.week_day),b.start_time),'%Y-%m-%d%H:%i:%s'),interval b.kill_time minute) end_time,
		b.price,b.member_price from t_mall_activity a
		INNER JOIN t_mall_activity_second_kill b on a.id = b.activity_id
		where now() >= a.start_date  and now() < date_add(a.stop_date, interval 1 day)
		and a.`enable`=1 and b.`enable` = 1
		and subdate(curdate(),date_format(curdate(),'%w') - b.week_day) >= a.start_date
		and subdate(curdate(),date_format(curdate(),'%w') - b.week_day) < date_add(a.stop_date, interval 1 day)
) t2 where t2.date >= DATE_FORMAT(NOW(), '%Y-%m-%d') ;


INSERT INTO resources
SELECT NULL,'商城活动列表','shop/activity/list','shop:activity:list',resources.id,1,NULL,NULL
FROM resources 
WHERE permission='shop:all';

INSERT INTO resources
SELECT NULL,'添加/编辑商城活动','shop/activity/save','shop:activity:save',resources.id,1,NULL,NULL
FROM resources 
WHERE permission='shop:activity:list';

INSERT INTO resources
SELECT NULL,'商城活动详情','shop/activity/detail','shop:activity:detail',resources.id,1,NULL,NULL
FROM resources 
WHERE permission='shop:activity:list';

INSERT INTO resources
SELECT NULL,'禁用/启用商城活动','shop/activity/enable','shop:activity:enable',resources.id,1,NULL,NULL
FROM resources 
WHERE permission='shop:activity:list';

INSERT INTO resources
SELECT NULL,'新增/修改秒杀商品','shop/activity/secondKill/save','shop:activity:secondKill:save',resources.id,1,NULL,NULL
FROM resources 
WHERE permission='shop:activity:list';
