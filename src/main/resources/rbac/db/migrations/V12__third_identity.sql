CREATE TABLE t_rbac_third_identity
(
    `id`             int(11) NOT NULL AUTO_INCREMENT,
    `user_id`        int(11) NOT null,
    `tenant_id`      int(11) NOT null,
    `third_name`     varchar(10)  not null,
    `identity` varchar(100) not null,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;