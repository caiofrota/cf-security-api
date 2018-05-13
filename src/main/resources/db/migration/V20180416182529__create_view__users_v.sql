create or replace view cf_users_v as
select u.email    username
     , u.password password
     , u.enabled  enabled
  from cf_users u;
