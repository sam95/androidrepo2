package com.example.pavan.sunshine;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ArrayAdapter<String> myadpt;



    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            FetchTask exec = new FetchTask();
            /*
            SharedPreferences prefs = getActivity().getSharedPreferences(
                    "pref_general.xml", Context.MODE_PRIVATE);*/
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String picode="";
            picode = prefs.getString(getString(R.string.pref_location_key),getString(R.string.myblore));
            Log.e("pincode",picode);
            exec.execute(picode);

            return true;
        }
        if(id == R.id.action_settings){
            Intent iu = new Intent(this.getContext(),SettingsActivity.class);
            startActivity(iu);

        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        container = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        ArrayList<String> myarray = new ArrayList<>();
        myarray.add("Today-Sunny-88/63");
        myarray.add("Mon-Sunny-88/63");
        myarray.add("Tue-Sunny-88/63");
        myarray.add("wed-Sunny-88/63");
        myarray.add("Thurs-Sunny-88/63");
        myarray.add("fri-Sunny-88/63");
        myarray.add("sat-Sunny-88/63");

        myadpt = new ArrayAdapter<>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, myarray);
        ListView lv = (ListView) container.findViewById(R.id.listview_forcast);
        lv.setAdapter(myadpt);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),myadpt.getItem(position),Toast.LENGTH_SHORT).show();
                Intent iu = new Intent(view.getContext(),DetailActivity.class);
                iu.putExtra("data",myadpt.getItem(position));
                startActivity(iu);
            }
        });

        return container;
    }



    public class FetchTask extends AsyncTask<String, Void, String[]> {

        // These two need to be declared outside the try/catch
// so that they can be closed in the finally block.

        String[] str = new String[7];
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(String[] strings) {
            if (strings != null) {
                myadpt.clear();
                myadpt.addAll(strings);
            }
        }

        @Override
        protected String[] doInBackground(String... params) {
            Uri.Builder builder = new Uri.Builder();
            String postcode = params[0]+ ",india";
            //http://api.openweathermap.org/data/2.5/forecast/daily?zip=560040,india&mode=json&units=metric&cnt=7&appid=41a404f10522ec4c2eea0dbeb272836f
            builder.scheme("http").authority("api.openweathermap.org").appendPath("data").appendPath("2.5").appendPath("forecast").appendPath("daily").appendQueryParameter("zip", postcode)
                    .appendQueryParameter("type", "like").appendQueryParameter("mode", "json").appendQueryParameter("units", "metric")
                    .appendQueryParameter("cnt", "7").appendQueryParameter("appid","41a404f10522ec4c2eea0dbeb272836f");

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            Log.e("the url ",builder.toString());
            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            try

            {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL(builder.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    forecastJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    forecastJsonStr = null;
                }
                forecastJsonStr = buffer.toString();
                //Log.e("the reply", forecastJsonStr);
                parseths(forecastJsonStr);
                /*for(String si : str){
                    Log.e("the day  ",si);
                }*/

                return str;

            }

            catch(
                    IOException e
                    )

            {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                forecastJsonStr = null;
            } catch (JSONException e) {
                e.printStackTrace();
            } finally

            {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }
            }

            return null;
        }

        private void parseths(String forecastJsonStr) throws JSONException {

            //int day = calendar.get(Calendar.DATE);
            //Date date;// calendar.getTime();
            //Date date = calendar.getTime();

            for(int i=0;i<7;i++){
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
                Date date = calendar.getTime();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, i);
                date = calendar.getTime();
                String mys = date.toString();
                mys = mys.substring(0, 10);
                StringBuilder myName = new StringBuilder(mys);
                myName.setCharAt(3, ',');
                mys = myName.toString();
                String desc = getDesc(forecastJsonStr,i);
                 mys = mys + desc;
                str[i] = mys;
            }

        }
        //max = arr.getJSONObject(dayIndex).getJSONObject("temp").getDouble("max");
        //min = arr.getJSONObject(dayIndex).getJSONObject("temp").getDouble("min");

        private String getDesc(String forecastJsonStr, int dayIndex) throws JSONException {
            JSONObject obj = new JSONObject(forecastJsonStr);
            JSONArray arr = obj.getJSONArray("list");
            JSONArray s = arr.getJSONObject(dayIndex).getJSONArray("weather");

            String post_id = " - " +s.getJSONObject(0).getString("main");
            int min =(int) arr.getJSONObject(dayIndex).getJSONObject("temp").getDouble("min");

            post_id = post_id +" - "+ (int)arr.getJSONObject(dayIndex).getJSONObject("temp").getDouble("max")+'/' +min;

            return post_id;
        }


    }
}