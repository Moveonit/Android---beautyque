package moveonit.beautyque.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DANIELE on 14/11/2016.
 */

public class ProvaResponse {
    @SerializedName("prova")
    private String prova;

    public String getProva() {
        return prova;
    }

    public void setProva(String prova) {
        this.prova = prova;
    }
}
