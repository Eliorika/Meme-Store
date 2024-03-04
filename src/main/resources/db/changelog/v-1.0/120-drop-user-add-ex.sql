alter table users
    drop column if exists is_extremist;

alter table users
    drop column if exists extremist;

alter table users
    drop column if exists foreign_agent;
