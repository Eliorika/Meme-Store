CREATE TABLE albums (
    id serial PRIMARY KEY,
    name varchar(50) not null,
    author_id int not null,
    status varchar(30) not null default 'public',
    description varchar(250),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
alter table albums
    add constraint FK_user foreign key (author_id) references users(id);