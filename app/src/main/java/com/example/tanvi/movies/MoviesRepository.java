package com.example.tanvi.movies;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

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
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                for (int i = 0; i < moviesResponse.getMoviesId().size(); i++) {
                                    final int finalI = i;
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            getMovieDetails(moviesResponse.getMoviesId().get(finalI), callback);

                                        }
                                    }, 10000);

                                }


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
//                                    try {
//                                        Thread.sleep(10000);
//                                    } catch (InterruptedException e) {
//                                        e.printStackTrace();
//                                    }
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
