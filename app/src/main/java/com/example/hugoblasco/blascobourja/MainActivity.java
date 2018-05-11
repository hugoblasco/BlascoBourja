package com.example.hugoblasco.blascobourja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv_hw = (TextView)findViewById(R.id.tv_hello_world);
        Button btn_hw = (Button)findViewById(R.id.btn_hello_world);
        String now = DateUtils.formatDateTime(getApplicationContext(), (new Date()).getTime(), DateFormat.FULL);

        tv_hw.setText(now);
        btn_hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),getString(R.string.msg),Toast.LENGTH_LONG).show();
            }
        });

        
    }
}
