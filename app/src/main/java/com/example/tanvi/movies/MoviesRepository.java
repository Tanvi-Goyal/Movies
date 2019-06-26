package com.example.tanvi.movies;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    private static final String API_KEY = "f237940c743ded3e0dfd0193a5b6fb5b";

    private static MoviesRepository repository;

    private TMDbApi api;

    private MoviesRepository(TMDbApi api) {
        this.api = api;
    }

    public static MoviesRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MoviesRepository(retrofit.create(TMDbApi.class));
        }

        return repository;
    }

    public void getMovies(final OnGetMoviesCallback callback) {
        api.getPopularMovies(API_KEY, LANGUAGE, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                //call getDetails and fetch imdb_id for each movie
                                List<Movie> movies = new ArrayList<>();

                                movies = getMovieDetails(moviesResponse.getMoviesId(), movies, callback);
                                callback.onSuccess(movies);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }

    private List<Movie> getMovieDetails(List<MovieId> moviesId, final List<Movie> movies, final OnGetMoviesCallback callback) {

        for (int i = 0; i < moviesId.size(); i++) {
            api.getDetails(moviesId.get(i).getId(), API_KEY)
                    .enqueue(new Callback<Movie>() {
                        @Override
                        public void onResponse(Call<Movie> call, Response<Movie> response) {
                            if (response.isSuccessful()) {
                                Movie movie = response.body();
                                if (movie != null) {
                                    movies.add(movie);
                                }
                            }
                            callback.onSuccess(movies);

                        }

                        @Override
                        public void onFailure(Call<Movie> call, Throwable t) {
                            callback.onError();
                        }
                    });
        }

        return movies;

    }


}
