package learn.vibeur.data;

import learn.vibeur.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.simple.JdbcClient;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserJdbcClientRepositoryTest {

    @Autowired
    JdbcClient client;

    @Autowired
    UserJdbcClientRepository repository;

    @BeforeEach
    void setup() {
        client.sql("call set_known_good_state();").update();
    }


    @Nested
    public class Create{
        @Test
        void happyPath() {

            User user = new User();
            user.setUsername("Test");
            user.setPassword("Test");
            User createdUser = repository.create(user);

            assertEquals(4,createdUser.getUserId());
            assertEquals("Test",createdUser.getUsername());
            assertEquals("Test",createdUser.getPassword());
            assertNull(createdUser.getUserImageUrl());
        }

        }

        @Nested
    public class findMethods{
            @Test
            void findByUserName(){
                User user = repository.findByUserName("user1");
                assertNotNull(user);
                assertEquals(1,user.getUserId());
                assertEquals("user1",user.getUsername());
                assertEquals("password1",user.getPassword());
                assertNull(user.getUserImageUrl());
            }


            @Test
            void findById(){
                User user = repository.findById(1);
                assertNotNull(user);
                assertEquals(1,user.getUserId());
                assertEquals("user1",user.getUsername());
                assertEquals("password1",user.getPassword());
                assertNull(user.getUserImageUrl());
            }

            @Test
            void findByUserNameNotFound(){
                User user = repository.findByUserName("user100");
                assertNull(user);
            }

            @Test
            void findByIdNotFound(){
                User user = repository.findById(100);
                assertNull(user);
            }
        }

        @Nested
        public class Update{
            @Test
            void update(){
                User user = new User();
                user.setUserId(1);
                user.setUserImageUrl("test");
                assertTrue(repository.update(user));
                User actual = repository.findById(1);
                assertNotNull(actual);
                assertEquals(1,actual.getUserId());
                assertEquals("user1",actual.getUsername());
                assertEquals("password1",actual.getPassword());
                assertEquals("test",actual.getUserImageUrl());
            }

            @Test
            void updateNotFound(){
                User user = new User();
                user.setUserId(100);
                user.setUserImageUrl("test");
                assertFalse(repository.update(user));
            }
        }

    }