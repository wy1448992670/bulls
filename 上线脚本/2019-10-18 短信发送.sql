CREATE TABLE sms_send  (
  id BIGINT(0) NOT NULL AUTO_INCREMENT COMMENT '短信发送id',
  reserve_send_time DATETIME(0) NOT NULL COMMENT '预计发送时间',
  send_channel_id INT NOT NULL DEFAULT 1 COMMENT '发送通道id, MessageChannelEnum.id',
  send_status INT NOT NULL DEFAULT 0 COMMENT '是否已发送 0未发,1已发,-1失败',
  send_time DATETIME(0) NULL COMMENT '实际发送时间',
  mobile VARCHAR(255) NOT NULL COMMENT '短信接收手机号码',
  content VARCHAR(255) NOT NULL COMMENT '短信内容',
  create_time DATETIME(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
);
