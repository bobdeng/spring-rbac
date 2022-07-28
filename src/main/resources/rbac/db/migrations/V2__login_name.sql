CREATE TABLE t_rbac_login_name
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    `login_name` varchar(20) NOT null,
    `tenant_id`  int(11) NOT NULL,
    `user_id`    int(11) NOT null,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;