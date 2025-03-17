package learn.vibeur.mapper;

import learn.vibeur.models.Like;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeMapper implements RowMapper<Like> {
    @Override
    public Like mapRow(ResultSet rs, int rowNum) throws SQLException {
        Like like = new Like();
        like.setLikeId(rs.getInt("like_id"));
        like.setUserId(rs.getInt("user_id"));
        like.setVibeId(rs.getInt("vibe_id"));

        return like;
    }
}
