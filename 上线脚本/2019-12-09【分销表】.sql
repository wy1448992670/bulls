ALTER TABLE `bulls`.`employ` 
ADD COLUMN `is_dimission` bit(1) NULL DEFAULT 0 COMMENT '是否离职(1是 0否)' AFTER `company`;