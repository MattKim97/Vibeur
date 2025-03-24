package learn.vibeur.domain;

import learn.vibeur.data.UserRepository;
import learn.vibeur.data.VibeRepository;
import learn.vibeur.models.User;
import learn.vibeur.models.Vibe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VibeService {

    public VibeRepository repository;
    public UserRepository userRepository;

    public VibeService(VibeRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Vibe> findAll() {
        return repository.findAll();
    }

    public List<Vibe> findByUserId(int userId) {
        return repository.findByUserId(userId);
    }

    public Result<Vibe> findById(int vibeId) {
        Result<Vibe> result = new Result<>();
        Vibe foundVibe = repository.findById(vibeId);

        if (foundVibe == null) {
            result.addErrorMessage("Vibe not found", ResultType.NOT_FOUND);
        } else {
            result.setPayload(foundVibe);
        }

        return result;
    }

    public Result<Vibe> add(Vibe vibe) {
        Result<Vibe> result = validate(vibe);

        if (!result.isSuccess()) {
            return result;
        }

        if(userRepository.findById(vibe.getUser().getUserId())== null){
            result.addErrorMessage("User does not exist");
        }


        if(vibe != null && vibe.getVibeId() != 0){
            result.addErrorMessage("Vibe ID cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        User user = userRepository.findById(vibe.getUser().getUserId());
        user.setPassword("null");
        vibe.setUser(user);

        if(result.isSuccess()){
            vibe = repository.create(vibe);
            result.setPayload(vibe);
        }

        return result;
    }

    public Result<Vibe> update(Vibe vibe){
        Result<Vibe> result = validate(vibe);

        if(!result.isSuccess()){
            return result;
        }


        if(vibe != null && vibe.getVibeId() <= 0){
            result.addErrorMessage("Vibe ID cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        User user = userRepository.findById(vibe.getUser().getUserId());
        user.setPassword("null");
        vibe.setUser(user);

        if(result.isSuccess()) {
            if (repository.update(vibe)){
                result.setPayload(vibe);
        } else {
                result.addErrorMessage("Vibe not found", ResultType.NOT_FOUND);
            }
        }

        return result;
    }

    public Result<Vibe> deleteById(int vibeId){
        Result<Vibe> result = new Result<>();


        if(!repository.deleteById(vibeId)){
            result.addErrorMessage("Vibe not found", ResultType.NOT_FOUND);
        }

        return result;
    }



    private Result<Vibe> validate(Vibe vibe){
        Result<Vibe> result = new Result<>();

        if(vibe == null){
            result.addErrorMessage("Vibe cannot be null", ResultType.INVALID);
            return result;
        }

        if(vibe.getTitle() == null || vibe.getTitle().isBlank()){
            result.addErrorMessage("Title is required", ResultType.INVALID);
        }

        if(vibe.getDescription() == null || vibe.getDescription().isBlank()){
            result.addErrorMessage("Description is required", ResultType.INVALID);
        }

        if(vibe.getImageUrl() == null || vibe.getImageUrl().isBlank()){
            result.addErrorMessage("Image URL is required", ResultType.INVALID);
        }

        if(vibe.getSongUrl() == null || vibe.getSongUrl().isBlank()){
            result.addErrorMessage("Song URL is required", ResultType.INVALID);
        }

        if(vibe.getDateUploaded() == null){
            result.addErrorMessage("Date Uploaded is required", ResultType.INVALID);
        }

        if(vibe.getUser() ==  null){
            result.addErrorMessage("User is required", ResultType.INVALID);
            return result;
        }

        if(vibe.getUser().getUserId() <= 0){
            result.addErrorMessage("User does not exist", ResultType.INVALID);
        }

        if(vibe.getMood() == null){
            result.addErrorMessage("Mood is required", ResultType.INVALID);
            return result;
        }

        if(vibe.getMood().getMoodId() <= 0){
            result.addErrorMessage("Mood does not exist", ResultType.INVALID);
        }

        return result;
    }

}
