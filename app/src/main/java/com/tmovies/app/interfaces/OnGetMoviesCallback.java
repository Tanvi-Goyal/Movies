package com.tmovies.app.interfaces;

import com.tmovies.app.model.Movie;

public interface OnGetMoviesCallback {

    void onMovieSuccess(Movie movie);
    void onError();
}