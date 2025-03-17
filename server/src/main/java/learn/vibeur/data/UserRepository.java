package learn.vibeur.data;

import learn.vibeur.models.User;

public interface UserRepository {
    public User findByUserName(String username);

    public User create(User user);

    public User findById(int userId);

    public boolean update(User user);
}
