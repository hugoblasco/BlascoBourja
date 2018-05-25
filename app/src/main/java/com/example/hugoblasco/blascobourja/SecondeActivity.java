package com.example.hugoblasco.blascobourja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SecondeActivity extends AppCompatActivity {

    TextView site_name;
    TextView site_name2;
    TextView site_hack;
    TextView site_access;
    TextView site_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconde);

        site_name = findViewById(R.id.tv_nom);
        site_name2 = findViewById(R.id.tv_name);
        site_hack = findViewById(R.id.tv_donnees);
        site_access = findViewById(R.id.tv_access);
        site_description = findViewById(R.id.tv_desc);

        Intent intent = getIntent();

        if (intent != null) {
            String recup_titre = "";
            String recup_data = "";
            String recup_access = "";
            String recup_desc = "";
            if (intent.hasExtra("title")) {
                recup_titre = intent.getStringExtra("title");
                recup_data = intent.getStringExtra("donnees");
                recup_access = intent.getStringExtra("access");
                recup_desc = intent.getStringExtra("desc");
            }
            site_name.setText(recup_titre);
            site_hack.setText(recup_data);
            site_access.setText(recup_access);
            site_description.setText(recup_desc);
        }
        //site_hack = findViewById(R.id.rv_site_element_name);;
        //site_description = findViewById(R.id.rv_site_element_name);;
    }

}
