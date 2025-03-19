package learn.vibeur.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import learn.vibeur.domain.Result;
import learn.vibeur.domain.ResultType;
import learn.vibeur.domain.UserService;
import learn.vibeur.domain.VibeService;
import learn.vibeur.models.User;
import learn.vibeur.models.Vibe;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService service;
    private VibeService vibeService;
    private final SecretSigningKey key;

    public UserController(UserService service, VibeService vibeService, SecretSigningKey key) {
        this.service = service;
        this.vibeService = vibeService;
        this.key = key;
    }

    @GetMapping("/{userId}/myVibes")
    public List<Vibe> getMyVibes(@PathVariable int userId){
        return vibeService.findByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody User user) {
        Result<User> result = service.create(user);

        if (result.isSuccess()) {
            String jwt = Jwts.builder()
                    .claim("username", user.getUsername())
                    .claim("userImageUrl", user.getUserImageUrl())
                    .claim("userId", user.getUserId())
                    .signWith(key.getKey())
                    .compact();

            Map<String, String> output = new HashMap<>();
            output.put("jwt", jwt);
            return new ResponseEntity<>(output, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> update(@PathVariable int userId, @RequestBody User user, @RequestHeader Map<String,String> headers) {


        Integer headerId = getUserIdFromHeaders(headers);

        System.out.println(headers);

        System.out.println("HEADER USER ID" + headerId);

        if(headerId == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if(headerId != userId){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (userId != user.getUserId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<User> result = service.update(user);

        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) {
        Result<User> userResult = service.findByUsername(user.getUsername());

        if (userResult.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(userResult.getErrorMessages(), HttpStatus.NOT_FOUND);
        }


        char[] proposedPassword = user.getPassword().toCharArray();
        char[] existing = userResult.getPayload().getPassword().toCharArray();

        if (BCrypt.verifyer().verify(proposedPassword, existing).verified) {
            String jwt = Jwts.builder()
                    .claim("username", user.getUsername())
                    .claim("userImageUrl", userResult.getPayload().getUserImageUrl())
                    .claim("userId", userResult.getPayload().getUserId())
                    .signWith(key.getKey())
                    .compact();

            Map<String, String> output = new HashMap<>();
            output.put("jwt", jwt);
            return new ResponseEntity<>(output, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(List.of("Username and password do not match"), HttpStatus.FORBIDDEN);
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
