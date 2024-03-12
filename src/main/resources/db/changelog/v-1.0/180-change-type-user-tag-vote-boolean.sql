alter table user_tag_votes
    alter column type type boolean using type::boolean;