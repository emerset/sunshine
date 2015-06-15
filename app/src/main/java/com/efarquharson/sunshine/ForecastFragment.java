package com.efarquharson.sunshine;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    private ArrayAdapter<String> mForecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

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

        // new FetchWeatherTask().execute();




        return rootView;


    }

    public class FetchWeatherTask extends AsyncTask<Void, Void, Void> {

        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();
        @Override
        protected Void doInBackground(Void... params) {
            // Copy/Paste from https://gist.github.com/udacityandroid/d6a7bb21904046a91695
            /////////////////////////////////////////////////////////////////////////////////
            // Declare outside try/catch block & close in finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain raw JSON response.
            String forecastJsonStr = null;

            try {
                // Build URL query for OpenWeatherMap
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");

                // Create request to OpenWeatherMap, open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Add a newline for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                // give complete JSON response to string.
                forecastJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            /////////////////////////////////////////////////////////////////////////////////
            // return forecastJsonStr;
            return null;

        } // End of doInBackground
    }


}
