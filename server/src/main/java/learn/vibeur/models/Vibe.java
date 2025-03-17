package learn.vibeur.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Vibe {
    private int vibeId;
    private String title;
    private String description;
    private String imageUrl;
    private String songUrl;
    private LocalDateTime dateUploaded;
    private Mood mood;
    private User user;

    public Vibe(int vibeId, String title, String description, String imageUrl, String songUrl, LocalDateTime dateUploaded, Mood mood, User user) {
        this.vibeId = vibeId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.songUrl = songUrl;
        this.dateUploaded = dateUploaded;
        this.mood = mood;
        this.user = user;
    }

    public Vibe() {
    }

    public int getVibeId() {
        return vibeId;
    }

    public void setVibeId(int vibeId) {
        this.vibeId = vibeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public LocalDateTime getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(LocalDateTime dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vibe vibe)) return false;
        return vibeId == vibe.vibeId && Objects.equals(title, vibe.title) && Objects.equals(description, vibe.description) && Objects.equals(imageUrl, vibe.imageUrl) && Objects.equals(songUrl, vibe.songUrl) && Objects.equals(dateUploaded, vibe.dateUploaded) && Objects.equals(mood, vibe.mood) && Objects.equals(user, vibe.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vibeId, title, description, imageUrl, songUrl, dateUploaded, mood, user);
    }

    @Override
    public String toString() {
        return "Vibe{" +
                "vibeId=" + vibeId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", songUrl='" + songUrl + '\'' +
                ", dateUploaded=" + dateUploaded +
                ", mood=" + mood +
                ", user=" + user +
                '}';
    }
}
