package learn.vibeur.domain;

import learn.vibeur.data.LikeRepository;
import learn.vibeur.models.Like;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    private LikeRepository repository;

    public LikeService(LikeRepository repository) {
        this.repository = repository;
    }

    public Result<Like> findById(int id) {
        Result<Like> result = new Result<>();
        Like like = repository.findById(id);

        if (like == null) {
            result.addErrorMessage("Like not found", ResultType.NOT_FOUND);
        } else {
            result.setPayload(like);
        }

        return result;
    }

    public List<Like> findAll() {
        return repository.findAll();
    }

    public Result<List<Like>> findByVibeId(int vibeId) {
        Result<List<Like>> result = new Result<>();

        List<Like> likes = repository.findByVibeId(vibeId);

        if (likes == null) {
            result.addErrorMessage("Likes not found", ResultType.NOT_FOUND);
        } else {
            result.setPayload(likes);
        }

        return result;

    }

    public Result<Like> add(Like like){
        Result<Like> result = validate(like);
        if(!result.isSuccess()){
            return result;
        }

        if(like.getLikeId() > 0){
            result.addErrorMessage("Like ID cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        if(result.isSuccess()){
            boolean liked = repository.addLike(like.getVibeId(), like.getUserId());
            result.setPayload(like);
        }

        return result;
    }

    public Result<Like> delete(Like like){

        Result<Like> result = new Result<>();
        if(like == null){
            result.addErrorMessage("Like cannot be null", ResultType.INVALID);
            return result;
        }

        if(like.getUserId() <= 0){
            result.addErrorMessage("User ID must not be null", ResultType.INVALID);
            return result;
        }

        if(like.getVibeId() <= 0){
            result.addErrorMessage("Vibe ID must not be null", ResultType.INVALID);
            return result;
        }

        if(!result.isSuccess()){
            return result;
        }

        if(result.isSuccess()){
            boolean deleted = repository.removeLike(like.getVibeId(), like.getUserId());
            if(deleted){
                result.setPayload(like);
            } else {
                result.addErrorMessage("Like not found", ResultType.NOT_FOUND);
            }
        }

        return result;
    }

    private Result<Like> validate(Like like){
        Result<Like> result = new Result<>();

        if(like == null){
            result.addErrorMessage("Like cannot be null", ResultType.INVALID);
            return result;
        }

        if(like.getUserId() <= 0){
            result.addErrorMessage("User ID must not be null", ResultType.INVALID);
            return result;
        }

        if(like.getVibeId() <= 0){
            result.addErrorMessage("Vibe ID must not be null", ResultType.INVALID);
            return result;
        }

        if(repository.hasLiked(like.getVibeId(), like.getUserId())){
            result.addErrorMessage("User has already liked this vibe", ResultType.INVALID);
        }


        return result;
    }

}
