CREATE TABLE image_board (
    image_id bigint,
    board_id bigint,
    primary key(image_id, board_id)
)

GO

alter table image_board
    add constraint FK_image foreign key (image_id) references images(id)
GO

alter table image_board
    add constraint FK_board foreign key (board_id) references boards(id)
