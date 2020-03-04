INSERT INTO resources
SELECT NULL,'线下充值编辑','recharge/rechargeOfflineApply/edit','recharge:rechargeOfflineApply:edit',resources.id,0,NULL,NULL
FROM resources 
WHERE permission='recharge:rechargeOfflineApply:list';

INSERT INTO resources
SELECT NULL,'线下充值删除','recharge/rechargeOfflineApply/delete','recharge:rechargeOfflineApply:delete',resources.id,0,NULL,NULL
FROM resources 
WHERE permission='recharge:rechargeOfflineApply:list';

ALTER TABLE `bulls`.`recharge_offline_apply` 
ADD COLUMN `last_update_by` INT(11) NULL DEFAULT NULL COMMENT '申请单最后编辑人' AFTER `voucher_pic_id`,
ADD COLUMN `last_update_time` DATETIME NULL DEFAULT NULL COMMENT '申请单最后编辑时间' AFTER `last_update_by`;
