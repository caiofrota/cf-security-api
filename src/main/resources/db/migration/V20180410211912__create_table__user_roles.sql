-- Create table
create table cf_user_roles (
  created_by bigint,
  created_on datetime,
  updated_by bigint,
  updated_on datetime,
  role_id    bigint    not null,
  user_id    bigint    not null,
  superadmin bit       not null default 0,
  primary key (role_id, user_id)
)
engine=InnoDB
DEFAULT CHARSET=UTF8
;

-- Relationships
alter table cf_user_roles add constraint cf_user_roles_fk2
                             foreign key (role_id)
                              references cf_roles (id)
;

alter table cf_user_roles add constraint cf_user_roles_fk1
                             foreign key (user_id)
                              references cf_users (id)
;
