CREATE TABLE tags (
    id serial PRIMARY KEY,
    tag varchar(50) not null
)

GO
alter table tags
    add constraint UK_tag unique (tag);

