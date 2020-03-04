
CREATE TABLE `export_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_user_id` int(11) NOT NULL COMMENT '申请人',
  `apply_time` datetime NOT NULL COMMENT '申请时间',
  `target_list` int(4) NOT NULL COMMENT '导出目标列表 0.网站用户',
  `apply_reason` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '申请原因',
  `audit_user_id` int(11) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_remark` varchar(255) CHARACTER SET utf8 DEFAULT '' COMMENT '审核备注',
  `apply_status` int(11) NOT NULL DEFAULT '0' COMMENT '申请状态 0.申请中，1.通过，2.拒绝，',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `version` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='导出申请表';

CREATE TABLE `export_apply_condition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_id` int(11) NOT NULL COMMENT 'export_apply.id',
  `property_code` varchar(32) NOT NULL COMMENT '条件字段名称',
  `property_name` varchar(64) NOT NULL COMMENT '条件字段描述',
  `value_code` varchar(32) NOT NULL COMMENT '条件值',
  `value_name` varchar(64) NOT NULL COMMENT '条件值的描述',
  `rule` varchar(20) DEFAULT NULL COMMENT '查询规则，SearchRuleEnum',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='导出申请条件表';

CREATE TABLE `export_apply_columns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apply_id` int(11) NOT NULL COMMENT 'export_apply.id',
  `col_code` varchar(32) NOT NULL COMMENT '列名',
  `col_name` varchar(64) NOT NULL COMMENT '列名描述',
  `is_encrypt` bit(1) DEFAULT NULL COMMENT '是否加密',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='导出申请列表';

INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (322, '导出申请列表', 'exportApply/exportApplyList', 'exportApply:exportApplyList', 2, 1, NULL, NULL);
INSERT INTO `bulls`.`resources`(`id`, `name`, `url`, `permission`, `parent_id`, `ismenu`, `seq`, `icon`) VALUES (325, '导出审核', 'exportApply/exportApplyAudit', 'exportApply:exportApplyAudit', 322, 0, NULL, NULL);

INSERT INTO `bulls`.`resources` VALUES(NULL,'导出网站用户列表申请','user/export/apply','user:export:apply',9,0,NULL,NULL);
INSERT INTO `bulls`.`resources` VALUES(NULL,'导出下载','exportApply/download','exportApply:download',322,0,NULL,NULL);
