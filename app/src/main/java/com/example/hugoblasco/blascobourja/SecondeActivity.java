package com.example.hugoblasco.blascobourja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondeActivity extends AppCompatActivity {

    TextView site_name;
    TextView site_date;
    TextView site_hack;
    TextView site_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seconde);

        site_name = findViewById(R.id.rv_site_element_name);
        site_date = findViewById(R.id.rv_site_element_date);
        //site_hack = findViewById(R.id.rv_site_element_name);;
        //site_description = findViewById(R.id.rv_site_element_name);;
    }

}
