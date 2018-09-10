-- SUPERADMIN
insert into cf_users (id, created_by, created_on, updated_by, updated_on, email, enabled, superadmin, name, password)
              values (1, 1, now(), 1, now(), 'superadmin@cftechsol.com', 1, 1, 'Super Admin', '$2a$10$Lmz/VVefxOTeZmUK8/Tg4uHVmh0nmHz1kaTg0gMkxkkYo/rJcXBwi')
;

insert into cf_roles (id, created_by, created_on, updated_by, updated_on, cod, superadmin)
              values (1, 1, now(), 1, now(), 'SUPERADMIN', 1)
;

insert into cf_permissions (id, created_by, created_on, updated_by, updated_on, cod, superadmin)
                    values (1, 1, now(), 1, now(), 'SUPERADMIN', 1)
;

insert into cf_user_roles (created_by, created_on, updated_by, updated_on, user_id, role_id, superadmin)
                   values (1, now(), 1, now(), 1, 1, 1)
;

insert into cf_role_permissions (created_by, created_on, updated_by, updated_on, role_id, permission_id, superadmin)
                         values (1, now(), 1, now(), 1, 1, 1)
;

-- ADMIN

insert into cf_users (id, created_by, created_on, updated_by, updated_on, email, enabled, name, password)
              values (2, 1, now(), 1, now(), 'admin@cftechsol.com', 1, 'Admin', '$2a$10$Lmz/VVefxOTeZmUK8/Tg4uHVmh0nmHz1kaTg0gMkxkkYo/rJcXBwi')
;

insert into cf_roles (id, created_by, created_on, updated_by, updated_on, cod)
              values (2, 1, now(), 1, now(), 'ADMIN')
;

insert into cf_permissions (id, created_by, created_on, updated_by, updated_on, cod)
                    values (2, 1, now(), 1, now(), 'ADMIN')
;

insert into cf_user_roles (created_by, created_on, updated_by, updated_on, user_id, role_id)
                   values (1, now(), 1, now(), 2, 2)
;

insert into cf_role_permissions (created_by, created_on, updated_by, updated_on, role_id, permission_id)
                         values (1, now(), 1, now(), 2, 2)
;
