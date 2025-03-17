package learn.vibeur.models;

public class Like {
    int likeId;
    int vibeId;
    int userId;

    public Like(int likeId, int vibeId, int userId) {
        this.likeId = likeId;
        this.vibeId = vibeId;
        this.userId = userId;
    }

    public Like() {
    }

    public int getLikeId() {
        return likeId;
    }

    public void setLikeId(int likeId) {
        this.likeId = likeId;
    }

    public int getVibeId() {
        return vibeId;
    }

    public void setVibeId(int vibeId) {
        this.vibeId = vibeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Like like)) return false;
        return likeId == like.likeId && vibeId == like.vibeId && userId == like.userId;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(likeId) + Integer.hashCode(vibeId) + Integer.hashCode(userId);
    }

    @Override
    public String toString() {
        return "Like{" +
                "likeId=" + likeId +
                ", vibeId=" + vibeId +
                ", userId=" + userId +
                '}';
    }
}
