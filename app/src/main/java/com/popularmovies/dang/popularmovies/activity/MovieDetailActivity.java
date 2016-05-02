package com.popularmovies.dang.popularmovies.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;

import com.popularmovies.dang.popularmovies.R;
import com.popularmovies.dang.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import static com.popularmovies.dang.popularmovies.R.color.colorPrimary;

public class MovieDetailActivity extends AppCompatActivity {
    private final static String LOG_TAG = MovieDetailActivity.class.getSimpleName();
    private ImageView posterImageView;
    private Movie movie;
    boolean isShow = false;
    private  CollapsingToolbarLayout collapsingToolbarLayout;
    private  AppBarLayout appBarLayout;
    private  Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_collapsing);

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    showToolbar();
                } else if(isShow) {
                    hideToolbar();
                }
            }
        });
        movie = (Movie) getIntent().getSerializableExtra("movie");
        collapsingToolbarLayout.setTitle(movie.getTitle());
        String imagePath = Movie.parentPosterPath + movie.getPoster_path();
        Log.d(LOG_TAG, imagePath);
        posterImageView = (ImageView) findViewById(R.id.movie_poster_detail);
        Picasso.with(this).load(imagePath).into(posterImageView);
        hideToolbar();
    }

    private void showToolbar()
    {
        collapsingToolbarLayout.setTitle(movie.getTitle());
        isShow = true;
        collapsingToolbarLayout.setBackgroundResource(colorPrimary);
        toolbar.setBackgroundResource(colorPrimary);
    }
    private void hideToolbar()
    {
        collapsingToolbarLayout.setTitle(movie.getTitle());
        //collapsingToolbarLayout.setBackgroundResource(android.R.color.transparent);
        collapsingToolbarLayout.setBackgroundResource(android.R.color.transparent);
        toolbar.setBackgroundResource(android.R.color.transparent);
        isShow = false;
    }

}
