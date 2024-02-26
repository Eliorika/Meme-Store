CREATE TABLE album_images (
    image_id int,
    album_id int,
    primary key(image_id, album_id)
)

GO

alter table album_images
    add constraint FK_image foreign key (image_id) references images(id)
GO

alter table album_images
    add constraint FK_board foreign key (album_id) references albums(id)
