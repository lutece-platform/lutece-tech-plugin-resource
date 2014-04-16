INSERT INTO core_admin_right VALUES ('RESOURCE_MANAGE_RESOURCES', 'resource.resourceManagement.pageTitle', 2, 'jsp/admin/plugins/resource/ManageResources.jsp', 'resource.resourceManagement.description', 0, 'resource', 'CONTENT', NULL, NULL, NULL);
INSERT INTO core_user_right (id_right,id_user) VALUES ('RESOURCE_MANAGE_RESOURCES',1);

INSERT INTO core_datastore VALUES ( 'core.cache.status.resource.resourceCacheService.enabled', '1' );
INSERT INTO core_datastore VALUES ( 'core.cache.status.resource.resourceCacheService.maxElementsInMemory', '500' );
