
update `share` set title = '优质好货，全场满300包邮自家阳光草场出品，绿色环保健康！', context = '牛肉生鲜垂直精选商城，全程可溯源，注册有好礼！', link = 'https://wap.bfmuchang.com/h5register3.html' where id = 1;


INSERT INTO resources VALUES(NULL,'添加虚拟评价','shop/addAppraise','shop:addAppraise',212,0,NULL,NULL);
INSERT INTO resources VALUES(NULL,'虚拟评价列表','shop/appraiseList','shop:appraiseList',212,0,NULL,NULL);

INSERT INTO `bulls`.`resources` (`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '商品类别新增', 'shop/addCategory', 'shop:addCategory', '209', '0', NULL, NULL);
INSERT INTO `bulls`.`resources` (`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '商品类别列表', 'shop/listCategory', 'shop:listCategory', '209', '1', NULL, NULL);
INSERT INTO `bulls`.`resources` (`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '商品类别编辑', 'shop/editCategory', 'shop:editCategory', '209', '0', NULL, NULL);

update tm_dict set t_value=1 where t_key='PINGNIU_PROJECT_SHOW_NUM';
