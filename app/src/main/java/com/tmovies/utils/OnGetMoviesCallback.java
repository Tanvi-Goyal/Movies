package com.tmovies.utils;

import com.tmovies.model.Movie;

public interface OnGetMoviesCallback {

    void onSuccess(Movie movie);

    void onError();
}