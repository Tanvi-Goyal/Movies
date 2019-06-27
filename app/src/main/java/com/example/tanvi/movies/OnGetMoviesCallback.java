package com.example.tanvi.movies;

public interface OnGetMoviesCallback {

    void onSuccess(Movie movie);

    void onError();
}