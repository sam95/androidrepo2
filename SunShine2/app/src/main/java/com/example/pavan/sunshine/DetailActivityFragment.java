package com.example.pavan.sunshine;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.support.v7.widget.ShareActionProvider;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private ShareActionProvider sap;
    String data;
    public DetailActivityFragment() {
        //setHasOptionsMenu(true);
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail, menu);
        /*MenuItem mi = menu.findItem(R.id.shareid);
        sap = (ShareActionProvider) MenuItemCompat.getActionProvider(mi);
        Intent iu = new Intent(Intent.ACTION_SEND);
        iu.setType("text/plain");
        iu.putExtra(Intent.EXTRA_TEXT, data + " #Sunshineapp");
        if(sap!=null) {
            Log.e("uclicked","share button");
            sap.setShareIntent(iu);
            startActivity(iu);
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container = (ViewGroup) inflater.inflate(R.layout.fragment_detail, container, false);
        TextView tv  = (TextView) container.findViewById(R.id.my_weat);
        data = getActivity().getIntent().getExtras().getString("data");
        tv.setText(data);
        return container;
    }
}
