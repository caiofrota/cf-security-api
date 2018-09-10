-- Insert roles
insert into cf_roles (id, created_by, created_on, updated_by, updated_on, cod, superadmin)
              values (5, 1, now(), 1, now(), 'PERMISSIONS_MANAGER', 0),
			         (6, 1, now(), 1, now(), 'ROLES_MANAGER', 0),
			         (7, 1, now(), 1, now(), 'ROLE_PERMISSIONS_MANAGER', 0),
			         (8, 1, now(), 1, now(), 'SUBSCRIBER', 0)
;

-- Insert role permissions
insert into cf_role_permissions (created_by, created_on, updated_by, updated_on, role_id, permission_id, superadmin)
                         values (1, now(), 1, now(), 4, 3, 0),
						        (1, now(), 1, now(), 4, 4, 0),
						        (1, now(), 1, now(), 4, 7, 0),
						        (1, now(), 1, now(), 5, 7, 0),
						        (1, now(), 1, now(), 5, 16, 0),
                                (1, now(), 1, now(), 5, 17, 0),
                                (1, now(), 1, now(), 5, 18, 0),
                                (1, now(), 1, now(), 5, 19, 0),
                                (1, now(), 1, now(), 6, 7, 0),
                                (1, now(), 1, now(), 6, 8, 0),
                                (1, now(), 1, now(), 6, 9, 0),
                                (1, now(), 1, now(), 6, 10, 0),
                                (1, now(), 1, now(), 6, 11, 0),
                                (1, now(), 1, now(), 7, 7, 0),
                                (1, now(), 1, now(), 7, 8, 0),
                                (1, now(), 1, now(), 7, 9, 0),
                                (1, now(), 1, now(), 7, 16, 0),
                                (1, now(), 1, now(), 7, 17, 0),
                                (1, now(), 1, now(), 7, 18, 0),
                                (1, now(), 1, now(), 7, 19, 0),
                                (1, now(), 1, now(), 8, 7, 0)
;
