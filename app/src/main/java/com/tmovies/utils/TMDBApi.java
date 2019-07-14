package com.tmovies.utils;

import com.tmovies.model.Movie;
import com.tmovies.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface TMDbApi {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<Movie> getDetails(@Path("movie_id") int var,
                           @Query("api_key") String apiKey);
}

