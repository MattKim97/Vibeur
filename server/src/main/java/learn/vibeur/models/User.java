package learn.vibeur.models;


import java.util.Objects;

public class User {


    private int userId;
    private String username;
    private String password;
    private String userImageUrl;

    public User(int userId, String username, String password, String userImageUrl) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.userImageUrl = userImageUrl;
    }

    public User () {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return userId == user.userId && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(userImageUrl, user.userImageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, password, userImageUrl);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userImageUrl='" + userImageUrl + '\'' +
                '}';
    }
}
