package moveonit.beautyque.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DANIELE on 14/11/2016.
 */

public class TokenResponse {
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
