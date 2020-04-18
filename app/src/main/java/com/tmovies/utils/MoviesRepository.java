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
import static com.tmovies.constants.ApiConstants.API_KEY;
import static com.tmovies.constants.ApiConstants.BASE_URL;
import static com.tmovies.constants.ApiConstants.LANGUAGE;

public class MoviesRepository {

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

    public void getPopularMovies(final OnGetMoviesCallback callback) {

        api.getPopularMovies(API_KEY, LANGUAGE, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                for(int index = 0; index < moviesResponse.getMoviesId().size();index++) {
                                    getMovieDetails(moviesResponse.getMoviesId().get(index), callback);
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

    public void getLatestMovies(final OnGetMoviesCallback callback) {

        api.getLatestMovies(API_KEY, LANGUAGE)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                for(int index = 0; index < moviesResponse.getMoviesId().size();index++) {
                                    getMovieDetails(moviesResponse.getMoviesId().get(index), callback);
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

    public void getNowPlayingMovies(final OnGetMoviesCallback callback) {

        api.getNowPlayingMovies(API_KEY, LANGUAGE, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                for(int index = 0; index < moviesResponse.getMoviesId().size();index++) {
                                    getMovieDetails(moviesResponse.getMoviesId().get(index), callback);
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

    public void getTopRatedMovies(final OnGetMoviesCallback callback) {

        api.getTopRatedMovies(API_KEY, LANGUAGE, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                for(int index = 0; index < moviesResponse.getMoviesId().size();index++) {
                                    getMovieDetails(moviesResponse.getMoviesId().get(index), callback);
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

    public void getUpcomingMovies(final OnGetMoviesCallback callback) {

        api.getUpcomingMovies(API_KEY, LANGUAGE, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                for(int index = 0; index < moviesResponse.getMoviesId().size();index++) {
                                    getMovieDetails(moviesResponse.getMoviesId().get(index), callback);
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

    public void getTrendingMovies(final OnGetMoviesCallback callback) {

        api.getTrendingMovies(API_KEY, "all", "week")
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                for(int index = 0; index < moviesResponse.getMoviesId().size();index++) {
                                    getMovieDetails(moviesResponse.getMoviesId().get(index), callback);
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

    public void getTVLatest(final OnGetMoviesCallback callback) {

        api.getTVLatest(API_KEY, LANGUAGE)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                for(int index = 0; index < moviesResponse.getMoviesId().size();index++) {
                                    getMovieDetails(moviesResponse.getMoviesId().get(index), callback);
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

    public void getTVAiringToday(final OnGetMoviesCallback callback) {

        api.getTVAiringToday(API_KEY, LANGUAGE, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                for(int index = 0; index < moviesResponse.getMoviesId().size();index++) {
                                    getMovieDetails(moviesResponse.getMoviesId().get(index), callback);
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

    public void getTVOnTheAir(final OnGetMoviesCallback callback) {

        api.getTVOnTheAir(API_KEY, LANGUAGE, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                for(int index = 0; index < moviesResponse.getMoviesId().size();index++) {
                                    getMovieDetails(moviesResponse.getMoviesId().get(index), callback);
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

    public void getTVPopular(final OnGetMoviesCallback callback) {

        api.getTVPopular(API_KEY, LANGUAGE, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                for(int index = 0; index < moviesResponse.getMoviesId().size();index++) {
                                    getMovieDetails(moviesResponse.getMoviesId().get(index), callback);
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

    public void getTVTopRated(final OnGetMoviesCallback callback) {

        api.getTVTopRated(API_KEY, LANGUAGE, 1)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                for(int index = 0; index < moviesResponse.getMoviesId().size();index++) {
                                    getMovieDetails(moviesResponse.getMoviesId().get(index), callback);
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
