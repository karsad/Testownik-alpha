package com.example.master.testownik13;

import com.google.gson.annotations.SerializedName;

/**
 * Created by amiga on 31.01.2017.
 */

public class User {

    @SerializedName("ID")
    public int id;

    @SerializedName("username")
    public String username;

    @SerializedName("password")
    public String password;

    @SerializedName("status")
    public String status;

}
