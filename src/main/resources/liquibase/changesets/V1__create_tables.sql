CREATE TABLE IF NOT EXISTS users
(
    id       bigserial primary key,
    username varchar(50)  not null unique,
    password varchar(256) not null
);

CREATE TABLE IF NOT EXISTS user_roles
(
    user_id bigint references users (id),
    role    varchar(50) not null,
    primary key (user_id, role)
);

INSERT INTO users (id, username, password)VALUES (NEXTVAL('users_id_seq'), 'user', '$2a$10$2HS96q3QVZm0ZVGSxogOqOdyM94uGkw.P7MUa36WjkXYrJTk80kOi');
INSERT INTO user_roles (user_id, role) VALUES (1, 'USER_ROLE');