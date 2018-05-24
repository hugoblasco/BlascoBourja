package com.example.hugoblasco.blascobourja;

import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    public RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        final TextView tv_hw = (TextView) findViewById(R.id.tv_hello_world);
        //Button btn_hw = (Button) findViewById(R.id.btn_hello_world);
        String now = DateUtils.formatDateTime(getApplicationContext(), (new Date()).getTime(), DateFormat.FULL);

        IntentFilter intentFilter = new IntentFilter(SITE_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new SiteUpdate(),intentFilter);
        setContentView(R.layout.activity_main);

        //tv_hw.setText(now);
        GetSiteService.startActionGetSite(MainActivity.this);

        //TextView test = findViewById(R.id.test);

        rv = findViewById(R.id.rv_site);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        SiteAdapter sa = new SiteAdapter(getSiteFromFile());
        rv.setAdapter(sa);

        // faire un bouton ou quand on clic dessus ça affiche une boite de dialogue ou on rentre le nom du site qu'on veut
        //exminer et ça nous envoie sur la deuxieme activity avec les details du hack
        final DatePickerDialog dpd;
        DatePickerDialog.OnDateSetListener odls = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String date = i2+"/"+i1+"/"+i;
                tv_hw.setText(date);
            }
        };
        dpd = new DatePickerDialog(this, odls, 2018, 05, 11);

        /*btn_hw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), getString(R.string.msg), Toast.LENGTH_LONG).show();
                dpd.show();
                //notification_test(); //fait crash l'appli
                secondeActivity();
            }
        }); */


    }

    public void notification_test() {
        NotificationCompat.Builder notif = new NotificationCompat.Builder(MainActivity.this);
        notif.setContentText("bonjour");
        notif.setSmallIcon(R.drawable.ic_launcher_foreground);
        notif.setContentTitle("Notif");
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, notif.build());
    }

    /*public ArrayList<JSONObject> getSiteFromFile() {
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "site.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            JSONObject obj = new JSONObject(new String(buffer, "UTF-8"));

            obj = obj.getJSONObject("data");

            Iterator<String> keys = obj.keys();

            ArrayList<JSONObject> arrayOfObject = new ArrayList<>();

            while (keys.hasNext()) {
                arrayOfObject.add(obj.getJSONObject(keys.next()));
            }

            return arrayOfObject;


        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }*/

    public JSONArray getSiteFromFile(){
        try {
            InputStream is = new FileInputStream(getCacheDir() + "/" + "site.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            return new JSONArray(new String(buffer, "UTF-8")); // construction du tableau
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public static final String SITE_UPDATE = "com.example.hugoblasco.blascobourja.SITE_UPDATE";

    public class SiteUpdate extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, getIntent().getAction());
            Toast.makeText(getApplicationContext(),"success", Toast.LENGTH_LONG).show();
            ((SiteAdapter) rv.getAdapter()).setNewSite(getSiteFromFile());
        }
    }

    private class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.SiteHolder> {

        private JSONArray site;

        SiteAdapter(JSONArray in) {
            site = in;
        }

        @Override
        public SiteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_site_element, parent, false);
            SiteHolder sh = new SiteHolder(view);
            return sh;
        }

        @Override
        public void onBindViewHolder(SiteHolder holder, int position) {

            try {
                //obj = site.getJSONArray(0);
                //ici est l'erreur : faire en fonction de
                // la taille du tableau avec getItemCount
                //TextView test = findViewById(R.id.test);
                //test.setText(site.getJSONObject(0).getString("Title")); //position ou 0 ?
                holder.name.setText(site.getJSONObject(position).getString("Title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return site.length();
        }

        public class SiteHolder extends RecyclerView.ViewHolder {

            public TextView name;

            public SiteHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.rv_site_element_name);

            }
        }

        public void setNewSite(JSONArray updated) {
            site = updated;
            Log.d("tag", "update");
            notifyDataSetChanged();
        }

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
