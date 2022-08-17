CREATE TABLE t_rbac_parameter
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `tenant_id`   int(11) NOT null,
    `param_key`   varchar(100) not null,
    `param_value` varchar(100) not null,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;
create unique index t_rbac_parameter_key
    on t_rbac_parameter (tenant_id, param_key);