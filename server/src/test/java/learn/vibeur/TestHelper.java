package learn.vibeur;

import learn.vibeur.models.Mood;
import learn.vibeur.models.User;
import learn.vibeur.models.Vibe;

public class TestHelper {

    public static User makeUser(){
        return new User(1,"testUserName","testPassword", null);
    }

    public static Mood makeMood(){
        return new Mood(1,"testMood");
    }

}
