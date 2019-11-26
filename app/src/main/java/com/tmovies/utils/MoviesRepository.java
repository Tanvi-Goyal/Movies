package com.tmovies.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.tmovies.model.Movie;
import com.tmovies.model.MovieId;
import com.tmovies.model.MovieResponse;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class MoviesRepository {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    private static final String API_KEY = "f237940c743ded3e0dfd0193a5b6fb5b";

    private static MoviesRepository repository;
    private Timer timer;
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
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {


                                TimerTask task = new TimerTask() {
                                    int index = 0;

                                    @Override
                                    public void run() {
                                        if (index < moviesResponse.getMoviesId().size()) {
                                            try {
                                                getMovieDetails(moviesResponse.getMoviesId().get(index), callback);

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            index++;
                                        } else {
                                            Log.i(TAG, "run: " + "ALL MOVIEs ADDED");
                                            timer.cancel();
                                        }
                                    }
                                };

                                timer = new Timer();
                                timer.scheduleAtFixedRate(task, 0, 200);

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

    private void getMovieDetails(MovieId moviesId, final OnGetMoviesCallback callback) {

        api.getDetails(moviesId.getId(), API_KEY)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.isSuccessful()) {
                            Movie movie = response.body();
                            if (movie != null) {
                                Log.w("TAG", "Retrievd " + movie.getTitle());
                                callback.onSuccess(movie);
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<Movie> call, Throwable t) {
                        callback.onError();
                    }
                });

    }


}
