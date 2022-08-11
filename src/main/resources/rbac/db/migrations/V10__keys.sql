create unique index t_rbac_domain_pk
    on t_rbac_domain (domain);

create unique index t_rbac_login_name_pk
    on t_rbac_login_name (login_name,tenant_id);