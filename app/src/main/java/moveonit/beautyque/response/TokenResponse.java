package moveonit.beautyque.response;

import com.google.gson.annotations.SerializedName;

import moveonit.beautyque.model.Meta;
import moveonit.beautyque.model.User;

/**
 * Created by DANIELE on 14/11/2016.
 */
//UserResponse

public class TokenResponse {
    @SerializedName("meta")
    private Meta meta;

    @SerializedName("data")
    private User user;

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getToken() { return meta.getToken(); }

    public void setToken(String token) { meta.setToken(token); }
}

