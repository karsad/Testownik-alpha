package com.example.master.testownik13;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Lista extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemClickListener {

    SharedPreferences sharedPreferences;
    private boolean theme, sound;
    MediaPlayer click, soundtrack;
    String url = "http://bezproby.cba.pl/testownik/baza.php";
    final String LOG = "ListActivity";
    private ArrayList<Baza> baza;
    private ListView lvLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("onCreate START");
        super.onCreate(savedInstanceState);
        get_savedSettings();
        set_theme(theme);

        click = MediaPlayer.create(this, R.raw.click);
        click.setVolume(70,70);

        soundtrack = ((Music)this.getApplication()).get_soundtrack();


        setContentView(R.layout.activity_lista);

        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(Lista.this, this);
        taskRead.execute(url);

    }

    @Override
    public void processFinish(String s) {
        Log.d(LOG,s);
        baza = new JsonConverter<Baza>().toArrayList(s, Baza.class);

        BindDictionary<Baza> dict = new BindDictionary<Baza>();
        dict.addStringField(R.id.lista_name, new StringExtractor<Baza>() {
            @Override
            public String getStringValue(Baza item, int position) {
                return "" + item.name;
            }
        });

        FunDapter<Baza> adapter = new FunDapter<>(Lista.this, baza, R.layout.lista_adapter, dict);

        lvLista = (ListView)findViewById(R.id.lvLista);
        lvLista.setAdapter(adapter);
        lvLista.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(sound==true){click.start();}
        Baza b = baza.get(position);
        Intent intent = new Intent(Lista.this, Pytania.class);
        intent.putExtra("baza", b);
        startActivity(intent);

    }
}
