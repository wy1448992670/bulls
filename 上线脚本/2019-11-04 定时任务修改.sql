# 渠道注册用户唤醒发送短信定时任务执行时间修改
UPDATE qrtz_cron_triggers SET cron_expression='0 0 3 * * ?' WHERE trigger_name='awakenChannelRegisterMessage'
