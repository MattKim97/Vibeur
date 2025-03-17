package learn.vibeur.mapper;

import learn.vibeur.models.Comment;
import learn.vibeur.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<Comment> {
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        RowMapper<User> userMapper = new UserMapper();
        user = userMapper.mapRow(rs, rowNum);

        Comment comment = new Comment();
        comment.setCommentId(rs.getInt("comment_id"));
        comment.setComment(rs.getString("content"));
        comment.setCreatedDate(rs.getTimestamp("dateCreated").toLocalDateTime());
        comment.setEdited(rs.getBoolean("isEdited"));
        comment.setUser(user);
        comment.setVibeId(rs.getInt("vibe_id"));

        return comment;
    }
}
