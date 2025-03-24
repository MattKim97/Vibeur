package learn.vibeur.data;

import learn.vibeur.mapper.VibeMapper;
import learn.vibeur.models.Vibe;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VibeJdbcClientRepository  implements VibeRepository{

    JdbcClient client;

    public VibeJdbcClientRepository(JdbcClient client) {
        this.client = client;
    }
    @Override
    public List<Vibe> findAll() {
        final String sql = "SELECT vibe_id, title, `description`, imageUrl, songUrl, dateUploaded, vibe.user_id, vibe.mood_id, username, `password`, userImageUrl, mood_name\n" +
                "FROM vibe\n" +
                "JOIN `user` on vibe.user_id = user.user_id\n" +
                "JOIN  mood on vibe.mood_id = mood.mood_id";

        return client.sql(sql)
                .query(new VibeMapper())
                .list();
    }

    @Override
    public Vibe findById(int vibeId) {
        final String sql = "SELECT vibe_id, title, `description`, imageUrl, songUrl, dateUploaded, vibe.user_id, vibe.mood_id, username, `password`, userImageUrl, mood_name\n" +
                "FROM vibe\n" +
                "JOIN `user` on vibe.user_id = user.user_id\n" +
                "JOIN  mood on vibe.mood_id = mood.mood_id\n" +
                "WHERE vibe_id = ?";

        return client.sql(sql)
                .param(vibeId)
                .query(new VibeMapper())
                .optional().orElse(null);
    }


    @Override
    public List<Vibe> findByUserId(int userId) {
        final String sql = "SELECT vibe_id, title, `description`, imageUrl, songUrl, dateUploaded, vibe.user_id, vibe.mood_id, username, `password`, userImageUrl, mood_name\n" +
                "FROM vibe\n" +
                "JOIN `user` on vibe.user_id = user.user_id\n" +
                "JOIN  mood on vibe.mood_id = mood.mood_id\n" +
                "WHERE vibe.user_id = ?";

        return client.sql(sql)
                .param(userId)
                .query(new VibeMapper())
                .list();
    }

    @Override
    public Vibe create(Vibe vibe) {
        final String sql = "INSERT INTO vibe (title, `description`, imageUrl, songUrl, dateUploaded, user_id, mood_id) " +
                "VALUES(:title, :description, :imageUrl, :songUrl, :dateUploaded, :userId, :moodId);";

        KeyHolder keyHolder = new GeneratedKeyHolder();


        int rowsAffected = client.sql(sql)
                .param("title", vibe.getTitle())
                .param("description", vibe.getDescription())
                .param("imageUrl", vibe.getImageUrl())
                .param("songUrl", vibe.getSongUrl())
                .param("dateUploaded", vibe.getDateUploaded())
                .param("userId", vibe.getUser().getUserId())
                .param("moodId", vibe.getMood().getMoodId())
                .update(keyHolder, "vibe_id");

        if(rowsAffected <= 0) {
            return null;
        }

        vibe.setVibeId(keyHolder.getKey().intValue());
        return vibe;
    }


    @Override
    public boolean update(Vibe vibe) {
        final String sql = "UPDATE vibe SET title = :title, `description` = :description WHERE vibe_id = :vibeId;";

        return client.sql(sql)
                .param("title", vibe.getTitle())
                .param("description", vibe.getDescription())
                .param("vibeId", vibe.getVibeId())
                .update() > 0;

    }

    @Override
    public boolean deleteById(int vibeId) {
        final String sql = "DELETE FROM vibe WHERE vibe_id = ?";

        return client.sql(sql)
                .param(vibeId)
                .update() > 0;
    }
}
