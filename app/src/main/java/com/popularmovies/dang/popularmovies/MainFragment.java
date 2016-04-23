package com.popularmovies.dang.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private final static String LOG_TAG = MainFragment.class.getSimpleName();
    private ArrayList<String> moviesentry;
    private ArrayAdapter<String> movielistadaptateur;
    private ListView listView;

    private String themoviedbApiKey;
    private static final String THEMOVIEDB_BASE_URL= "api.themoviedb.org";
    private int currentPage = 0;
    private int maximumPageIndex = 1000;
    private int minimumPageIndex = 1;

    public MainFragment() {
        // constructor
        super();
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovie();
    }

    private void updateMovie() {
        if (themoviedbApiKey == null || themoviedbApiKey.equals(""))
            themoviedbApiKey = getApiKey();
        if (getActivity() != null) {
            currentPage++;
            if (currentPage < maximumPageIndex && currentPage >= minimumPageIndex) {
                new FetchMovieTask().execute();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //@TODO insert your code here to handle
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview_movie);
        moviesentry = new ArrayList<String>();
        movielistadaptateur = new ArrayAdapter<>(getContext(), R.layout.list_item_movie, moviesentry);

        listView.setAdapter(movielistadaptateur);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(getActivity(), moviesentry.get(position), Toast.LENGTH_LONG);
                toast.show();
            }
        });
        return rootView;
    }

    private void updateData(String moviesJson) {
        try {
            moviesentry.clear();
            String[] moviesArray = getMoviesDataFromJson(moviesJson);
            Collections.addAll(moviesentry, moviesArray);
            /*JSONArray foreCastList = jsonObject.getJSONArray("list");
            for (int i = 0; i < foreCastList.length(); i++) {
                moviesentry.add(foreCastList.getString(i));
            }*/
            movielistadaptateur.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getApiKey() {
        String result = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getActivity().getAssets().open("popularMoviesApi.txt")));

            // do reading, usually loop until end of file reading
            result = reader.readLine();
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return result;
    }

    private String fetchMovieData() {
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String movieJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast

            Uri.Builder builder = new Uri.Builder();

            final String PAGE_INDEX_PARAM = "page";
            final String APPID_PARAM = "api_key";

            //http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7
            builder.scheme("http")
                    .authority(THEMOVIEDB_BASE_URL)
                    .appendPath("3")
                    .appendPath("movie")
                    .appendPath("top_rated")
                    .appendQueryParameter(PAGE_INDEX_PARAM, Integer.toString(currentPage))
                    .appendQueryParameter(APPID_PARAM, themoviedbApiKey);

            URL url = new URL(builder.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                // Nothing to do.
                movieJsonStr = null;
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
                movieJsonStr = null;
            }
            movieJsonStr = buffer.toString();
        } catch (FileNotFoundException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            movieJsonStr = null;
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            movieJsonStr = null;
        } finally {
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
        return movieJsonStr;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * <p>
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    private String[] getMoviesDataFromJson(String forecastJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String MOVIE_LIST = "results";
        final String MOVIE_TITLE= "title";
        final String MOVIE_ORIGINAL_TITLE= "original_title";
        final String MOVIE_ORIGINAL_LANGUAGE= "original_language";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_ADULT = "adult";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE= "release_date";
        final String MOVIE_ID = "id";
        final String MOVIE_POPULARITY = "popularity";
        final String MOVIE_VOTE_COUNT = "vote_count";
        final String MOVIE_VOTE_AVERAGE = "vote_average";
        JSONObject moviesResultJson = new JSONObject(forecastJsonStr);
        JSONArray moviesArrayJson = moviesResultJson.getJSONArray(MOVIE_LIST);

        String[] resultStrs = new String[moviesArrayJson.length()];

        for (int i = 0; i < moviesArrayJson.length(); i++) {

            // Get the JSON object representing the day
            JSONObject movieJson = moviesArrayJson.getJSONObject(i);

            // The date/time is returned as a long.  We need to convert that
            // into something human-readable, since most people won't read "1400356800" as
            // "this saturday".

            // description is in a child array called "weather", which is 1 element long.
            String title = movieJson.getString(MOVIE_TITLE);
            String overview = movieJson.getString(MOVIE_OVERVIEW);
            String releasedate = movieJson.getString(MOVIE_RELEASE_DATE);
            String originaltitle = movieJson.getString(MOVIE_ORIGINAL_TITLE);
            String poster_path = movieJson.getString(MOVIE_POSTER_PATH);
            String id = movieJson.getString(MOVIE_ID);
            String popularity = movieJson.getString(MOVIE_POPULARITY);
            int vote_count = movieJson.getInt(MOVIE_VOTE_COUNT);
            Double vote_averagev= movieJson.getDouble(MOVIE_VOTE_AVERAGE);
            resultStrs[i] = title + " - " + overview + " - " + releasedate;
        }

        for (String s : resultStrs) {
            Log.v(LOG_TAG, "Forecast entry: " + s);
        }
        return resultStrs;

    }

    private class FetchMovieTask extends AsyncTask<Void, Void, String> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected String doInBackground(Void... params) {
            String result = fetchMovieData();
            return result;
        }


        @Override
        protected void onPostExecute(String movieJson) {
            super.onPostExecute(movieJson);
            updateData(movieJson);
        }
    }
}
