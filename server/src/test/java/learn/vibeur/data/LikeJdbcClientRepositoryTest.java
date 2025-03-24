package learn.vibeur.data;

import learn.vibeur.models.Like;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LikeJdbcClientRepositoryTest {

    @Autowired
    JdbcClient client;

    @Autowired
    LikeJdbcClientRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }

    @Nested
    public class FindByMethods{
        @Test
        void findAll(){
            assertEquals(3,repository.findAll().size());
        }

        @Test
        void findById(){
            assertNotNull(repository.findById(1));
        }

        @Test
        void shouldNotFindById(){
            assertNull(repository.findById(100));
        }

        @Test
        void findByVibeId(){
            assertEquals(1,repository.findByVibeId(1).size());
        }

        @Test
        void shouldNotFindByVibeId(){
            assertEquals(0,repository.findByVibeId(100).size());
        }

    }

    @Test
    void shouldAddLike(){
        List<Like> allLikes = repository.findAll();
        assertEquals(3,allLikes.size());
        assertTrue(repository.addLike(1,1));
        List<Like> allLikesAfter = repository.findAll();
        assertEquals(4,allLikesAfter.size());
    }

    @Test
    void shouldRemoveLike(){
        List<Like> allLikes = repository.findAll();
        assertEquals(3,allLikes.size());
        assertTrue(repository.removeLike(1,3));
        List<Like> allLikesAfter = repository.findAll();
        assertEquals(2,allLikesAfter.size());
    }

    @Test
    void shouldNotRemoveLike(){
        List<Like> allLikes = repository.findAll();
        assertEquals(3,allLikes.size());
        assertFalse(repository.removeLike(1,1));
        List<Like> allLikesAfter = repository.findAll();
        assertEquals(3,allLikesAfter.size());
    }

    @Test
    void hasLiked(){
        assertTrue(repository.hasLiked(2,1));
    }

    @Test
    void hasNotLiked(){
        assertFalse(repository.hasLiked(3,3));
    }

}