DROP TABLE IF EXISTS resource_resource;
CREATE TABLE resource_resource
(
	id_resource INT DEFAULT 0 NOT NULL,
	resource_type VARCHAR(255) DEFAULT NULL,
	resource_name VARCHAR(255) DEFAULT NULL,
	PRIMARY KEY (id_resource)
);

CREATE INDEX resource_resource_type_idx ON resource_resource(resource_type);

DROP TABLE IF EXISTS resource_resource_type;
CREATE TABLE resource_resource_type
(
	resource_type_name VARCHAR(255) DEFAULT NULL,
	resource_type_description VARCHAR(255) DEFAULT NULL,
	PRIMARY KEY (resource_type_name)
);
