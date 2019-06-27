package com.example.tanvi.movies.utils;

import com.example.tanvi.movies.model.Movie;

public interface OnGetMoviesCallback {

    void onSuccess(Movie movie);

    void onError();
}