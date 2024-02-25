CREATE TABLE user_tag_votes (
    user_id int not null,
    image_id int not null,
    tag_id int not null,
    type boolean not null,
    primary key(user_id, image_id, tag_id)
)

GO

alter table user_tag_votes
    add constraint FK_image foreign key (image_id) references images(id)
GO

alter table user_tag_votes
    add constraint FK_user foreign key (user_id) references users(id)
go

alter table user_tag_votes
    add constraint FK_tag foreign key (tag_id) references tags(id)
