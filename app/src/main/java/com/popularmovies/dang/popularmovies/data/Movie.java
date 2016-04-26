package com.popularmovies.dang.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dang on 23-Apr-16.
 */
public class Movie implements Parcelable {

    private String title;
    private String overview;
    private String releasedate;
    private String originaltitle;
    private String poster_path;
    private String id;
    private String popularity;
    private int vote_count;
    private Double vote_averagev;



    public Movie(String title, String overview, String releasedate, String originaltitle, String poster_path, String id, String popularity, int vote_count, Double vote_averagev) {
        this.title = title;
        this.overview = overview;
        this.releasedate = releasedate;
        this.originaltitle = originaltitle;
        this.poster_path = poster_path;
        this.id = id;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.vote_averagev = vote_averagev;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getOriginaltitle() {
        return originaltitle;
    }

    public void setOriginaltitle(String originaltitle) {
        this.originaltitle = originaltitle;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public Double getVote_averagev() {
        return vote_averagev;
    }

    public void setVote_averagev(Double vote_averagev) {
        this.vote_averagev = vote_averagev;
    }

    protected Movie(Parcel in) {
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
