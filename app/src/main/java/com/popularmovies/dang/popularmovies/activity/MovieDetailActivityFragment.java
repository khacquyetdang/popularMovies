package com.popularmovies.dang.popularmovies.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popularmovies.dang.popularmovies.R;
import com.popularmovies.dang.popularmovies.data.Movie;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {

    private Movie movie;
    private TextView descriptionView;

    public MovieDetailActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        movie = (Movie) getActivity().getIntent().getSerializableExtra("movie");

        descriptionView = (TextView) getActivity().findViewById(R.id.movie_detail_description);
        descriptionView.setText(movie.getOverview());
    }
}
