package learn.vibeur.data;

import learn.vibeur.mapper.LikeMapper;
import learn.vibeur.models.Like;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LikeJdbcClientRepository implements LikeRepository{

    JdbcClient client;

    public LikeJdbcClientRepository(JdbcClient client) {
        this.client = client;
    }


    @Override
    public List<Like> findAll() {
        final String sql = "select like_id, vibe_id, user_id from `like`";

        return client.sql(sql)
                .query(new LikeMapper())
                .list();
    }

    @Override
    public Like findById(int likeId) {
        final String sql = "select like_id, vibe_id, user_id from `like` where like_id = ?";

        return client.sql(sql)
                .param(likeId)
                .query(new LikeMapper())
                .optional().orElse(null);
    }

    @Override
    public List<Like> findByVibeId(int vibeId) {
        final String sql = "select like_id, vibe_id, user_id from `like` where vibe_id = ?";

        return client.sql(sql)
                .param(vibeId)
                .query(new LikeMapper())
                .list();
    }

    @Override
    public boolean addLike(int vibeId, int userId) {
        final String sql = "INSERT INTO `like` (vibe_id, user_id) VALUES(:vibe_id, :user_id);";

        KeyHolder keyHolder = new GeneratedKeyHolder();


        int rowsAffected = client.sql(sql)
                .param("vibe_id", vibeId)
                .param("user_id", userId)
                .update(keyHolder,"like_id");

        return rowsAffected > 0;
    }

    @Override
    public boolean removeLike(int vibeId, int userId) {
        final String sql = "DELETE FROM `like` WHERE vibe_id = ? AND user_id = ?";

        int rowsAffected = client.sql(sql)
                .param(vibeId)
                .param(userId)
                .update();

        return rowsAffected > 0;
    }

    @Override
    public boolean hasLiked(int vibeId, int userId) {
        final String sql = "SELECT * FROM `like` WHERE vibe_id = ? AND user_id = ?";

        return client.sql(sql)
                .param(vibeId)
                .param(userId)
                .query(new LikeMapper())
                .optional().isPresent();
    }
}
