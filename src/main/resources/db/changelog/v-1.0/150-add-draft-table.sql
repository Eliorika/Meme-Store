create table drafts(
    uuid uuid not null primary key,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
)