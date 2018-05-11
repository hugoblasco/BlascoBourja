package com.example.hugoblasco.blascobourja;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv_hw = (TextView) findViewById(R.id.tv_hello_world);
        Button btn_hw = (Button) findViewById(R.id.btn_hello_world);
        String now = DateUtils.formatDateTime(getApplicationContext(), (new Date()).getTime(), DateFormat.FULL);

        tv_hw.setText(now);

        // faire un bouton ou quand on clic dessus ça affiche une boite de dialogue ou on rentre le nom du site qu'on veut
        //exminer et ça nous envoie sur la deuxieme activity
        final DatePickerDialog dpd;
        DatePickerDialog.OnDateSetListener odls = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = i2+"/"+i1+"/"+i;
                tv_hw.setText(date);
            }
        };
        dpd = new DatePickerDialog(this, odls, 2018, 05, 11);

        btn_hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getString(R.string.msg), Toast.LENGTH_LONG).show();
                dpd.show();
                //notification_test(); fait crash l'appli
                secondeActivity();
            }
        });


    }

    public void notification_test() {
        NotificationCompat.Builder notif = new NotificationCompat.Builder(MainActivity.this);
        notif.setContentText("bonjour");
        notif.setSmallIcon(R.drawable.ic_launcher_foreground);
        notif.setContentTitle("Notif");
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, notif.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toast:
                Toast.makeText(getApplicationContext(), "Toast actionBar", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void secondeActivity() {
        Intent i = new Intent(this, SecondeActivity.class);
        startActivity(i);
    }
}
