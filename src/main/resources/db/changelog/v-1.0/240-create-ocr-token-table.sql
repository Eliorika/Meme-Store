create table ocr_tokens
(
    id serial,
    token varchar not null,
    expired_at TIMESTAMP NOT NULL
)