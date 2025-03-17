package learn.vibeur.data;

import learn.vibeur.mapper.UserMapper;
import learn.vibeur.models.User;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserJdbcClientRepository implements UserRepository {
    JdbcClient client;

    public UserJdbcClientRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    public User findByUserName(String username) {
        final String sql = "SELECT * FROM `user` WHERE username= ?";

        return client.sql(sql)
                .param(username)
                .query(new UserMapper())
                .optional().orElse(null);

    }

    @Override
    public User create(User user) {
        final String sql = "INSERT INTO `user` (username,userImageUrl,password) VALUES(:username, :userImageUrl, :password);";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int rowsAffected = client.sql(sql)
                .param("username", user.getUsername())
                .param("userImageUrl", user.getUserImageUrl())
                .param("password",user.getPassword())
                .update(keyHolder,"user_id");

        if(rowsAffected <= 0) {
            return null;
        }

        user.setUserId(keyHolder.getKey().intValue());
        return user;

    }

    @Override
    public User findById(int userId) {
        final String sql = "SELECT * FROM `user` WHERE user_id= ?";

        return client.sql(sql)
                .param(userId)
                .query(new UserMapper())
                .optional().orElse(null);

    }

    @Override
    public boolean update(User user) {
        final String sql = "UPDATE `user` SET userImageUrl = :imageUrl WHERE user_id = :userId;";

        return client.sql(sql)
                .param("imageUrl", user.getUserImageUrl())
                .param("userId", user.getUserId())
                .update() > 0;
    }
}
