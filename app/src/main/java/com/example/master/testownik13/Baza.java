package com.example.master.testownik13;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by amiga on 29.01.2017.
 */

public class Baza implements Serializable{



    @SerializedName("ID")
    public int id;

    @SerializedName("NAME")
    public String name;
}
