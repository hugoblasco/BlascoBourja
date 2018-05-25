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
import java.lang.reflect.Array;
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


        IntentFilter intentFilter = new IntentFilter(SITE_UPDATE);
        LocalBroadcastManager.getInstance(this).registerReceiver(new SiteUpdate(),intentFilter);
        setContentView(R.layout.activity_main);

        GetSiteService.startActionGetSite(MainActivity.this);

        rv = findViewById(R.id.rv_site);
        rv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        SiteAdapter sa = new SiteAdapter(getSiteFromFile());
        rv.setAdapter(sa);

        // faire un bouton ou quand on clic dessus ça affiche une boite de dialogue ou on rentre le nom du site qu'on veut
        //examiner et ça nous envoie sur la deuxieme activity avec les details du hack

        /*btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), getString(R.string.msg), Toast.LENGTH_LONG).show();
                //notification_test(); //fait crash l'appli
                secondeActivity();
            }
        });*/


    }

    public void notification_test() {
        NotificationCompat.Builder notif = new NotificationCompat.Builder(MainActivity.this);
        notif.setContentText("bonjour");
        notif.setSmallIcon(R.drawable.ic_launcher_foreground);
        notif.setContentTitle("Notif");
        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1, notif.build());
    }

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
        public void onBindViewHolder(SiteHolder holder, final int position) {

            try {

                //Toast.makeText(getApplicationContext(), dodo, Toast.LENGTH_LONG).show();
                holder.name.setText(site.getJSONObject(position).getString("Title"));
                holder.date.setText(site.getJSONObject(position).getString("BreachDate"));
                holder.btn_info.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {

                        try {
                            Intent intent = new Intent(MainActivity.this, SecondeActivity.class);
                            intent.putExtra("title", site.getJSONObject(position).getString("Title"));
                            String donnees = "";
                            JSONArray data = site.getJSONObject(position).getJSONArray("DataClasses");
                            for (int i=0; i<data.length(); i++) {
                                donnees = donnees  + data.get(i)+ "\n";
                            }
                            intent.putExtra("donnees", donnees);
                            intent.putExtra("access", site.getJSONObject(position).getString("Domain"));
                            intent.putExtra("desc", site.getJSONObject(position).getString("Description"));
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),site.getJSONObject(position).getString("Title") , Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
            public Button btn_info;
            public TextView date;

            public SiteHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.rv_site_element_name);
                date = itemView.findViewById(R.id.rv_site_element_date);
                btn_info = itemView.findViewById(R.id.rv_site_element_button);
            }
        }

        public void setNewSite(JSONArray updated) {
            site = updated;
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
