package learn.vibeur.mapper;

import learn.vibeur.models.Mood;
import learn.vibeur.models.User;
import learn.vibeur.models.Vibe;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class VibeMapper implements RowMapper<Vibe> {
    @Override
    public Vibe mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        RowMapper<User> userMapper = new UserMapper();
        user = userMapper.mapRow(rs, rowNum);

        Mood mood = new Mood();
        RowMapper<Mood> moodMapper = new MoodMapper();
        mood = moodMapper.mapRow(rs, rowNum);

        Vibe vibe = new Vibe();
        vibe.setVibeId(rs.getInt("vibe_id"));
        vibe.setTitle(rs.getString("title"));
        vibe.setDescription(rs.getString("description"));
        vibe.setImageUrl(rs.getString("imageUrl"));
        vibe.setSongUrl(rs.getString("songUrl"));
        vibe.setDateUploaded(rs.getTimestamp("dateUploaded").toLocalDateTime());
        vibe.setMood(mood);
        vibe.setUser(user);

        return vibe;
    }
}
