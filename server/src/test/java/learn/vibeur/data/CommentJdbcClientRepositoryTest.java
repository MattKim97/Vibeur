package learn.vibeur.data;

import learn.vibeur.TestHelper;
import learn.vibeur.models.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CommentJdbcClientRepositoryTest {

    @Autowired
    JdbcClient client;

    @Autowired
    CommentJdbcClientRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }

    @Nested
    public class FindByMethods{
        @Test
        void findByCommentId(){
            Comment comment = repository.findById(1);
            assertNotNull(comment);
            assertEquals(1,comment.getCommentId());
            assertEquals("content1",comment.getComment());
            assertEquals(LocalDateTime.of(2021, 1, 1, 1, 1,1),comment.getCreatedDate());
            assertFalse(comment.isEdited());
            assertEquals(1,comment.getUser().getUserId());
            assertEquals(1,comment.getVibeId());
            assertEquals("user1",comment.getUser().getUsername());
            assertEquals("password1",comment.getUser().getPassword());
        }

        @Test
        void shouldNotFindByCommentId(){
            Comment comment = repository.findById(100);
            assertNull(comment);
        }

        @Test
        void findByVibeId(){
            Comment comment = repository.findByVibeId(1).get(0);
            assertNotNull(comment);
            assertEquals(1,comment.getCommentId());
            assertEquals("content1",comment.getComment());
            assertEquals(LocalDateTime.of(2021, 1, 1, 1, 1,1),comment.getCreatedDate());
            assertFalse(comment.isEdited());
            assertEquals(1,comment.getUser().getUserId());
            assertEquals(1,comment.getVibeId());
            assertEquals("user1",comment.getUser().getUsername());
            assertEquals("password1",comment.getUser().getPassword());
        }

    }


    @Nested
    public class Create{
        @Test
        void happyPath(){
            Comment comment = new Comment();
            comment.setComment("content");
            comment.setCreatedDate(LocalDateTime.of(2021, 1, 1, 1, 1,1));
            comment.setEdited(false);
            comment.setUser(TestHelper.makeUser());
            comment.setVibeId(1);

            Comment createdComment = repository.create(comment);
            assertNotNull(createdComment);
            assertEquals(4,createdComment.getCommentId());
            assertEquals("content",createdComment.getComment());
            assertEquals(LocalDateTime.of(2021, 1, 1, 1, 1,1),createdComment.getCreatedDate());
            assertFalse(createdComment.isEdited());
            assertEquals(1,createdComment.getUser().getUserId());
            assertEquals(1,createdComment.getVibeId());
        }
    }

    @Nested
    public class Update{
        @Test
        void happyPath(){
            Comment comment = new Comment();
            comment.setCommentId(1);
            comment.setComment("updated content");
            assertTrue(repository.update(comment));
            assertEquals("updated content",repository.findById(1).getComment());
            assertTrue(repository.findById(1).isEdited());
        }

        @Test
        void shouldNotUpdate(){
            Comment comment = new Comment();
            comment.setCommentId(100);
            comment.setComment("content");
            assertFalse(repository.update(comment));
        }
    }

    @Nested
    public class Delete{
        @Test
        void happyPath(){
            assertTrue(repository.deleteById(1));
            assertNull(repository.findById(1));
        }

        @Test
        void shouldNotDelete(){
            assertFalse(repository.deleteById(100));
        }
    }


}