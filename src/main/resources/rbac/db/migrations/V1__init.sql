CREATE TABLE t_rbac_tenant
(
    `id`   int(11) NOT NULL AUTO_INCREMENT,
    `name` varchar(20) NOT null,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4
;
CREATE TABLE t_rbac_user
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `name`      varchar(20) NOT null,
    `tenant_id` int(11) NOT null,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;