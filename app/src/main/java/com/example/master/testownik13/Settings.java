package com.example.master.testownik13;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class Settings extends AppCompatActivity {


    MediaPlayer click, soundtrack;
    private boolean switch1, switch2, theme, sound;
    private int list1, list2;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        get_savedSettings();

        click = MediaPlayer.create(this, R.raw.click);
        click.setVolume(70,70);

        soundtrack = ((Music)this.getApplication()).get_soundtrack();
        if (sound == true) {
            soundtrack.start();
        }

        set_theme(theme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        set_spinner();
        set_switch();
        set_text();
        set_listener();
    }

    private void set_theme(boolean x) {
        if(x){
            setTheme(R.style.AppTheme_Night);
        }
        else{
            setTheme(R.style.AppTheme);
        }
    }

    private void set_text() {
        TextView textview1 = (TextView) findViewById(R.id.settings_textSounds);
        TextView textview2 = (TextView) findViewById(R.id.settings_textNightMode);
        String nightMode_off = getText(R.string.settings_nightMode_off).toString();
        String nightMode_on = getText(R.string.settings_nightMode_on).toString();
        String sounds_on = getText(R.string.settings_sounds_on).toString();
        String sounds_off = getText(R.string.settings_sounds_off).toString();

        if (switch1) {
            textview1.setText(sounds_on);
        } else {
            textview1.setText(sounds_off);
        }

        if (switch2) {
            textview2.setText(nightMode_on);
            theme = true;
        } else {
            textview2.setText(nightMode_off);
            theme = false;
        }
    }

    public void get_savedSettings() {
        sharedPreferences = getSharedPreferences("com.example.rysiu.testownik10", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        list1 = sharedPreferences.getInt("list1", 2);
        list2 = sharedPreferences.getInt("list2", 1);
        switch1 = sharedPreferences.getBoolean("switch1", true);
        switch2 = sharedPreferences.getBoolean("switch2", false);
        theme = sharedPreferences.getBoolean("theme", false);
        sound=switch1;

    }

    public void set_spinner() {
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner1.setSelection(list1);
        spinner2.setSelection(list2);
    }

    public void get_spinner() {
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        list1 = spinner1.getSelectedItemPosition();
        list2 = spinner2.getSelectedItemPosition();
    }

    public void set_switch() {
        Switch sw1 = (Switch) findViewById(R.id.switch1);
        Switch sw2 = (Switch) findViewById(R.id.switch2);
        sw1.setChecked(switch1);
        sw2.setChecked(switch2);
    }

    public void get_switch() {
        Switch sw1 = (Switch) findViewById(R.id.switch1);
        Switch sw2 = (Switch) findViewById(R.id.switch2);
        switch1 = sw1.isChecked();
        switch2 = sw2.isChecked();
    }

    public void onClick_saveSettings(View view) {
        get_switch();
        get_spinner();
        if(switch1==true){ click.start(); }
        sharedPreferences = getSharedPreferences("com.example.rysiu.testownik10", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("switch1", switch1);
        editor.putBoolean("switch2", switch2);
        editor.putInt("list1", list1);
        editor.putInt("list2", list2);
        editor.putBoolean("theme", theme);
        editor.commit();
        finish();
    }

    public void onClick_switch(View view) {
        get_switch();
        set_text();
    }

    public void set_listener() {
        Switch sw1 = (Switch) findViewById(R.id.switch1);
        Switch sw2 = (Switch) findViewById(R.id.switch2);

        CompoundButton.OnCheckedChangeListener switch_listener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                get_switch();
                set_text();
            }
        };

        sw1.setOnCheckedChangeListener(switch_listener);
        sw2.setOnCheckedChangeListener(switch_listener);
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

}
