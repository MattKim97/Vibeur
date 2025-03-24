package learn.vibeur.domain;

import learn.vibeur.data.LikeJdbcClientRepository;
import learn.vibeur.models.Like;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)

class LikeServiceTest {

    @Autowired
    LikeService service;

    @MockBean
    LikeJdbcClientRepository repository;

    @Test
    void shouldfindById(){
        Like like = new Like();
        like.setUserId(1);
        like.setVibeId(1);
        like.setLikeId(1);
        when(repository.findById(1)).thenReturn(like);

        Result<Like> expected = new Result<>();
        expected.setPayload(like);

        Result<Like> actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindLike(){
        when(repository.findById(99)).thenReturn(null);

        Result<Like> expected = new Result<>();
        expected.addErrorMessage("Like not found", ResultType.NOT_FOUND);

        Result<Like> actual = service.findById(99);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByVibeId(){
        Like like = new Like();
        like.setUserId(1);
        like.setVibeId(1);
        like.setLikeId(1);
        when(repository.findByVibeId(1)).thenReturn(List.of(like));

        Result<List<Like>> expected = new Result<>();
        expected.setPayload(List.of(like));

        Result<List<Like>> actual = service.findByVibeId(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindLikes(){
        when(repository.findByVibeId(99)).thenReturn(null);

        Result<List<Like>> expected = new Result<>();
        expected.addErrorMessage("Likes not found", ResultType.NOT_FOUND);

        Result<List<Like>> actual = service.findByVibeId(99);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd(){
        Like like = new Like();
        like.setUserId(1);
        like.setVibeId(1);
        like.setLikeId(0);
        when(repository.addLike(1, 1)).thenReturn(true);

        Result<Like> expected = new Result<>();
        expected.setPayload(like);

        Result<Like> actual = service.add(like);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddLike(){
        Like like = new Like();
        like.setUserId(1);
        like.setVibeId(1);
        like.setLikeId(1);
        when(repository.addLike(1, 1)).thenReturn(false);

        Result<Like> expected = new Result<>();
        expected.addErrorMessage("Like ID cannot be set for `add` operation", ResultType.INVALID);

        Result<Like> actual = service.add(like);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotDuplicateLike(){
        Like like = new Like();
        like.setUserId(1);
        like.setVibeId(1);
        like.setLikeId(0);
        when(repository.hasLiked(1, 1)).thenReturn(true);

        Result<Like> expected = new Result<>();
        expected.addErrorMessage("User has already liked this vibe", ResultType.INVALID);

        Result<Like> actual = service.add(like);
        assertEquals(expected, actual);
    }

    @Test
    void shouldDelete(){
        Like like = new Like();
        like.setUserId(1);
        like.setVibeId(1);
        like.setLikeId(1);
        when(repository.removeLike(1, 1)).thenReturn(true);

        Result<Like> expected = new Result<>();
        expected.setPayload(like);

        Result<Like> actual = service.delete(like);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotDeleteLike(){
        Like like = new Like();
        like.setUserId(1);
        like.setVibeId(1);
        like.setLikeId(0);
        when(repository.removeLike(1, 1)).thenReturn(false);

        Result<Like> expected = new Result<>();
        expected.addErrorMessage("Like ID must be set for `delete` operation", ResultType.INVALID);

        Result<Like> actual = service.delete(like);
        assertEquals(expected, actual);
    }
}