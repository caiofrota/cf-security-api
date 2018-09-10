-- Insert permissions
insert into cf_permissions (id, created_by, created_on, updated_by, updated_on, cod, superadmin)
                    values (3, 1, now(), 1, now(), 'USER_FIND_ALL', 0),
                           (4, 1, now(), 1, now(), 'USER_FIND_BY_ID', 0),
                           (5, 1, now(), 1, now(), 'USER_SAVE', 0),
                           (6, 1, now(), 1, now(), 'USER_DELETE', 0),
                           (7, 1, now(), 1, now(), 'USER_CHANGEOWNPWD', 0),
                           (8, 1, now(), 1, now(), 'ROLES_FIND_ALL', 0),
                           (9, 1, now(), 1, now(), 'ROLES_FIND_BY_ID', 0),
                           (10, 1, now(), 1, now(), 'ROLES_SAVE', 0),
                           (11, 1, now(), 1, now(), 'ROLES_DELETE', 0),
                           (12, 1, now(), 1, now(), 'PERMISSIONS_FIND_ALL', 0),
                           (13, 1, now(), 1, now(), 'PERMISSIONS_FIND_BY_ID', 0),
                           (14, 1, now(), 1, now(), 'PERMISSIONS_SAVE', 0),
                           (15, 1, now(), 1, now(), 'PERMISSIONS_DELETE', 0),
                           (16, 1, now(), 1, now(), 'ROLE_PERMISSIONS_FIND_ALL', 0),
                           (17, 1, now(), 1, now(), 'ROLE_PERMISSIONS_FIND_BY_ID', 0),
                           (18, 1, now(), 1, now(), 'ROLE_PERMISSIONS_SAVE', 0),
                           (19, 1, now(), 1, now(), 'ROLE_PERMISSIONS_DELETE', 0),
                           (20, 1, now(), 1, now(), 'USER_ROLES_FIND_ALL', 0),
                           (21, 1, now(), 1, now(), 'USER_ROLES_FIND_BY_ID', 0),
                           (22, 1, now(), 1, now(), 'USER_ROLES_SAVE', 0),
                           (23, 1, now(), 1, now(), 'USER_ROLES_DELETE', 0)
;

-- Insert roles
insert into cf_roles (id, created_by, created_on, updated_by, updated_on, cod, superadmin)
              values (3, 1, now(), 1, now(), 'USER_MANAGER', 0),
                     (4, 1, now(), 1, now(), 'USER_ROLES_MANAGER', 0)
;

-- Insert role permissions
insert into cf_role_permissions (created_by, created_on, updated_by, updated_on, role_id, permission_id, superadmin)
                         values (1, now(), 1, now(), 3, 3, 0),
                                (1, now(), 1, now(), 3, 4, 0),
                                (1, now(), 1, now(), 3, 5, 0),
                                (1, now(), 1, now(), 3, 6, 0),
                                (1, now(), 1, now(), 3, 7, 0),
                                (1, now(), 1, now(), 4, 20, 0),
                                (1, now(), 1, now(), 4, 21, 0),
                                (1, now(), 1, now(), 4, 22, 0),
                                (1, now(), 1, now(), 4, 23, 0)
;
