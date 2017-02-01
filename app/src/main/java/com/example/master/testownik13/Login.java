package com.example.master.testownik13;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.Log;
import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    private boolean theme, sound;
    MediaPlayer click, soundtrack;
    final String LOG = "xxx";
    private ArrayList<User> user;
    String url = "http://bezproby.cba.pl/testownik/login.php";
    String username, pass;
    TextView login, password;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        get_savedSettings();
        set_theme(theme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        click = MediaPlayer.create(this, R.raw.click);
        click.setVolume(70,70);

        soundtrack = ((Music)this.getApplication()).get_soundtrack();

        login = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);

        button = (Button) findViewById(R.id.login);
        button.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(sound==true){
            click.start();
        }

        HashMap postData = new HashMap();

        username = login.getText().toString();
        pass = password.getText().toString();


        postData.put("txtUsername", username);
        postData.put("txtPassword", pass);

        final PostResponseAsyncTask taskRead = new PostResponseAsyncTask(Login.this, postData,
                new AsyncResponse() {

                    @Override
                    public void processFinish(String s) {

                        if(s.contains("sukcesik_gwarantowany")){
                            Toast.makeText(Login.this, "Poprawne logowanie\n          SUKCES!", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Login.this, Lista.class);
                            finish();
                            startActivity(intent);
                        }
                        if (s.contains("niepowodzonko_pewne")){
                            Toast.makeText(Login.this, "Niepoprawy login i/lub hasło!", Toast.LENGTH_LONG).show();
                        }

                        System.out.append(s);

                    }

                });
        taskRead.execute(url);
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
