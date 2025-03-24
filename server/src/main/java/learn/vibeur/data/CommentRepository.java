package learn.vibeur.data;

import learn.vibeur.models.Comment;

import java.util.List;

public interface CommentRepository {

    public Comment findById(int commentId);

    public List<Comment> findByVibeId(int vibeId);

    public Comment create(Comment comment);

    public boolean update(Comment comment);

    public boolean deleteById(int comment);
}
