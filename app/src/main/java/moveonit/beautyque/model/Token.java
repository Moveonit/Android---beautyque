package moveonit.beautyque.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DANIELE on 14/11/2016.
 */

public class Token {
    @SerializedName("token")
    private String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
