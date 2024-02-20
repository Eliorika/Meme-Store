CREATE TABLE image_tag (
    image_id bigint,
    tag_id bigint,
    primary key (image_id, tag_id)
);

alter table image_tag
    add constraint FK_image foreign key (image_id) references images(id);

alter table image_tag
    add constraint FK_tag foreign key (tag_id) references tags(id);
