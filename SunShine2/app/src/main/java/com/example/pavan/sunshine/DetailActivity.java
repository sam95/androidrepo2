package com.example.pavan.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {
    private ShareActionProvider sap;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem mi = menu.findItem(R.id.shareid);

        sap = (ShareActionProvider) MenuItemCompat.getActionProvider(mi);
        Intent iu = new Intent(Intent.ACTION_SEND);
        TextView tv = (TextView) findViewById(R.id.my_weat);
        data = tv.getText().toString();
        iu.setType("text/plain");
        Log.e("hello", data);
        iu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        iu.putExtra(Intent.EXTRA_TEXT, data + " #Sunshineapp");
        if(sap!=null) {
            Log.e("uclicked", "share button");
            sap.setShareIntent(iu);
            //startActivity(iu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

                Intent iu = new Intent(this,SettingsActivity.class);
                startActivity(iu);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
