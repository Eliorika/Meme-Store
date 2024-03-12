alter table user_tag_votes
    alter column type type INT using type::INT;