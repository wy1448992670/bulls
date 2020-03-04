
update user set department_id=2
where channel_id is null and department_id=1 and create_date<'2019-10-25';
update user set department_id=2 where phone='18704363149';
update user set department_id=2 where phone='18659363636';
update user set department_id=2 where phone='15859365333';
update user set department_id=2 where phone='13581121305';
update user set department_id=2 where phone='15263298650';
update user set department_id=2 where phone='13564974407';
update user set department_id=2 where phone='13336372575';
update user set department_id=2 where phone='13587757977';
update user set department_id=2 where phone='18904710110';
update user set department_id=2 where phone='18104710099';
update user set department_id=2 where phone='13793195322';
update user set department_id=2 where phone='13868758079';
update user set department_id=2 where phone='13843688806';
update user set department_id=2 where phone='15004946644';
update user set department_id=2 where phone='15988733222';
update user set department_id=2 where phone='13736386666';
update user set department_id=2 where phone='18857733999';
update user set department_id=2 where phone='15246429175';
update user set department_id=2 where phone='13429028860';
update user set department_id=2 where phone='13854098645';
update user set department_id=2 where phone='13853315280';
update user set department_id=2 where phone='18721762808';
update user set department_id=2 where phone='13843601611';
update user set department_id=2 where phone='18059303602';
update user set department_id=2 where phone='17857188351';
update user set department_id=2 where phone='15928288633';
update user set department_id=2 where phone='15241851852';
update user set department_id=2 where phone='13452525567';
update user set department_id=2 where phone='15067709795';
update user set department_id=2 where phone='15043660707';
update user set department_id=2 where phone='13905877537';
update user set department_id=2 where phone='18850725235';
update user set department_id=2 where phone='13453184800';
update user set department_id=2 where phone='13606322988';
update user set department_id=2 where phone='13816206031';

insert into employ_user 
select null,employ.id,invitee.id,now(),null
from employ
inner join user inviter on employ.mobile=inviter.phone
inner join user invitee on inviter.invite_code=invitee.invite_by_code
left join employ_user on employ_user.user_id=invitee.id
where employ_user.id is null;


update user inviter
inner join user invitee on inviter.invite_code=invitee.invite_by_code
set invitee.department_id=inviter.department_id;
update user inviter
inner join user invitee on inviter.invite_code=invitee.invite_by_code
set invitee.department_id=inviter.department_id;
update user inviter
inner join user invitee on inviter.invite_code=invitee.invite_by_code
set invitee.department_id=inviter.department_id;
update user inviter
inner join user invitee on inviter.invite_code=invitee.invite_by_code
set invitee.department_id=inviter.department_id;

update department set name='运营' where id=1;
update department set name='大客户' where id=2;
