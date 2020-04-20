package com.tmovies.app.interfaces;

import com.tmovies.app.model.MovieResponse;

public interface OnGetMoviesResponseCallback {
    void onMovieResponseSuccess(MovieResponse movieResponse);
    void onError();
}