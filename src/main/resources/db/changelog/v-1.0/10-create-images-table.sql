CREATE TABLE images (
    id bigserial PRIMARY KEY,
    url varchar,
    owner_id bigint not null,
    status varchar(30) not null default 'public'
)

GO
alter table images
    add constraint UK_url unique (url);

alter table images
    add constraint FK_owner foreign key (owner_id) references users(id)