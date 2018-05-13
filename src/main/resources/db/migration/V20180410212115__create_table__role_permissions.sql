-- Create table
create table cf_role_permissions (
  created_by    bigint,
  created_on    datetime,
  updated_by    bigint,
  updated_on    datetime,
  permission_id bigint    not null,
  role_id       bigint    not null,
  primary key (permission_id, role_id)
)
engine=MyISAM
DEFAULT CHARSET=UTF8
;

-- Relationships
alter table cf_role_permissions add constraint cf_role_permissions_fk2
                                   foreign key (permission_id)
                                    references cf_permissions (id)
;

alter table cf_role_permissions add constraint cf_role_permissions_fk1
                                   foreign key (role_id)
                                    references cf_roles (id)
;
