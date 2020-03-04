ALTER TABLE `project` 
ADD COLUMN `auto_enable_sale` bit(1) DEFAULT b'0' COMMENT '是否自动上架' after `expectant`;

insert into tm_dict values(null,'标的自动上架数量','SALE_STATUS_PROJECT_MINI_COUNT','5','标的自动上架阈值',null,null);
