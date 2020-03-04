CREATE TABLE `t_goods_click` (
  `id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `click` int(11) DEFAULT '0' COMMENT '点击次数',
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `t_goods_category` ADD COLUMN `ad_upload_id` INT(11) NULL DEFAULT null COMMENT '类型广告位';
ALTER TABLE `t_goods_category` ADD COLUMN `logo_upload_id` int(11) NULL COMMENT '分类logo图片ID' AFTER `ad_upload_id`;
/**
 * 分类名称根据实际业务调整
 */
--INSERT INTO `bulls`.`t_goods_category` (`id`, `parent_id`, `category_name`, `create_date`, `update_date`, `ad_upload_id`) VALUES (null, '1', '高端牛排', now(), now(), NULL);
--INSERT INTO `bulls`.`t_goods_category` (`id`, `parent_id`, `category_name`, `create_date`, `update_date`, `ad_upload_id`) VALUES (null, '1', '精品生鲜', now(), now(), NULL);
--INSERT INTO `bulls`.`t_goods_category` (`id`, `parent_id`, `category_name`, `create_date`, `update_date`, `ad_upload_id`) VALUES (null, '1', '精品红酒', now(), now(), NULL);
--INSERT INTO `bulls`.`t_goods_category` (`id`, `parent_id`, `category_name`, `create_date`, `update_date`, `ad_upload_id`) VALUES (null, '1', '熟食加工', now(), now(), NULL);
--INSERT INTO `bulls`.`t_goods_category` (`id`, `parent_id`, `category_name`, `create_date`, `update_date`, `ad_upload_id`) VALUES (null, '1', '厨房配件', now(), now(), NULL);
--INSERT INTO `bulls`.`t_goods_category` (`id`, `parent_id`, `category_name`, `create_date`, `update_date`, `ad_upload_id`) VALUES (null, '1', '同城配送', now(), now(), NULL);
--INSERT INTO `bulls`.`t_goods_category` (`id`, `parent_id`, `category_name`, `create_date`, `update_date`, `ad_upload_id`) VALUES (null, '1', '农产品区', now(), now(), NULL);
--INSERT INTO `bulls`.`t_goods_category` (`id`, `parent_id`, `category_name`, `create_date`, `update_date`, `ad_upload_id`) VALUES (null, '1', '奔富牛卡', now(), now(), NULL);
DELETE from t_goods_category;
update t_goods set category_id = null;
DELETE from user_address;
INSERT INTO `bulls`.`t_goods_category` (`id`, `parent_id`, `category_name`, `create_date`, `update_date`, `ad_upload_id`) VALUES (0, '0', '全部商品', now(), now(), NULL);
INSERT INTO `bulls`.`t_goods_category` (`id`, `parent_id`, `category_name`, `create_date`, `update_date`, `ad_upload_id`) VALUES (null, '0', '特惠套餐', now(), now(), NULL);
INSERT INTO `bulls`.`t_goods_category` (`id`, `parent_id`, `category_name`, `create_date`, `update_date`, `ad_upload_id`) VALUES (null, '0', '原切牛排', now(), now(), NULL);
INSERT INTO `bulls`.`t_goods_category` (`id`, `parent_id`, `category_name`, `create_date`, `update_date`, `ad_upload_id`) VALUES (null, '0', '新鲜牛肉', now(), now(), NULL);
INSERT INTO `bulls`.`t_goods_category` (`id`, `parent_id`, `category_name`, `create_date`, `update_date`, `ad_upload_id`) VALUES (null, '0', '休闲零食', now(), now(), NULL);
# 爆款商品
INSERT INTO `tm_dict` (`id`, `t_name`, `t_key`, `t_value`, `memo`, `t_sort`, `tp`) VALUES (null, '爆款商品数量', 'HOT_GOODS', '10', NULL, NULL, NULL);
# 秒杀预热时间
INSERT INTO `tm_dict` (`id`, `t_name`, `t_key`, `t_value`, `memo`, `t_sort`, `tp`) VALUES (null, '秒杀预热时间', 'ACTIVITY_KILL_TIME', '60', '分钟', NULL, NULL);

# 商品category_id设置为空之后数据维护
INSERT INTO t_goods_property (category_id, property_name,create_date,update_date) 
select t.id as category_id ,'规格' as property_name ,NOW(), NOW() from t_goods_category t 
	where t.id <> 0 and t.id not in(select category_id from t_goods_property)
