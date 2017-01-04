package moveonit.beautyque.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import moveonit.beautyque.model.SignupUser;

/**
 * Created by DANIELE on 14/11/2016.
 */

public class SignupUserResponse {
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("data")
    private List<SignupUser> results;

    public String getEmail() {
        return email;
    }

    public List<SignupUser> getResults() {
        return results;
    }

    public void setResults(List<SignupUser> results) {
        this.results = results;
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
