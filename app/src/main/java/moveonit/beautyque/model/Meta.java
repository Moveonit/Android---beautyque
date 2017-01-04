package moveonit.beautyque.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DANIELE on 03/01/2017.
 */

public class Meta {
    @SerializedName("token")
    public String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}