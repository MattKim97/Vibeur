package learn.vibeur.data;

import learn.vibeur.TestHelper;
import learn.vibeur.models.User;
import learn.vibeur.models.Vibe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class VibeJdbcClientRepositoryTest {

    @Autowired
    JdbcClient client;

    @Autowired
    VibeJdbcClientRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }

    @Nested
    public class FindByMethods{
        @Test
        void findByVibeId(){
            Vibe vibe = repository.findById(1);
            assertNotNull(vibe);
            assertEquals(1,vibe.getVibeId());
            assertEquals("title1",vibe.getTitle());
            assertEquals("description1",vibe.getDescription());
            assertEquals("imageUrl1",vibe.getImageUrl());
            assertEquals("songUrl1",vibe.getSongUrl());

        }

        @Test
        void shouldNotFindByVibeId(){
            Vibe vibe = repository.findById(100);
            assertNull(vibe);
        }

        @Test
        void findByUserId(){
            Vibe vibe = repository.findByUserId(1).get(0);
            assertNotNull(vibe);
            assertEquals(1,vibe.getVibeId());
            assertEquals("title1",vibe.getTitle());
            assertEquals("description1",vibe.getDescription());
            assertEquals("imageUrl1",vibe.getImageUrl());
            assertEquals("songUrl1",vibe.getSongUrl());
        }

        @Test
        void shouldNotFindByUserId(){
            List<Vibe> vibes = repository.findByUserId(100);
            assertEquals(0,vibes.size());
        }

        @Test
        void shouldFindAll(){
            assertEquals(3,repository.findAll().size());
        }



    }

    @Nested
    public class Create{
        @Test
        void happyPath() {

            User user = TestHelper.makeUser();

            Vibe vibe = new Vibe();
            vibe.setTitle("Test");
            vibe.setDescription("Test");
            vibe.setImageUrl("Test");
            vibe.setSongUrl("Test");
            vibe.setUser(user);
            vibe.setMood(TestHelper.makeMood());
            vibe.setDateUploaded(LocalDateTime.now());

            Vibe createdVibe = repository.create(vibe);

            assertEquals(4,createdVibe.getVibeId());
            assertEquals("Test",createdVibe.getTitle());
            assertEquals("Test",createdVibe.getDescription());
            assertEquals("Test",createdVibe.getImageUrl());
            assertEquals("Test",createdVibe.getSongUrl());
            assertEquals(user.getUserId(),createdVibe.getUser().getUserId());
            assertEquals("testUserName",createdVibe.getUser().getUsername());

            System.out.println(createdVibe);
        }

    }

    @Nested
    public class Update{
        @Test
        void happyPath(){
            Vibe vibe = repository.findById(1);
            vibe.setTitle("Updated Title");
            vibe.setDescription("Updated Description");

            assertTrue(repository.update(vibe));
            Vibe updatedVibe = repository.findById(1);
            assertEquals("Updated Title",updatedVibe.getTitle());
            assertEquals("Updated Description",updatedVibe.getDescription());
        }

    }

    @Nested
    public class Delete{
        @Test
        void happyPath(){
            List<Vibe> vibes = repository.findAll();
            assertEquals(3,vibes.size());
            assertTrue(repository.deleteById(1));
            assertNull(repository.findById(1));
            List<Vibe> after = repository.findAll();
            assertEquals(2,after.size());

        }

        @Test
        void shouldNotDelete(){
            List<Vibe> vibes = repository.findAll();
            assertEquals(3,vibes.size());
            assertFalse(repository.deleteById(100));
            List<Vibe> after = repository.findAll();
            assertEquals(3,after.size());

        }
    }

}