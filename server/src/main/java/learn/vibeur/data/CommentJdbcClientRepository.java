package learn.vibeur.data;

import learn.vibeur.mapper.CommentMapper;
import learn.vibeur.mapper.UserMapper;
import learn.vibeur.models.Comment;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentJdbcClientRepository implements CommentRepository{

    JdbcClient client;

    public CommentJdbcClientRepository(JdbcClient client) {
        this.client = client;
    }

    @Override
    public Comment findById(int commentId) {
       final String sql = "select comment_id, content, dateCreated, isEdited, `comment`.user_id, vibe_id, username, userImageUrl, `password`\n" +
               "from `comment`\n" +
               "join `user` on `comment`.user_id = `user`.user_id\n" +
               "where comment_id = ?";

         return client.sql(sql)
                 .param(commentId)
                 .query(new CommentMapper())
                 .optional().orElse(null);
    }

    @Override
    public List<Comment> findByVibeId(int vibeId) {
        final String sql = "select comment_id, content, dateCreated, isEdited, `comment`.user_id, vibe_id, username, userImageUrl, `password`\n" +
                "from `comment`\n" +
                "join `user` on `comment`.user_id = `user`.user_id\n" +
                "where comment.vibe_id = ?";

        return client.sql(sql)
                .param(vibeId)
                .query(new CommentMapper())
                .list();
    }

    @Override
    public Comment create(Comment comment) {
        final String sql = "INSERT INTO `comment` (content, dateCreated, isEdited, user_id, vibe_id) VALUES(:content, :dateCreated, :isEdited, :user_id, :vibe_id);";

        KeyHolder keyHolder = new GeneratedKeyHolder();


        int rowsAffected = client.sql(sql)
                .param("content", comment.getComment())
                .param("dateCreated", comment.getCreatedDate())
                .param("isEdited", comment.isEdited())
                .param("user_id", comment.getUser().getUserId())
                .param("vibe_id", comment.getVibeId())
                .update(keyHolder,"comment_id");

        if(rowsAffected <= 0) {
            return null;
        }

        comment.setCommentId(keyHolder.getKey().intValue());

        return comment;
    }

    @Override
    public boolean update(Comment comment) {
        final String sql = "UPDATE `comment` SET content = :content, isEdited = true  WHERE comment_id = :comment_id;";

        return client.sql(sql)
                .param("content", comment.getComment())
                .param("comment_id", comment.getCommentId())
                .update() > 0;
    }

    @Override
    public boolean deleteById(int comment) {
        final String sql = "DELETE FROM `comment` WHERE comment_id = ?";

        return client.sql(sql)
                .param(comment)
                .update() > 0;
    }
}
