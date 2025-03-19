package learn.vibeur.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import learn.vibeur.domain.*;
import learn.vibeur.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/vibe")
public class VibeController {

    private UserService userService;
    private VibeService vibeService;
    private CommentService commentService;
    private LikeService likeService;
    private final SecretSigningKey key;

    public VibeController(UserService userService, VibeService vibeService, CommentService commentService, LikeService likeService, SecretSigningKey key) {
        this.userService = userService;
        this.vibeService = vibeService;
        this.commentService = commentService;
        this.likeService = likeService;
        this.key = key;
    }

    @GetMapping
    public List<Vibe> getAllVibes(){
        return vibeService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable int id){
        Result<Vibe> result = vibeService.findById(id);
        if(result.isSuccess()){
            return ResponseEntity.ok(result.getPayload());
        }else if (result.getResultType() == ResultType.NOT_FOUND) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.badRequest().body(result.getErrorMessages());
        }
    }

    @GetMapping("{id}/comments")
    public ResponseEntity<Object> findCommentsByVibeId (@PathVariable int id){
        Result<List<Comment>> result = commentService.findByVibeId(id);
        if(result.isSuccess()){
            return ResponseEntity.ok(result.getPayload());
        }else if (result.getResultType() == ResultType.NOT_FOUND) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.badRequest().body(result.getErrorMessages());
        }
    }

    @GetMapping("{id}/likes")
    public ResponseEntity<Object> findLikesByVibeId (@PathVariable int id){
        Result<List<Like>> result = likeService.findByVibeId(id);
        if(result.isSuccess()){
            return ResponseEntity.ok(result.getPayload());
        }else if (result.getResultType() == ResultType.NOT_FOUND) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.badRequest().body(result.getErrorMessages());
        }
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Vibe vibe, @RequestHeader Map<String,String> headers){
        Integer userId = getUserIdFromHeaders(headers);

        if(userId == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        User user = new User();
        user.setUserId(userId);
        vibe.setUser(user);

        LocalDateTime now = LocalDateTime.now();

        vibe.setDateUploaded(now);

        Result<Vibe> result = vibeService.add(vibe);

        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Vibe vibe, @RequestHeader Map<String,String> headers){
        if (id != vibe.getVibeId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Result<Vibe> existingVibe = vibeService.findById(id);
        if (existingVibe.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (existingVibe.getPayload().getUser().getUserId() != userId) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        existingVibe.getPayload().setTitle(vibe.getTitle());
        existingVibe.getPayload().setDescription(vibe.getDescription());

        Result<Vibe> result = vibeService.update(existingVibe.getPayload());

        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable int id, @RequestHeader Map<String,String> headers){
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Result<Vibe> existingVibe = vibeService.findById(id);
        if (existingVibe.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (existingVibe.getPayload().getUser().getUserId() != userId) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Result<Vibe> result = vibeService.deleteById(id);

        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Object> addComment(@PathVariable int id, @RequestBody Comment comment, @RequestHeader Map<String,String> headers){
        Integer userId = getUserIdFromHeaders(headers);

        if(userId == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        System.out.println(userId);

        User user = new User();
        user.setUserId(userId);
        comment.setUser(user);
        comment.setVibeId(id);
        comment.setEdited(false);

        LocalDateTime now = LocalDateTime.now();

        comment.setCreatedDate(now);

        Result<Comment> result = commentService.add(comment);

        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/likes")
    public ResponseEntity<Object> addLike(@PathVariable int id, @RequestBody Like like, @RequestHeader Map<String,String> headers){
        Integer userId = getUserIdFromHeaders(headers);

        if(userId == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        like.setUserId(userId);

        like.setVibeId(id);

        Result<Like> result = likeService.add(like);

        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }









    private Integer getUserIdFromHeaders(Map<String,String> headers){
        if(headers.get("authorization") == null){
            return null;
        }
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key.getKey())
                    .build().parseClaimsJws(headers.get("authorization"));

            return (Integer) claims.getBody().get("userId");
        } catch (Exception e){
            return null;
        }
    }

}
