package learn.vibeur.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {
    private int commentId;
    private String comment;
    private LocalDateTime createdDate;
    private boolean isEdited;
    private User user;
    private int vibeId;

    public Comment(int commentId, String comment, LocalDateTime createdDate, boolean isEdited, User user, int vibeId) {
        this.commentId = commentId;
        this.comment = comment;
        this.createdDate = createdDate;
        this.isEdited = isEdited;
        this.user = user;
        this.vibeId = vibeId;
    }

    public Comment() {
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getVibeId() {
        return vibeId;
    }

    public void setVibeId(int vibeId) {
        this.vibeId = vibeId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Comment comment1)) return false;
        return commentId == comment1.commentId && isEdited == comment1.isEdited && vibeId == comment1.vibeId && Objects.equals(comment, comment1.comment) && Objects.equals(createdDate, comment1.createdDate) && Objects.equals(user, comment1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentId, comment, createdDate, isEdited, user, vibeId);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", comment='" + comment + '\'' +
                ", createdDate=" + createdDate +
                ", isEdited=" + isEdited +
                ", user=" + user +
                ", vibeId=" + vibeId +
                '}';
    }
}
