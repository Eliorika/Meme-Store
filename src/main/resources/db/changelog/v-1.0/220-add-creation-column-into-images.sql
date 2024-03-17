alter table images
    add column if not exists created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL;