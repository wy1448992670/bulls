
INSERT INTO resources
SELECT NULL,'下架项目','project/lowerShelves','project:lowerShelves',resources.id,0,NULL,NULL
FROM resources 
WHERE permission='project:view';

