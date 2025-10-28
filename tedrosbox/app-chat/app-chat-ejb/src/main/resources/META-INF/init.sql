create user if not exists myuser  PASSWORD 'xpto'  ADMIN;
create schema if not exists myschema authorization myuser;

