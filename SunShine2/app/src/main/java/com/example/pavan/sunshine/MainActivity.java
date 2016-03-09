package com.example.pavan.sunshine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.e("oncreate", "reached oncreate");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.e("on start", "reached on start");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e("on pause","reached");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.e("on stop","reached on stop");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e("on resume", "reached on resume");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e("on destroy", "reached on destroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
