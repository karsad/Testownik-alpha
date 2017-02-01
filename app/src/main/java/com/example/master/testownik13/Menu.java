package com.example.master.testownik13;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.R.color.black;
import static android.R.color.white;

public class Menu extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private boolean theme, sound, theme_tmp, connected = false;
    MediaPlayer click, soundtrack, wrong;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        get_savedSettings();
        set_theme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        click = MediaPlayer.create(this, R.raw.click);
        click.setVolume(50,50);

        wrong = MediaPlayer.create(this, R.raw.wrong);
        wrong.setVolume(200, 200);

        soundtrack = MediaPlayer.create(this, R.raw.soundtrack);
        soundtrack.setVolume(1, 1);
        soundtrack.setLooping(true);
        ((Music)this.getApplication()).set_soundtrack(soundtrack);



        soundtrack.start();
    if (sound == false) {soundtrack.pause(); }

    }

    public void onClick_exit(View view) {
        if(sound==true){ click.start(); }
        finish();
        System.exit(0);
    }

    public void onClick_author(View view) {
        if(sound==true){ click.start(); }
        Intent intent = new Intent(this, Author.class);
        startActivity(intent);
    }

    public void onClick_settings(View view) {
        if(sound==true){ click.start(); }
        Intent intent = new Intent (this, Settings.class);
        startActivity(intent);
    }

    public void onClick_start(View view) {

        connected = checkInternetConnection();
        if (connected == false) {
            Toast.makeText(getApplicationContext(), "Wymagane jest połączenie z internetem! \n\n Sprawdź połączenie i spróbuj ponownie.", Toast.LENGTH_LONG).show();
            if (sound == true) { wrong.start(); }
        }
        else {
            if (sound == true) { click.start(); }
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }
    }

    public void get_savedSettings(){
        sharedPreferences = getSharedPreferences("com.example.rysiu.testownik10", MODE_PRIVATE);
        theme = sharedPreferences.getBoolean("theme", false);
        sound = sharedPreferences.getBoolean("switch1", true);
    }

    public void set_theme(boolean x) {
        if(x){
            setTheme(R.style.AppTheme_Night);
        }
        else{
            setTheme(R.style.AppTheme);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        theme_tmp=theme;
        get_savedSettings();

        if (sound == true) {
            soundtrack.start();
        }

        if(theme!=theme_tmp) {
            Intent reset = getIntent();
            finish();
            startActivity(reset);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (sound == true) {
//            soundtrack.start();
//        }
//    }


    @Override
    protected void onPause() {
        super.onPause();
        soundtrack.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundtrack.stop();
    }

    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }



}
