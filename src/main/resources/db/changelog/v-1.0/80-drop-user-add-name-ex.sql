alter table users
    drop column if exists is_extremist;

    go

alter table users
    drop column if exists display_name;
