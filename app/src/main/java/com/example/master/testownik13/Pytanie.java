package com.example.master.testownik13;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amiga on 01.02.2017.
 */

public class Pytanie {

    @SerializedName("ID")
    public int id;

    @SerializedName("question")
    public String pytanie;

    @SerializedName("ansA")
    public String odp1;

    @SerializedName("ansB")
    public String odp2;

    @SerializedName("ansC")
    public String odp3;

    @SerializedName("ansD")
    public String odp4;

    @SerializedName("correct")
    public String poprawna;


}
