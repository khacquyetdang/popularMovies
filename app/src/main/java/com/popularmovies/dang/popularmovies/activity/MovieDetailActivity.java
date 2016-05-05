package com.popularmovies.dang.popularmovies.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.popularmovies.dang.popularmovies.R;
import com.popularmovies.dang.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import static com.popularmovies.dang.popularmovies.R.color.colorPrimary;

public class MovieDetailActivity extends AppCompatActivity {
    private final static String LOG_TAG = MovieDetailActivity.class.getSimpleName();
    private Movie movie;
    boolean isShow = false;
    private  CollapsingToolbarLayout collapsingToolbarLayout;
    private  AppBarLayout appBarLayout;
    private  Toolbar toolbar;
    private TextView appBarTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_collapsing);

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        movie = (Movie) getIntent().getSerializableExtra("movie");
        collapsingToolbarLayout.setTitle(movie.getTitle());
    }
}
