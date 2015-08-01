package com.efarquharson.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    public ArrayAdapter<String> mForecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events
        setHasOptionsMenu(true);
    }

    // Inflate custom menu
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // String city = getString(R.string.pref_location_default);
        if (id == R.id.action_refresh) {
            updateWeather();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // declare arraylist of strings to represent a weather forecast list
        ArrayList<String> weatherArrayList = new ArrayList<>();

        // Create array adapter to populate ListView
        mForecastAdapter = new ArrayAdapter<>(
            // context --> fragment's parent activity
            getActivity(),
            // name of xml layout file
            R.layout.list_item_forecast,
            // id of textview to populate
            R.id.list_item_forecast_textview,
            // arraylist to get data
            weatherArrayList
        );

        // Bind ListView to ArrayAdapter
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Get weather data for item clicked
                String weatherDetail = mForecastAdapter.getItem(position);
                // Create explicit intent to launch the detail activity
                Intent detailIntent = new Intent(getActivity(), DetailActivity.class);
                // Pass in weather info for day selected
                detailIntent.putExtra(Intent.EXTRA_TEXT, weatherDetail);
                // Launch intent
                startActivity(detailIntent);
            }
        });
        ///// pause

        // new FetchWeatherTask().execute();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        // populate listview
        updateWeather();
    }

    private void updateWeather() {
        FetchWeatherTask weatherTask = new FetchWeatherTask(getActivity(), mForecastAdapter);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String location = preferences.getString(getString(R.string.pref_location_key), getString(R.string.pref_location_default));
        weatherTask.execute(location);
    }
}
