create table if not exists tenant_roles(
    id int not null primary key,
    role varchar(100) not null
);

go

INSERT INTO tenant_roles (id, role) VALUES
                                        (0, 'anonymous guest a.k.a. unauthorized user'),
                                        (1, 'authorized user with default capabilities set (read-only access)'),
                                        (2, 'private uploader: allows uploading of private assets'),
                                        (3, 'public uploader: allows uploading of public assets'),
                                        (4, 'voter: allows affect voting on tags'),
                                        (5, 'tag assigner: allows voting (assigning) for a new tag'),
                                        (6, 'tag creator: allows creating new tags'),
                                        (7, 'private album creator: allows creating private albums (and deletion of owned private albums)'),
                                        (8, 'public album creator: allows creating public albums (and deletion of owned public albums)'),
                                        (256, 'global admin'),
                                        (257, 'debug view, this role must only provide access to debug data'),
                                        (258, 'tag vote moderator: allows tag vote deletion'),
                                        (259, 'tag moderator: allows deletion of tags and associated votes'),
                                        (260, 'uploads moderator: allows edit and deletion of accessible assets'),
                                        (261, 'public album moderator: allows edit and deletion of public albums'),
                                        (262, 'private album moderator: allows edit, view and deletion of private albums'),
                                        (263, 'user tenant moderator: allows edit of user tenants'),
                                        (264, 'allows deletion of user tenants'),
                                        (265, '(service) create user tenants: allows creation of user tenants'),
                                        (266, '(service) authorize user tenants: allows creation of user tenants');
go
create table if not exists user_tenant_roles(
    user_id int not null references users(id),
    tenant_role_id int not null references tenant_roles(id),
    primary key (user_id, tenant_role_id)
);
