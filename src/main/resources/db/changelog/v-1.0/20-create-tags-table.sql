CREATE TABLE tags (
    id bigserial PRIMARY KEY,
    tag varchar(20) not null
)

GO
alter table tags
    add constraint UK_tag unique (tag);

