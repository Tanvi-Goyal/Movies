package com.tmovies.interfaces;

import com.tmovies.model.Movie;

public interface OnGetMoviesCallback {

    void onMovieSuccess(Movie movie);
    void onError();
}