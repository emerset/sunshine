package com.efarquharson.sunshine;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private ArrayAdapter<String> mForecastAdapter;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);

        // create an arraylist of string to represent a static weather forecast list
        ArrayList<String> weatherArrayList = new ArrayList<>();
        weatherArrayList.add("Today - Sunny - 88/63");
        weatherArrayList.add("Tomorrow - Foggy - 70/46");
        weatherArrayList.add("Weds - Cloudy - 72/53");
        weatherArrayList.add("Thurs - Rainy - 64/51");
        weatherArrayList.add("Fri - Foggy - 70/56");
        weatherArrayList.add("Sat - Sunny - 76/68");

        // Create array adapter to populate ListView
        mForecastAdapter = new ArrayAdapter<>(
            // context --> fragment's parent activity
            getActivity(),
            // name of xml layout file
            R.layout.list_item_forecast,
            // id of textview to populate
            R.id.list_item_forecast_textview,
            // arraylist to get data
            weatherArrayList);

        // Bind ListView to ArrayAdapter
        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
        listView.setAdapter(mForecastAdapter);
        return rootView;


    }
}
