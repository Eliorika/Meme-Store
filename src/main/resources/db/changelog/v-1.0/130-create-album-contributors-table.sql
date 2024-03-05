CREATE TABLE album_contributors (
    user_id int not null,
    album_id int not null,
    primary key(user_id, album_id)
)

GO

alter table album_contributors
    add constraint FK_user foreign key (user_id) references users(id)
go

alter table album_contributors
    add constraint FK_tag foreign key (album_id) references albums(id)
