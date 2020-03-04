ALTER TABLE `bank_card_bin` 
ADD COLUMN `line_number` varchar(11) NULL COMMENT '联行号' AFTER `state`;

update bank_card_bin set line_number=bank_id;

update bank_card_bin set bank_id=5 where bank_name_center='中国邮储银行' and card_type='借记卡' and state=1;

