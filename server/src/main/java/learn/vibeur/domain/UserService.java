package learn.vibeur.domain;

import at.favre.lib.crypto.bcrypt.BCrypt;
import learn.vibeur.data.UserRepository;
import learn.vibeur.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final int BCRYPT_COST = 12;

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Result<User> create(User user){
        Result<User> result = new Result<>();

        if(user.getUsername().isEmpty()){
            result.addErrorMessage("Username cannot be blank", ResultType.INVALID);
        }

        if(user.getPassword().isEmpty()){
            result.addErrorMessage("Password cannot be blank", ResultType.INVALID);
        }

        if(repository.findByUserName(user.getUsername()) != null){
            result.addErrorMessage("Username is already taken", ResultType.INVALID);

        }

        if(result.isSuccess()){
            String hashedPassword = BCrypt.withDefaults().hashToString(BCRYPT_COST,user.getPassword().toCharArray());
            user.setPassword(hashedPassword);
            User createdUser = repository.create(user);
            result.setPayload(createdUser);
        }

        return result;
    }

    public Result<User> findByUsername(String userName){
        Result<User> result = new Result<>();
        User foundUser = repository.findByUserName(userName);

        if(foundUser == null){
            result.addErrorMessage("User not found",ResultType.NOT_FOUND);
        } else {
            result.setPayload(foundUser);
        }

        return result;
    }

    public Result<User> findById(int userId){
        Result<User> result = new Result<>();
        User foundUser = repository.findById(userId);

        if(foundUser == null){
            result.addErrorMessage("User not found",ResultType.NOT_FOUND);
        } else {
            result.setPayload(foundUser);
        }

        return result;
    }

    public Result<User> update(User user){
        Result<User> result = new Result<>();

        if(user.getUserImageUrl() != null){
            user.setUserImageUrl(user.getUserImageUrl());
        }

        if(repository.update(user)){
            result.setPayload(user);
        } else {
            result.addErrorMessage("User not found",ResultType.NOT_FOUND);
        }

        return result;
    }

}
