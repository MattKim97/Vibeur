package learn.vibeur.domain;

import learn.vibeur.TestHelper;
import learn.vibeur.data.UserJdbcClientRepository;
import learn.vibeur.models.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {

    @Autowired
    UserService service;

    @MockBean
    UserJdbcClientRepository repository;


    @Nested
    class Create{

        @Test
        void happyPath() {
            User toAdd = TestHelper.makeUser();
            toAdd.setUserId(0);
            User afterCrated = TestHelper.makeUser();
            when(repository.create(toAdd)).thenReturn(afterCrated);

            Result<User> expected = new Result<>();
            expected.setPayload(afterCrated);

            Result<User> actual = service.create(toAdd);
            assertEquals(expected,actual);
        }

        @Test
        void failsWhenUsernameBlank() {
            User toAdd = TestHelper.makeUser();
            toAdd.setUserId(0);
            toAdd.setUsername("");

            Result<User> expected = new Result<>();
            expected.addErrorMessage("Username cannot be blank", ResultType.INVALID);

            Result<User> actual = service.create(toAdd);
            assertEquals(expected,actual);
        }

        @Test
        void failsWhenPasswordBlank() {
            User toAdd = TestHelper.makeUser();
            toAdd.setUserId(0);
            toAdd.setPassword("");

            Result<User> expected = new Result<>();
            expected.addErrorMessage("Password cannot be blank", ResultType.INVALID);

            Result<User> actual = service.create(toAdd);
            assertEquals(expected,actual);
        }

        @Test
        void failsWhenUsernameTaken() {
            User toAdd = TestHelper.makeUser();
            toAdd.setUserId(0);
            when(repository.findByUserName(toAdd.getUsername())).thenReturn(toAdd);

            Result<User> expected = new Result<>();
            expected.addErrorMessage("Username is already taken", ResultType.INVALID);

            Result<User> actual = service.create(toAdd);
            assertEquals(expected,actual);
        }

    }

    @Nested
    public class FindByUsername{
        @Test
        void happyPath(){
            User user = TestHelper.makeUser();
            when(repository.findByUserName(user.getUsername())).thenReturn(user);

            Result<User> expected = new Result<>();
            expected.setPayload(user);

            Result<User> actual = service.findByUsername(user.getUsername());
            assertEquals(actual,expected);
        }

        @Test
        void doesNotFind(){
            String username = "username";
            when(repository.findByUserName(username)).thenReturn(null);

            Result<User> expected = new Result<>();
            expected.addErrorMessage("User not found",ResultType.NOT_FOUND);

            Result<User> actual = service.findByUsername(username);
            assertEquals(actual,expected);
        }
    }

    @Nested
    public class edit{
        @Test
        void happyPath(){
            User user = TestHelper.makeUser();
            when(repository.findById(user.getUserId())).thenReturn(user);
            user.setUserImageUrl("test");
            when(repository.update(user)).thenReturn(true);

            Result<User> expected = new Result<>();
            expected.setPayload(user);

            Result<User> actual = service.update(user);
            assertEquals(actual,expected);
            assertEquals("test", repository.findById(1).getUserImageUrl());
        }

        @Test
        void doesNotFind(){
            User user = TestHelper.makeUser();
            when(repository.findById(user.getUserId())).thenReturn(null);

            Result<User> expected = new Result<>();
            expected.addErrorMessage("User not found",ResultType.NOT_FOUND);

            Result<User> actual = service.update(user);
            assertEquals(actual,expected);
        }
    }
}