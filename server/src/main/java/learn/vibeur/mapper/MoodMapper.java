package learn.vibeur.mapper;

import learn.vibeur.models.Mood;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoodMapper implements RowMapper<Mood> {
    @Override
    public Mood mapRow(ResultSet rs, int rowNum) throws SQLException {
        Mood mood = new Mood();
        mood.setMoodId(rs.getInt("mood_id"));
        mood.setMoodName(rs.getString("mood_name"));
        return mood;
    }
}
