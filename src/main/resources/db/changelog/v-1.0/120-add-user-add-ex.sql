alter table users
    drop column if exists is_extremist;
go
alter table users
    add column extremist_date date default null;
go
alter table users
    add column foreign_agent_date date default null;
