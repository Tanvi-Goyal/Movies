package com.tmovies.utils;

import com.tmovies.model.Movie;
import com.tmovies.model.MovieResponse;

public interface OnGetMoviesCallback {

    void onMovieSuccess(Movie movie);
    void onError();
}