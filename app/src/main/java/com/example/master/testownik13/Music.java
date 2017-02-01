package com.example.master.testownik13;

import android.app.Application;
import android.media.MediaPlayer;

import com.example.master.testownik13.R;

/**
 * Created by amiga on 26.01.2017.
 */

public class Music extends Application {

private MediaPlayer soundtrack;

    public void set_soundtrack(MediaPlayer item){
        soundtrack = item;
    }

    public MediaPlayer get_soundtrack(){
        return soundtrack;
    }
}
