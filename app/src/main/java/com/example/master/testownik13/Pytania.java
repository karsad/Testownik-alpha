package com.example.master.testownik13;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Pytania extends AppCompatActivity implements View.OnClickListener {

    String baza_name, question, ans1, ans2, ans3, asn4, correct;
    private String[] bufor;
    private Integer id;
    String url = "http://bezproby.cba.pl/testownik/pytania.php?format=json";
    String url2 = "http://bezproby.cba.pl/testownik/liczba_pytan.php?format=json";

    TextView tv_pytanie;
    CheckBox odp1, odp2, odp3, odp4;
    Button bt_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pytania);

        bufor = new String[7];

        Baza baza = (Baza) getIntent().getSerializableExtra("baza");

        tv_pytanie = (TextView) findViewById(R.id.tv_pytanie);
        odp1 = (CheckBox) findViewById(R.id.odp_1);
        odp2 = (CheckBox) findViewById(R.id.odp_2);
        odp3 = (CheckBox) findViewById(R.id.odp_3);
        odp4 = (CheckBox) findViewById(R.id.odp_4);
        bt_next = (Button) findViewById(R.id.bt_next);

        bt_next.setOnClickListener(this);

        if (baza != null) {
            baza_name = baza.name;
        }

        Log.d("tag","Pobieram id!");
        get_liczbapytan(baza_name);
        get_pytanie(baza_name, "1");

    }

    void get_liczbapytan(String db_name) {

        final int liczba = 0;

        HashMap hashMap2 = new HashMap();
        hashMap2.put("txtBaza", db_name);


        PostResponseAsyncTask task2 = new PostResponseAsyncTask(Pytania.this, hashMap2, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                ArrayList<Pytanie> listaPytan2 = new JsonConverter<Pytanie>().toArrayList(s, Pytanie.class);

                for (Pytanie value : listaPytan2) {

                    id = value.id;
                    bufor[5] = Integer.toString(id);
                    bufor[6] = value.pytanie;

                    Log.d("tag",bufor[5]);
                    get_random(id);

                }
            }
        });
        task2.execute(url2);
        Log.d("tag","Zwracam ID: "+bufor[5]);

    }

    private void get_pytanie(String db_name, String id) {

        HashMap hashMap = new HashMap();
        hashMap.put("txtBaza", db_name);
        hashMap.put("txtID", id);


        PostResponseAsyncTask task = new PostResponseAsyncTask(Pytania.this, hashMap, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                ArrayList<Pytanie> listaPytan = new JsonConverter<Pytanie>().toArrayList(s, Pytanie.class);

                for (Pytanie value : listaPytan) {
                    bufor[0] = value.pytanie;
                    bufor[1] = value.odp1;
                    bufor[2] = value.odp2;
                    bufor[3] = value.odp3;
                    bufor[4] = value.odp4;

                    int id = value.id;
                    bufor[5] = Integer.toString(id);


                    tv_pytanie.setText(bufor[0].toString());
                    odp1.setText(bufor[1].toString());
                    odp2.setText(bufor[2].toString());
                    odp3.setText(bufor[3].toString());
                    odp4.setText(bufor[4].toString());

                }

                Log.d("tag", bufor[0].toString());
            }
        });
        task.execute(url);
    }

    void get_random(int num){
        Random random = new Random();
        int n = random.nextInt(num+1);

        String x = Integer.toString(n);
        get_pytanie(baza_name, x);

    }

    @Override
    public void onClick(View view) {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}