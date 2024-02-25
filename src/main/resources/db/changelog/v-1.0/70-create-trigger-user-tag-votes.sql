CREATE OR REPLACE FUNCTION update_image_tags_score()
RETURNS TRIGGER AS
$$
DECLARE
up_votes INTEGER;
    down_votes INTEGER;
BEGIN

SELECT COUNT(*) INTO up_votes FROM user_tag_votes WHERE type = true;

SELECT COUNT(*) INTO down_votes FROM user_tag_votes WHERE type = false;


UPDATE image_tags
SET score = up_votes - down_votes;

RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TRIGGER update_image_tags_score_trigger
    AFTER INSERT OR UPDATE OR DELETE ON user_tag_votes
    FOR EACH STATEMENT
    EXECUTE FUNCTION update_image_tags_score();
