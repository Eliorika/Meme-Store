CREATE TABLE image_tags (
    image_id int,
    tag_id int,
    score int default 5,
    primary key (image_id, tag_id)
);

alter table image_tags
    add constraint FK_image foreign key (image_id) references images(id);

alter table image_tags
    add constraint FK_tag foreign key (tag_id) references tags(id);
