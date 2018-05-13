create or replace view cf_user_authorities_v as
select u.email username
     , p.cod   authorities
  from cf_users            u
     , cf_user_roles       ur
     , cf_role_permissions rp
     , cf_permissions      p
 where ur.user_id = u.id
   and rp.role_id = ur.role_id
   and p.id       = rp.permission_id;
