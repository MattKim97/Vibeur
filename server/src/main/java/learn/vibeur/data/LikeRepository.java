package learn.vibeur.data;

import learn.vibeur.models.Like;

import java.util.List;

public interface LikeRepository {
    public List<Like> findAll();
    public Like findById(int likeId);
    public List<Like> findByVibeId(int vibeId);

    public boolean addLike(int vibeId, int userId);
    public boolean removeLike(int vibeId, int userId);
    public boolean hasLiked(int vibeId, int userId);
}
