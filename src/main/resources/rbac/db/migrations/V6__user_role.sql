CREATE TABLE t_rbac_user_role
(
    `id`      int(11) NOT NULL AUTO_INCREMENT,
    `user_id` int(11) NOT null,
    `role_id` int(11) NOT null,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
;