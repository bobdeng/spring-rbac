CREATE TABLE t_cbac_context
(
    `id`          int(11) NOT NULL AUTO_INCREMENT,
    `tenant_id`   int(11) NOT null,
    `object_type` varchar(20)  not null,
    `object_id`   varchar(50)  not null,
    `authority`   varchar(500) not null,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;
create unique index t_cbac_context_pk
    on t_cbac_context (tenant_id, object_type, object_id);