INSERT INTO resources
select NULL,'查询全部回购权限','project/buyBack/viewall','project:buyBack:viewall',resources.id,0,NULL,NULL
from resources 
where permission='project:buyBack:view';

