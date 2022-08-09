CREATE TABLE t_rbac_organization
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `tenant_id` int(11) NOT null,
    `name`      varchar(20) NOT null,
    `parent_id` int(11),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;