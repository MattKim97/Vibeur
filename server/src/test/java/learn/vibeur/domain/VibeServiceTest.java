package learn.vibeur.domain;

import learn.vibeur.TestHelper;
import learn.vibeur.data.VibeJdbcClientRepository;
import learn.vibeur.models.Vibe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class VibeServiceTest {

    @Autowired
    VibeService service;

    @MockBean
    VibeJdbcClientRepository repository;

    @Test
    void findById() {
        Vibe toFind = new Vibe();
        toFind.setVibeId(1);
        toFind.setTitle("Test");

        when(repository.findById(1)).thenReturn(toFind);

        Result<Vibe> expected = new Result<>();
        expected.setPayload(toFind);

        Result<Vibe> actual = service.findById(1);
        assertEquals(expected, actual);

    }

    @Test
    void doesNotFindById(){
        when(repository.findById(1)).thenReturn(null);

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Vibe not found", ResultType.NOT_FOUND);

        Result<Vibe> actual = service.findById(1);
        assertEquals(expected, actual);
    }

    @Test
    void add() {
        Vibe toAdd = new Vibe();
        toAdd.setTitle("Test");
        toAdd.setUser(TestHelper.makeUser());
        toAdd.setMood(TestHelper.makeMood());
        toAdd.setDescription("test");
        toAdd.setImageUrl("test");
        toAdd.setSongUrl("test");
        toAdd.setDateUploaded(LocalDateTime.now());

        Vibe afterCreated = new Vibe();
        afterCreated.setVibeId(1);
        afterCreated.setTitle("Test");
        afterCreated.setUser(TestHelper.makeUser());
        afterCreated.setMood(TestHelper.makeMood());
        afterCreated.setDescription("test");
        afterCreated.setImageUrl("test");
        afterCreated.setSongUrl("test");
        afterCreated.setDateUploaded(LocalDateTime.now());

        when(repository.create(toAdd)).thenReturn(afterCreated);

        Result<Vibe> expected = new Result<>();
        expected.setPayload(afterCreated);

        Result<Vibe> actual = service.add(toAdd);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddEmptyTitle(){
        Vibe toAdd = new Vibe();
        toAdd.setTitle("");
        toAdd.setUser(TestHelper.makeUser());
        toAdd.setMood(TestHelper.makeMood());
        toAdd.setDescription("test");
        toAdd.setImageUrl("test");
        toAdd.setSongUrl("test");
        toAdd.setDateUploaded(LocalDateTime.now());

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Title is required", ResultType.INVALID);

        Result<Vibe> actual = service.add(toAdd);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddEmptyDescription(){
        Vibe toAdd = new Vibe();
        toAdd.setTitle("Test");
        toAdd.setUser(TestHelper.makeUser());
        toAdd.setMood(TestHelper.makeMood());
        toAdd.setDescription("");
        toAdd.setImageUrl("test");
        toAdd.setSongUrl("test");
        toAdd.setDateUploaded(LocalDateTime.now());

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Description is required", ResultType.INVALID);

        Result<Vibe> actual = service.add(toAdd);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddEmptyImageUrl(){
        Vibe toAdd = new Vibe();
        toAdd.setTitle("Test");
        toAdd.setUser(TestHelper.makeUser());
        toAdd.setMood(TestHelper.makeMood());
        toAdd.setDescription("test");
        toAdd.setImageUrl("");
        toAdd.setSongUrl("test");
        toAdd.setDateUploaded(LocalDateTime.now());

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Image URL is required", ResultType.INVALID);

        Result<Vibe> actual = service.add(toAdd);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddEmptySongUrl(){
        Vibe toAdd = new Vibe();
        toAdd.setTitle("Test");
        toAdd.setUser(TestHelper.makeUser());
        toAdd.setMood(TestHelper.makeMood());
        toAdd.setDescription("test");
        toAdd.setImageUrl("test");
        toAdd.setSongUrl("");
        toAdd.setDateUploaded(LocalDateTime.now());

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Song URL is required", ResultType.INVALID);

        Result<Vibe> actual = service.add(toAdd);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddEmptyDateUploaded(){
        Vibe toAdd = new Vibe();
        toAdd.setTitle("Test");
        toAdd.setUser(TestHelper.makeUser());
        toAdd.setMood(TestHelper.makeMood());
        toAdd.setDescription("test");
        toAdd.setImageUrl("test");
        toAdd.setSongUrl("test");
        toAdd.setDateUploaded(null);

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Date Uploaded is required", ResultType.INVALID);

        Result<Vibe> actual = service.add(toAdd);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddEmptyUser(){
        Vibe toAdd = new Vibe();
        toAdd.setTitle("Test");
        toAdd.setUser(null);
        toAdd.setMood(TestHelper.makeMood());
        toAdd.setDescription("test");
        toAdd.setImageUrl("test");
        toAdd.setSongUrl("test");
        toAdd.setDateUploaded(LocalDateTime.now());

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("User is required", ResultType.INVALID);

        Result<Vibe> actual = service.add(toAdd);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddEmptyMood(){
        Vibe toAdd = new Vibe();
        toAdd.setTitle("Test");
        toAdd.setUser(TestHelper.makeUser());
        toAdd.setMood(null);
        toAdd.setDescription("test");
        toAdd.setImageUrl("test");
        toAdd.setSongUrl("test");
        toAdd.setDateUploaded(LocalDateTime.now());

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Mood is required", ResultType.INVALID);

        Result<Vibe> actual = service.add(toAdd);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotAddNullVibe(){
        Vibe toAdd = null;

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Vibe cannot be null", ResultType.INVALID);

        Result<Vibe> actual = service.add(toAdd);
        assertEquals(expected, actual);
    }

    @Test
    void shouldUpdate(){
        Vibe toUpdate = new Vibe();
        toUpdate.setVibeId(1);
        toUpdate.setTitle("Test");
        toUpdate.setUser(TestHelper.makeUser());
        toUpdate.setMood(TestHelper.makeMood());
        toUpdate.setDescription("test");
        toUpdate.setImageUrl("test");
        toUpdate.setSongUrl("test");
        toUpdate.setDateUploaded(LocalDateTime.of(1,1,1,1,1,1));

        when(repository.update(toUpdate)).thenReturn(true);

        Result<Vibe> expected = new Result<>();
        expected.setPayload(toUpdate);

        Result<Vibe> actual = service.update(toUpdate);
        System.out.println(actual);
        System.out.println(expected);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateEmptyTitle(){
        Vibe toUpdate = new Vibe();
        toUpdate.setVibeId(1);
        toUpdate.setTitle("");
        toUpdate.setUser(TestHelper.makeUser());
        toUpdate.setMood(TestHelper.makeMood());
        toUpdate.setDescription("test");
        toUpdate.setImageUrl("test");
        toUpdate.setSongUrl("test");
        toUpdate.setDateUploaded(LocalDateTime.of(1,1,1,1,1,1));

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Title is required", ResultType.INVALID);

        Result<Vibe> actual = service.update(toUpdate);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateEmptyDescription(){
        Vibe toUpdate = new Vibe();
        toUpdate.setVibeId(1);
        toUpdate.setTitle("Test");
        toUpdate.setUser(TestHelper.makeUser());
        toUpdate.setMood(TestHelper.makeMood());
        toUpdate.setDescription("");
        toUpdate.setImageUrl("test");
        toUpdate.setSongUrl("test");
        toUpdate.setDateUploaded(LocalDateTime.of(1,1,1,1,1,1));

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Description is required", ResultType.INVALID);

        Result<Vibe> actual = service.update(toUpdate);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateEmptyImageUrl(){
        Vibe toUpdate = new Vibe();
        toUpdate.setVibeId(1);
        toUpdate.setTitle("Test");
        toUpdate.setUser(TestHelper.makeUser());
        toUpdate.setMood(TestHelper.makeMood());
        toUpdate.setDescription("test");
        toUpdate.setImageUrl("");
        toUpdate.setSongUrl("test");
        toUpdate.setDateUploaded(LocalDateTime.of(1,1,1,1,1,1));

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Image URL is required", ResultType.INVALID);

        Result<Vibe> actual = service.update(toUpdate);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateEmptySongUrl(){
        Vibe toUpdate = new Vibe();
        toUpdate.setVibeId(1);
        toUpdate.setTitle("Test");
        toUpdate.setUser(TestHelper.makeUser());
        toUpdate.setMood(TestHelper.makeMood());
        toUpdate.setDescription("test");
        toUpdate.setImageUrl("test");
        toUpdate.setSongUrl("");
        toUpdate.setDateUploaded(LocalDateTime.of(1,1,1,1,1,1));

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Song URL is required", ResultType.INVALID);

        Result<Vibe> actual = service.update(toUpdate);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotUpdateEmptyDateUploaded(){
        Vibe toUpdate = new Vibe();
        toUpdate.setVibeId(1);
        toUpdate.setTitle("Test");
        toUpdate.setUser(TestHelper.makeUser());
        toUpdate.setMood(TestHelper.makeMood());
        toUpdate.setDescription("test");
        toUpdate.setImageUrl("test");
        toUpdate.setSongUrl("test");
        toUpdate.setDateUploaded(null);

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Date Uploaded is required", ResultType.INVALID);

        Result<Vibe> actual = service.update(toUpdate);
        assertEquals(expected, actual);
    }

    @Test
    void shouldDelete(){
        when(repository.deleteById(1)).thenReturn(true);

        Result<Vibe> expected = new Result<>();
        expected.setPayload(null);

        Result<Vibe> actual = service.deleteById(1);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotDelete(){
        when(repository.deleteById(1)).thenReturn(false);

        Result<Vibe> expected = new Result<>();
        expected.addErrorMessage("Vibe not found", ResultType.NOT_FOUND);

        Result<Vibe> actual = service.deleteById(1);
        assertEquals(expected, actual);
    }





}