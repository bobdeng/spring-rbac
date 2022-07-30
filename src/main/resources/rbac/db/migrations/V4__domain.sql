CREATE TABLE t_rbac_domain
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `domain`    varchar(50) NOT null,
    `tenant_id` int(11) NOT null,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;