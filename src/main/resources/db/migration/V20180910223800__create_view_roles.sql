create or replace view cf_user_roles_v as
select u.email username
     , r.cod   roles
  from cf_users      u
     , cf_user_roles ur
     , cf_roles      r
 where ur.user_id = u.id
   and r.id       = ur.role_id;
