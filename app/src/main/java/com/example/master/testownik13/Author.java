package com.example.master.testownik13;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;



public class Author extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private boolean theme, sound;
    MediaPlayer click, soundtrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        get_savedSettings();
        set_theme(theme);

        click = MediaPlayer.create(this, R.raw.click);
        click.setVolume(70,70);

        soundtrack = ((Music)this.getApplication()).get_soundtrack();
        if (sound == true) {
            soundtrack.start();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author);
    }

    public void onClick_return(View view) {
        if (sound == true) {
            click.start();
        }
        finish();
    }

    public void onClick_contact(View view) {
        if (sound == true) {
            click.start();
        }
        String chooser_title = getString(R.string.chooser_title1);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Testownik - kontakt");
        String email = getString(R.string.email);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
        Intent chooser = Intent.createChooser(intent, chooser_title);
        startActivity(chooser);
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
    protected void onPause() {
        super.onPause();
        soundtrack.pause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (sound == true) {
            soundtrack.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sound == true) {
            soundtrack.start();
        }
    }
}
