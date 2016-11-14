package moveonit.beautyque.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DANIELE on 14/11/2016.
 */

public class Prova {
    @SerializedName("prova")
    private String prova;

    public Prova(String prova) {
        prova = prova;
    }

    public String getProva() {
        return prova;
    }

    public void setProva(String prova) {
        prova = prova;
    }
}
