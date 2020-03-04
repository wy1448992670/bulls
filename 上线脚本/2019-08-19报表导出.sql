#--------商城订单----------
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '导出商城订单列表', 'report/exportOrderList', 'report:exportOrderList', 211, 0, NULL, NULL);
#--------退款审核----------
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (null, '导出退款审核列表', 'report/exportRefundList', 'report:exportRefundList', 215, 0, NULL, NULL);

INSERT  INTO resources VALUES(null,'导出交易记录', 'trade/list/export', 'trade:list:export', 110, 0,null,'');
update  resources set name='导出客户资金快照' where permission='trade:exportDetail';
#--------导出红包列表----------
INSERT  INTO resources(NAME, url, permission, parent_id, ismenu) VALUES('导出红包列表', 'hongbao/export', 'hongbao:export', 108, 0);
#--------导出利息列表----------
INSERT  INTO resources(NAME, url, permission, parent_id, ismenu) VALUES('导出利息列表', 'interest/export', 'interest:export', 98, 0);
