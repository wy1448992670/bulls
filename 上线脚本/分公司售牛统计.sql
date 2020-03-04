INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '分公司售牛统计', 'report/filialeSell', 'invest:filialeSell', 63, 1, NULL, NULL);
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '售牛统计导出', 'report/export/filialeSell', 'invest:export:filialeSell', 63, 0, NULL, NULL);



INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '售牛详细导出', 'report/export/filialeSellDetail', 'invest:export:filialeSellDetail', 63, 0, NULL, NULL);
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '分公司售牛详细', 'report/filialeSellDetail', 'invest:filialeSellDetail', 63, 0, NULL, NULL);
insert into employ_user(emoloy_id, user_id) select employ.id, user.id from employ left join user on employ.mobile = user.phone where user.id not in (select user_id from employ_user);
