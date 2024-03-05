CREATE TABLE images (
    id serial PRIMARY KEY,
    uuid uuid,
    author_id int not null,
    extension varchar(15) not null,
    title varchar (100),
    status varchar(30) not null default 'public'
)
GO
alter table images
    add constraint UK_bucket unique (uuid);

alter table images
    add constraint FK_owner foreign key (author_id) references users(id)