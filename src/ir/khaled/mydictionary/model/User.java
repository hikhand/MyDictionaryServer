package ir.khaled.mydictionary.model;

import ir.khaled.mydictionary.model_requests.UserLogin;
import ir.khaled.mydictionary.model_requests.UserRegister;

/**
 * Created by khaled.bakhtiari on 5/2/2014.
 */
public class User {
    public int id;
    public int userId;
    public String firstName;
    public String lastName;
    public String nickName;
    public String displayName;
    public String picture;
    public Biography biography;
    public Device device;


    public static Response register(UserRegister userRegister) {
        return new Response();
    }

    public static Response login(UserLogin userLogin) {
        return new Response();
    }
}