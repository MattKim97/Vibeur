package learn.vibeur.controller;

import learn.vibeur.domain.CommentService;
import learn.vibeur.domain.Result;
import learn.vibeur.domain.ResultType;
import learn.vibeur.domain.UserService;
import learn.vibeur.models.Comment;
import learn.vibeur.models.Vibe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private UserService userService;
    private CommentService commentService;

    private final SecretSigningKey key;


    public CommentController(UserService userService, CommentService commentService, SecretSigningKey key) {
        this.userService = userService;
        this.commentService = commentService;
        this.key = key;
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable int id){
        Result<Comment> result = commentService.findById(id);
        if(result.isSuccess()){
            return ResponseEntity.ok(result.getPayload());
        }else if (result.getResultType() == ResultType.NOT_FOUND) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.badRequest().body(result.getErrorMessages());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Comment comment) {
        Result<Comment> existingComment = commentService.findById(id);

        if (existingComment.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        if (id != existingComment.getPayload().getCommentId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        existingComment.getPayload().setComment(comment.getComment());


        Result<Comment> result = commentService.update(existingComment.getPayload());
        if (result.isSuccess()) {
            return ResponseEntity.ok(result.getPayload());
        } else {
            return ResponseEntity.badRequest().body(result.getErrorMessages());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        Result<Comment> existingComment = commentService.findById(id);

        if (existingComment.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Result<Comment> result = commentService.deleteById(id);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }
}
