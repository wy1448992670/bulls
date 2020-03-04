
-- ALTER TABLE `project` MODIFY COLUMN `unit_manage_price`  double(12,4) NULL DEFAULT 0.0000 COMMENT '管理单价/天' AFTER `unit_feed_price`;

ALTER TABLE `bulls`.`project` MODIFY COLUMN `status` INT COMMENT ''0待上架1上架2待付款3已出售4已回购5已死亡6已删除'';
ALTER TABLE `bulls`.`project` ADD COLUMN `buy_again` BIT(1) NULL DEFAULT 0 COMMENT ''是否是续购'' AFTER `real_ear_number`;

-- 增加 buy_again 字段
CREATE OR REPLACE VIEW project_view AS
SELECT 
  `bulls`.`project`.`id` AS `id`,
  `bulls`.`project`.`parent_id` AS `parent_id`,
  `bulls`.`project`.`product_id` AS `product_id`,
  `bulls`.`project`.`investment_id` AS `investment_id`,
  `bulls`.`project`.`enterprise_id` AS `enterprise_id`,
  `bulls`.`project`.`title` AS `title`,
  `bulls`.`project`.`annualized` AS `annualized`,
  `bulls`.`project`.`increase_annualized` AS `increase_annualized`,
  `bulls`.`project`.`deadline` AS `deadline`,
  `bulls`.`project`.`limit_days` AS `limit_days`,
  `bulls`.`project`.`total_amount` AS `total_amount`,
  `bulls`.`project`.`start_time` AS `start_time`,
  `bulls`.`project`.`create_date` AS `create_date`,
  `bulls`.`project`.`update_date` AS `update_date`,
  `bulls`.`project`.`repayment_method` AS `repayment_method`,
  `bulls`.`project`.`project_description` AS `project_description`,
  `bulls`.`project`.`project_type` AS `project_type`,
  `bulls`.`project`.`user_id` AS `user_id`,
  `bulls`.`project`.`investors_num` AS `investors_num`,
  `bulls`.`project`.`invested_amount` AS `invested_amount`,
  `bulls`.`project`.`status` AS `status`,
  `bulls`.`project`.`version` AS `version`,
  `bulls`.`project`.`tag` AS `tag`,
  `bulls`.`project`.`item_number` AS `item_number`,
  `bulls`.`project`.`noob` AS `noob`,
  `bulls`.`project`.`rate_coupon_days` AS `rate_coupon_days`,
  `bulls`.`project`.`sort` AS `sort`,
  `bulls`.`project`.`contract_id` AS `contract_id`,
  `bulls`.`project`.`transferable` AS `transferable`,
  `bulls`.`project`.`plat_service_charge` AS `plat_service_charge`,
  `bulls`.`project`.`channel_service_charge` AS `channel_service_charge`,
  `bulls`.`project`.`repay_unit` AS `repay_unit`,
  `bulls`.`project`.`sex` AS `sex`,
  `bulls`.`project`.`raise_fee` AS `raise_fee`,
  `bulls`.`project`.`manage_fee` AS `manage_fee`,
  `bulls`.`project`.`ear_number` AS `ear_number`,
  `bulls`.`project`.`safe_number` AS `safe_number`,
  `bulls`.`project`.`interest_amount` AS `interest_amount`,
  `bulls`.`project`.`weight` AS `weight`,
  `bulls`.`project`.`unit_zoom_price` AS `unit_zoom_price`,
  `bulls`.`project`.`unit_feed_price` AS `unit_feed_price`,
  `bulls`.`project`.`unit_manage_price` AS `unit_manage_price`,
  `bulls`.`project`.`add_weight` AS `add_weight`,
  `bulls`.`project`.`gps_number` AS `gps_number`,
  `bulls`.`project`.`prairie_value` AS `prairie_value`,
  `bulls`.`project`.`expectant` AS `expectant`,
  `bulls`.`project`.`auto_enable_sale` AS `auto_enable_sale`,
  `bulls`.`project`.`real_ear_number` AS `real_ear_number`,
  `bulls`.`project`.`buy_again` AS `buy_again`,
  (
    `bulls`.`project`.`deadline` + INTERVAL `bulls`.`project`.`limit_days` DAY
  ) AS `due_time`,
  CAST(
    `project_property_view`.`yue_ling` AS UNSIGNED
  ) AS `yue_ling`,
  `investment_status`.`pay_time` AS `pay_time`,
  `investment_status`.`buy_back_time` AS `buy_back_time` 
FROM
  (
    (
      (
        `bulls`.`project` 
        LEFT JOIN `bulls`.`project_property_view` 
          ON (
            (
              `project_property_view`.`id` = `bulls`.`project`.`id`
            )
          )
      ) 
      LEFT JOIN `bulls`.`investment` 
        ON (
          (
            (
              `bulls`.`investment`.`order_status` IN (1, 2)
            ) 
            AND (
              `bulls`.`investment`.`project_id` = `bulls`.`project`.`id`
            )
          )
        )
    ) 
    LEFT JOIN 
      (SELECT 
        `bulls`.`order_done`.`order_no` AS `order_no`,
        MAX(
          (
            CASE
              WHEN (
                `bulls`.`order_done`.`order_status` = 'pay'
              ) 
              THEN `bulls`.`order_done`.`create_date` 
              ELSE NULL 
            END
          )
        ) AS `pay_time`,
        MAX(
          (
            CASE
              WHEN (
                `bulls`.`order_done`.`order_status` = 'success'
              ) 
              THEN `bulls`.`order_done`.`create_date` 
              ELSE NULL 
            END
          )
        ) AS `buy_back_time` 
      FROM
        `bulls`.`order_done` 
      WHERE (
          `bulls`.`order_done`.`order_type` = 'investment'
        ) 
      GROUP BY `bulls`.`order_done`.`order_no`) `investment_status` 
      ON (
        (
          `investment_status`.`order_no` = `bulls`.`investment`.`order_no`
        )
      )
  )
