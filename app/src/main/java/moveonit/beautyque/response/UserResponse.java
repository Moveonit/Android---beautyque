package moveonit.beautyque.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import moveonit.beautyque.model.User;

/**
 * Created by DANIELE on 14/11/2016.
 */

public class UserResponse {

    @SerializedName("data")
    private List<User> user;

    public List<User> getUser() { return user; }

    public void setUser(List<User> user) { this.user = user; }


}
