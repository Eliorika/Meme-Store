CREATE TABLE boards (
    id bigserial PRIMARY KEY,
    title varchar(50) not null,
    user_id bigint not null,
    status varchar(30) not null default 'public',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
alter table boards
    add constraint FK_user foreign key (user_id) references users(id);