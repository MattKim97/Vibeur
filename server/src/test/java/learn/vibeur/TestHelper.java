package learn.vibeur;

import learn.vibeur.models.User;

public class TestHelper {

    public static User makeUser(){
        return new User(1,"testUserName","testPassword", null);
    }

}
