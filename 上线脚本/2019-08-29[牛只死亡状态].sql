ALTER TABLE `bulls`.`project` 
MODIFY COLUMN `status` int(11) NULL DEFAULT 0 COMMENT '0待上架1上架2待付款3已出售4已回购5死亡' AFTER `invested_amount`;