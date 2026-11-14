-- liquibase formatted sql
-- changeset resource:update_db_resource-3.0.0-3.1.0.sql
-- preconditions onFail:MARK_RAN onError:WARN
-- The plugin descriptor declared the right RESOURCE_MANAGEMENT, which the management screens do not check: the
-- administrators who hold it get RESOURCE_MANAGE_RESOURCES, then RESOURCE_MANAGEMENT is removed
INSERT INTO core_user_right ( id_right, id_user )
    SELECT 'RESOURCE_MANAGE_RESOURCES', ur.id_user FROM core_user_right ur
    WHERE ur.id_right = 'RESOURCE_MANAGEMENT'
    AND NOT EXISTS ( SELECT 1 FROM core_user_right ur2 WHERE ur2.id_right = 'RESOURCE_MANAGE_RESOURCES' AND ur2.id_user = ur.id_user );
DELETE FROM core_user_right WHERE id_right = 'RESOURCE_MANAGEMENT';
DELETE FROM core_admin_right WHERE id_right = 'RESOURCE_MANAGEMENT';
