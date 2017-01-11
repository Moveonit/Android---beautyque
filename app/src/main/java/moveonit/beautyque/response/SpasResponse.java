package moveonit.beautyque.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import moveonit.beautyque.model.Spa;

/**
 * Created by DANIELE on 04/01/2017.
 */

public class SpasResponse {
    @SerializedName("data")
    @Expose
    private List<Spa> data = null;

    public List<Spa> getData() {
        return data;
    }

    public void setData(List<Spa> data) {
        this.data = data;
    }
}
