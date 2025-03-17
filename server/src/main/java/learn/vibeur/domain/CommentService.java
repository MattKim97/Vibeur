package learn.vibeur.domain;

import learn.vibeur.data.CommentRepository;
import learn.vibeur.data.UserRepository;
import learn.vibeur.data.VibeRepository;
import learn.vibeur.models.Comment;
import learn.vibeur.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private CommentRepository repository;
    private UserRepository userRepository;
    private VibeRepository vibeRepository;

    public CommentService(CommentRepository repository, UserRepository userRepository, VibeRepository vibeRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.vibeRepository = vibeRepository;
    }

    public Result<Comment> findById(int id) {
        Result<Comment> result = new Result<>();
        Comment comment = repository.findById(id);

        if (comment == null) {
            result.addErrorMessage("Comment not found", ResultType.NOT_FOUND);
        } else {
            result.setPayload(comment);
        }

        return result;
    }

    public Result<List<Comment>> findByVibeId(int vibeId) {
        Result<List<Comment>> result = new Result<>();

        List<Comment> comments = repository.findByVibeId(vibeId);

        if (comments == null) {
            result.addErrorMessage("Comments not found", ResultType.NOT_FOUND);
        } else {
            result.setPayload(comments);
        }

        return result;

    }

    public Result<Comment> add(Comment comment){
        Result<Comment> result = validate(comment);
        if(!result.isSuccess()){
            return result;
        }

        if(userRepository.findById(comment.getUser().getUserId()) == null){
            result.addErrorMessage("User does not exist");
        }

        if(vibeRepository.findById(comment.getVibeId()) == null){
            result.addErrorMessage("Vibe does not exist");
        }

        if(comment != null && comment.getCommentId() > 0){
            result.addErrorMessage("Comment ID cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }


        if(result.isSuccess()){
        User user = userRepository.findById(comment.getUser().getUserId());


        user.setPassword("*************");
        comment.setUser(user);
            comment = repository.create(comment);
            result.setPayload(comment);
        }

        return result;
    }

    public Result<Comment> update(Comment comment){
        Result<Comment> result = validate(comment);
        if(!result.isSuccess()){
            return result;
        }

        if(comment != null && comment.getCommentId() <= 0){
            result.addErrorMessage("Comment ID must be set for `update` operation", ResultType.INVALID);
            return result;
        }


        User user = userRepository.findById(comment.getUser().getUserId());
        user.setPassword("*************");
        comment.setUser(user);

        if(result.isSuccess()){
            if(repository.update(comment)){
                result.setPayload(comment);
            } else {
                result.addErrorMessage("Comment not found", ResultType.NOT_FOUND);
            }
        }

        return result;
    }

    public Result<Comment> deleteById(int id){
        Result<Comment> result = new Result<>();
        if(!repository.deleteById(id)){
            result.addErrorMessage("Comment not found", ResultType.NOT_FOUND);
        }
        return result;
    }

    private Result<Comment> validate(Comment comment){
        Result<Comment> result = new Result<>();
        if(comment == null){
            result.addErrorMessage("Comment cannot be null", ResultType.INVALID);
            return result;
        }
        if(comment.getUser() == null){
            result.addErrorMessage("Comment must have a user", ResultType.INVALID);
        }
        if(comment.getVibeId() <= 0){
            result.addErrorMessage("Comment must have a vibe ID", ResultType.INVALID);
        }
        if(comment.getComment() == null || comment.getComment().isBlank()){
            result.addErrorMessage("Comment must have content", ResultType.INVALID);
        }
        return result;
    }


}
