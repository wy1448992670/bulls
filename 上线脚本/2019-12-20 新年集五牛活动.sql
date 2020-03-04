CREATE TABLE `activity_blessing_regular_give`(
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`regular_time` datetime NOT NULL COMMENT '定期时间',
	`give_number` int(11) NOT NULL COMMENT '给几张',
	`is_given` bit(1) NOT NULL DEFAULT 0 COMMENT '是否已派发',
	`give_time` datetime NULL COMMENT '派发时间',
	`version` int(11) DEFAULT 0,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='定期发放福卡抽奖机会';


CREATE TABLE `activity_blessing_chance_record` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`user_id` int(11) DEFAULT NULL COMMENT '获得抽福卡机会的用户user.id',
	`get_count` int(11) DEFAULT NULL COMMENT '获得数量',
	`use_count` int(11) DEFAULT NULL COMMENT '已使用数量',
	`create_date` datetime DEFAULT NULL COMMENT '数据创建时间',
	`type` int(11) DEFAULT NULL COMMENT '获得方式:1定时发送 2分享获得 3注册获得 4邀请注册 5购牛 6邀请购牛',
	`foreign_id` int(11)  DEFAULT NULL COMMENT 'type=1.activity_blessing_regular_give.id 2.null 3.user.id 4.inveitee(user).id 5.investment.id 6.inveitee(user)的investment.id',
	`version` int(11) DEFAULT 0,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='福卡抽奖机会记录';


CREATE TABLE `activity_blessing_card_record` (
	`id` int(11) NOT NULL AUTO_INCREMENT,
	`card_no`  varchar(20) NOT NULL COMMENT '卡编号,转让后沿用',
	`user_id` int(11) NOT NULL COMMENT '获得抽福卡机会的用户user.id',
	`type` int(11) NOT NULL COMMENT '福卡类型:1 2 3 4 5 6.牛气冲天',
	`is_used` bit(1) NOT NULL DEFAULT 0 COMMENT '是否已使用,普通牛合成后为已使用,牛气冲天兑换红包后为已使用',
	`is_transfer` bit(1) NOT NULL DEFAULT 0 COMMENT '是否已转让,转让给他人后为已转让',
	`is_unfinished` bit(1) NOT NULL DEFAULT 0 COMMENT '是否未完成,仅牛气冲天卡,在用户未合成牛气冲天卡且需要预埋红包数据时,插入一条未完成的牛气冲天',
	`amount` double(12,2) NULL COMMENT '牛气冲天的金额,当牛气冲天未使用(is_used=false),此值不为null时,为预埋的用于发放的最终红包金额.为null时,按算法随机最终红包金额,存在这里',
	`is_double` bit(1) NULL DEFAULT 0 COMMENT '兑换牛气冲天时,是否翻倍',
	`create_date` datetime NOT NULL COMMENT '数据创建时间',
	`use_date` datetime DEFAULT NULL COMMENT '使用时间',
	`chance_record_id` int(11) DEFAULT NULL COMMENT '抽卡时使用的机会id(activity_blessing_chance_record.id),转让为null',
	`parent_id` int(11) DEFAULT NULL COMMENT '原转让卡id,抽出的卡为null',
	`is_fan` bit(1) NOT NULL DEFAULT 0 COMMENT '是否已翻过',
	`version` int(11) NOT NULL DEFAULT 0,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户福卡记录';

insert into activity_blessing_chance_record select null,id,1,0,now(),3,id,0 from user;

ALTER TABLE `hongbao` 
MODIFY COLUMN `trigger_type` int(10) NULL DEFAULT NULL COMMENT 'activity_detail.trigger_type 0.特殊活动 1.注册 2.投资 3.消费 4.登录 5.邀请首投 6.回购' AFTER `activity_detail_id`;

ALTER TABLE `activity` 
MODIFY COLUMN `end_time` datetime(0) NOT NULL COMMENT '结束时间' AFTER `start_time`;

insert into activity values(114,'春节集五牛',2957,'春节集五牛',CAST(SYSDATE()AS DATE),'2020-01-24 22:00:00','',1);


#日历
CREATE TABLE `calendar` (
  `date` date DEFAULT NULL,
  `id` int(11) NOT NULL,
  `y` smallint(6) DEFAULT NULL,
  `m` smallint(6) DEFAULT NULL,
  `d` smallint(6) DEFAULT NULL,
  `yw` smallint(6) DEFAULT NULL,
  `w` smallint(6) DEFAULT NULL,
  `q` smallint(6) DEFAULT NULL,
  `wd` smallint(6) DEFAULT NULL,
  `m_name` char(10) DEFAULT NULL,
  `wd_name` char(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


SET @d0 = "2019-01-01";
SET @d1 = "2025-12-31";
SET @date = date_sub(@d0, interval 1 day);

INSERT INTO calendar 
SELECT 
  @date := DATE_ADD(@date, INTERVAL 1 DAY) AS DATE,
  DATE_FORMAT(@date, "%Y%m%d") AS id,
  YEAR(@date) AS Y,
  MONTH(@date) AS m,
  DAY(@date) AS d,
  DATE_FORMAT(@date, "%x") AS yw,
  WEEK(@date, 3) AS w,
  QUARTER(@date) AS q,
  WEEKDAY(@date) + 1 AS wd,
  MONTHNAME(@date) AS m_name,
  DAYNAME(@date) AS wd_name 
FROM
 trade_record
WHERE DATE_ADD(@date, INTERVAL 1 DAY) <= @d1 
ORDER BY DATE ;

#发抽五牛机会
insert 
into activity_blessing_regular_give
select null,calendar.date,1,0,null,0
from calendar
where calendar.date>=CAST(SYSDATE()AS DATE) and calendar.date<='2020-01-24';

insert into activity_blessing_regular_give values(null,'2020-01-17 20:00:00',1,0,null,0);
insert into activity_blessing_regular_give values(null,'2020-01-18 20:00:00',1,0,null,0);
insert into activity_blessing_regular_give values(null,'2020-01-24 18:00:00',1,0,null,0);
insert into activity_blessing_regular_give values(null,'2020-01-24 19:00:00',1,0,null,0);
insert into activity_blessing_regular_give values(null,'2020-01-24 20:00:00',1,0,null,0);

#share
INSERT INTO share VALUES (null, '今年最容易集齐的五牛活动！', '集齐五牛，瓜分百万现金！发钱，我们是认真的！', NULL, 'https://wap.bfmuchang.com/cardRegister.html', '2019-01-10 00:00:00', NULL, NULL, '分享活动', 0);
INSERT INTO share VALUES (null, '送你一张牛卡，不要太感谢我喔~', '集齐五牛，瓜分百万现金！发钱，我们是认真的！', NULL, 'https://wap.bfmuchang.com/getnewYearCard.html', '2019-01-10 00:00:00', NULL, NULL, '送给朋友', 0);
