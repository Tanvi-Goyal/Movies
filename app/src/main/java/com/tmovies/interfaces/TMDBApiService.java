package com.tmovies.interfaces;

import com.tmovies.model.Movie;
import com.tmovies.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBApiService {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/latest")
    Call<MovieResponse> getLatestMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/now_playing")
    Call<MovieResponse> getNowPlayingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcomingMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("trending/{media_type}/{time_window}")
    Call<MovieResponse> getTrendingMovies(
            @Path("media_type") String mediaType,
            @Path("time_window") String timeWindow,
            @Query("api_key") String apiKey

    );

    @GET("tv/latest")
    Call<MovieResponse> getTVLatest(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("tv/airing_today")
    Call<MovieResponse> getTVAiringToday(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/on_the_air")
    Call<MovieResponse> getTVOnTheAir(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/popular")
    Call<MovieResponse> getTVPopular(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("tv/top_rated")
    Call<MovieResponse> getTVTopRated(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("search/movie")
    Call<MovieResponse> getSearchResults(
            @Query("api_key") String apiKey,
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<Movie> getDetails(@Path("movie_id") int var,
                           @Query("api_key") String apiKey);
}

