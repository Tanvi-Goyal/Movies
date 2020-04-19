package com.tmovies.interfaces;

import com.tmovies.model.Movie;
import com.tmovies.model.MovieResponse;

public interface OnGetMoviesResponseCallback {
    void onMovieResponseSuccess(MovieResponse movieResponse);
    void onError();
}