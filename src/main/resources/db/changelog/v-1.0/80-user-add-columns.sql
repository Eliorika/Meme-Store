alter table users
add column is_extremist boolean default false

go

alter table users
    add column display_name varchar(50) default false
