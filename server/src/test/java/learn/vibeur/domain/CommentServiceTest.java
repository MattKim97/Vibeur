package learn.vibeur.domain;

import learn.vibeur.TestHelper;
import learn.vibeur.data.CommentJdbcClientRepository;
import learn.vibeur.models.Comment;
import learn.vibeur.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)

class CommentServiceTest {


    @Autowired
    CommentService service;

    @MockBean
    CommentJdbcClientRepository repository;

    @Test
    void shouldFindById() {
        Comment comment = new Comment();
        comment.setUser(TestHelper.makeUser());
        comment.setVibeId(1);
        comment.setComment("This is a test comment.");
        comment.setCommentId(1);
        when(repository.findById(1)).thenReturn(comment);

        Result<Comment> expected = new Result<>();
        expected.setPayload(comment);

        Result<Comment> actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindComment() {
        when(repository.findById(99)).thenReturn(null);

        Result<Comment> expected = new Result<>();
        expected.addErrorMessage("Comment not found", ResultType.NOT_FOUND);

        Result<Comment> actual = service.findById(99);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindCommentByVibeId() {
        when(repository.findByVibeId(99)).thenReturn(null);

        Result<List<Comment>> expected = new Result<>();
        expected.addErrorMessage("Comments not found", ResultType.NOT_FOUND);

        Result<List<Comment>> actual = service.findByVibeId(99);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByVibeId() {
        Comment comment = new Comment();
        comment.setUser(TestHelper.makeUser());
        comment.setVibeId(1);
        comment.setComment("This is a test comment.");
        comment.setCommentId(1);
        when(repository.findByVibeId(1)).thenReturn(List.of(comment));

        Result<List<Comment>> expected = new Result<>();
        expected.setPayload(List.of(comment));

        Result<List<Comment>> actual = service.findByVibeId(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd() {
        Comment comment = new Comment();
        comment.setUser(TestHelper.makeUser());
        comment.setVibeId(1);
        comment.setComment("This is a test comment.");
        comment.setCommentId(0);
        when(repository.create(comment)).thenReturn(comment);

        Result<Comment> expected = new Result<>();
        expected.setPayload(comment);

        Result<Comment> actual = service.add(comment);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddEmptyComment() {
        Comment comment = new Comment();
        comment.setUser(TestHelper.makeUser());
        comment.setVibeId(1);
        comment.setComment("");
        comment.setCommentId(0);

        Result<Comment> expected = new Result<>();
        expected.addErrorMessage("Comment must have content", ResultType.INVALID);

        Result<Comment> actual = service.add(comment);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddCommentWithInvalidVibeId() {
        Comment comment = new Comment();
        comment.setUser(TestHelper.makeUser());
        comment.setVibeId(0);
        comment.setComment("This is a test comment.");
        comment.setCommentId(0);

        Result<Comment> expected = new Result<>();
        expected.addErrorMessage("Comment must have a vibe ID", ResultType.INVALID);

        Result<Comment> actual = service.add(comment);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddCommentWithInvalidUserId() {
        Comment comment = new Comment();
        User user = TestHelper.makeUser();
        user.setUserId(99);
        comment.setUser(user);
        comment.setVibeId(1);
        comment.setComment("This is a test comment.");
        comment.setCommentId(0);

        Result<Comment> expected = new Result<>();
        expected.addErrorMessage("User does not exist", ResultType.INVALID);

        Result<Comment> actual = service.add(comment);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddCommentWithInvalidCommentId() {
        Comment comment = new Comment();
        comment.setUser(TestHelper.makeUser());
        comment.setVibeId(1);
        comment.setComment("This is a test comment.");
        comment.setCommentId(1);

        Result<Comment> expected = new Result<>();
        expected.addErrorMessage("Comment ID cannot be set for `add` operation", ResultType.INVALID);

        Result<Comment> actual = service.add(comment);
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdate() {
        Comment comment = new Comment();
        comment.setUser(TestHelper.makeUser());
        comment.setVibeId(1);
        comment.setComment("This is a test comment.");
        comment.setCommentId(1);
        when(repository.update(comment)).thenReturn(true);

        Result<Comment> expected = new Result<>();
        expected.setPayload(comment);

        Result<Comment> actual = service.update(comment);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateComment() {
        Comment comment = new Comment();
        comment.setUser(TestHelper.makeUser());
        comment.setVibeId(1);
        comment.setComment("This is a test comment.");
        comment.setCommentId(0);
        when(repository.update(comment)).thenReturn(false);

        Result<Comment> expected = new Result<>();
        expected.addErrorMessage("Comment ID must be set for `update` operation", ResultType.NOT_FOUND);

        Result<Comment> actual = service.update(comment);
        assertEquals(expected, actual);
    }

    @Test
    void shouldDelete(){
        Comment comment = new Comment();
        comment.setUser(TestHelper.makeUser());
        comment.setVibeId(1);
        comment.setComment("This is a test comment.");
        comment.setCommentId(1);
        when(repository.deleteById(1)).thenReturn(true);

        Result<Comment> expected = new Result<>();

        Result<Comment> actual = service.deleteById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotDelete(){
        when(repository.deleteById(99)).thenReturn(false);

        Result<Comment> expected = new Result<>();
        expected.addErrorMessage("Comment not found", ResultType.NOT_FOUND);

        Result<Comment> actual = service.deleteById(99);
        assertEquals(expected, actual);
    }


}