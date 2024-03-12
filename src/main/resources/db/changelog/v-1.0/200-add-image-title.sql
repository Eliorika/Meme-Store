alter table images
    add column if not exists title varchar(255) not null default 'noname';
