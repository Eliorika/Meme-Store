alter table albums
    drop column status;

alter table images drop column status;

alter table albums
    add column status bool;
