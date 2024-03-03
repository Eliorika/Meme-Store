alter table users
    add column tg_id bigint;
alter table users
    alter column email drop not null;

