drop trigger if exists update_image_tag_score_trigger on user_tag_votes;

drop function if exists update_image_tags_score();
CREATE OR REPLACE FUNCTION update_image_tag_score() RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'DELETE' THEN
        UPDATE image_tags it
        SET score = 5 + (
            SELECT COALESCE(SUM(CASE WHEN utv.type THEN 1 ELSE -1 END), 0)
            FROM user_tag_votes utv
            WHERE utv.image_id = it.image_id AND utv.tag_id = it.tag_id
        )
        WHERE it.image_id = OLD.image_id AND it.tag_id = OLD.tag_id;
    ELSE
        UPDATE image_tags it
        SET score = 5 + (
            SELECT COALESCE(SUM(CASE WHEN utv.type THEN 1 ELSE -1 END), 0)
            FROM user_tag_votes utv
            WHERE utv.image_id = it.image_id AND utv.tag_id = it.tag_id
        )
        WHERE it.image_id = NEW.image_id AND it.tag_id = NEW.tag_id;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_image_tag_score_trigger
    AFTER INSERT OR UPDATE OR DELETE ON user_tag_votes
    FOR EACH ROW
EXECUTE FUNCTION update_image_tag_score();
