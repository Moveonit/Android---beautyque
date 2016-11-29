package moveonit.beautyque.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DANIELE on 14/11/2016.
 */

public class SignupUser {
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;

    public SignupUser(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
