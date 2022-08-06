CREATE TABLE t_rbac_role
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `name`      varchar(50) NOT null,
    `allows`    text        not null,
    `tenant_id` int(11) NOT null,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;