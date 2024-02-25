CREATE TABLE users
(
    id serial primary key,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(256) NOT NULL,
    email VARCHAR(256) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
)
GO
alter table users
    add constraint UK_email unique (email);

alter table users
    add constraint UK_username unique (username);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id int references users (id),
    role    varchar(50) not null,
    primary key (user_id, role)
);

go

INSERT INTO users (username, password, email )VALUES ('user', '$2a$10$2HS96q3QVZm0ZVGSxogOqOdyM94uGkw.P7MUa36WjkXYrJTk80kOi', 'a@a.ru');
INSERT INTO user_roles (user_id, role) VALUES (1, 'USER_ROLE');

