-- Create table
create table cf_tokens (
  token         varchar(255) not null,
  token_refresh varchar(255) not null,
  user_id       bigint       not null,
  created_on    datetime     default current_timestamp,
  primary key (token)
)
engine=MyISAM
DEFAULT CHARSET=UTF8
;

-- Unique constraints
alter table cf_tokens add constraint cf_tokens_fk1
                             foreign key (user_id)
                          references cf_users (id)
;

alter table cf_tokens add constraint cf_users_u1
                              unique (token_refresh)
;
