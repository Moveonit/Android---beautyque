package moveonit.beautyque.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import moveonit.beautyque.model.User;

/**
 * Created by DANIELE on 14/11/2016.
 */

public class UserResponse {
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("data")
    private List<User> results;

    public String getEmail() {
        return email;
    }

    public List<User> getResults() {
        return results;
    }

    public void setResults(List<User> results) {
        this.results = results;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
