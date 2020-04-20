package com.tmovies.app.repository;

import android.support.annotation.NonNull;

import com.tmovies.app.model.Movie;
import com.tmovies.app.model.MovieId;
import com.tmovies.app.model.MovieResponse;
import com.tmovies.app.interfaces.OnGetMoviesCallback;
import com.tmovies.app.interfaces.OnGetMoviesResponseCallback;
import com.tmovies.app.interfaces.TMDBApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.tmovies.app.constants.ApiConstants.API_KEY;
import static com.tmovies.app.constants.ApiConstants.BASE_URL;
import static com.tmovies.app.constants.ApiConstants.LANGUAGE;

public class MoviesRepository {

    private static MoviesRepository repository;
    private TMDBApiService api;

    private MoviesRepository(TMDBApiService api) {
        this.api = api;
    }

    public static MoviesRepository getInstance() {

        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MoviesRepository(retrofit.create(TMDBApiService.class));
        }

        return repository;
    }

    public void getLatestMovies(final OnGetMoviesResponseCallback callback) {

        api.getLatestMovies(API_KEY, LANGUAGE)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {
                                callback.onMovieResponseSuccess(moviesResponse);
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

    public void getNowPlayingMovies(final OnGetMoviesResponseCallback callback, int currentPage) {

        api.getNowPlayingMovies(API_KEY, LANGUAGE, currentPage)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {
                                callback.onMovieResponseSuccess(moviesResponse);
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

    public void getPopularMovies(final OnGetMoviesResponseCallback callback, int currentPage) {

        api.getPopularMovies(API_KEY, LANGUAGE, currentPage)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {
                                callback.onMovieResponseSuccess(moviesResponse);
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

    public void getTopRatedMovies(final OnGetMoviesResponseCallback callback, int currentPage) {

        api.getTopRatedMovies(API_KEY, LANGUAGE, currentPage)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {
                                callback.onMovieResponseSuccess(moviesResponse);
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

    public void getUpcomingMovies(final OnGetMoviesResponseCallback callback, int currentPage) {

        api.getUpcomingMovies(API_KEY, LANGUAGE, currentPage)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {
                                callback.onMovieResponseSuccess(moviesResponse);
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

    public void getTrendingMovies(final OnGetMoviesResponseCallback callback) {

        api.getTrendingMovies("all", "week", API_KEY)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {
                                callback.onMovieResponseSuccess(moviesResponse);
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

    public void getTVLatest(final OnGetMoviesResponseCallback callback) {

        api.getTVLatest(API_KEY, LANGUAGE)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {
                                callback.onMovieResponseSuccess(moviesResponse);
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

    public void getTVAiringToday(final OnGetMoviesResponseCallback callback, int currentPage) {

        api.getTVAiringToday(API_KEY, LANGUAGE, currentPage)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {

                                callback.onMovieResponseSuccess(moviesResponse);
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

    public void getTVOnTheAir(final OnGetMoviesResponseCallback callback, int currentPage) {

        api.getTVOnTheAir(API_KEY, LANGUAGE, currentPage)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {
                                callback.onMovieResponseSuccess(moviesResponse);
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

    public void getTVPopular(final OnGetMoviesResponseCallback callback, int currentPage) {

        api.getTVPopular(API_KEY, LANGUAGE, currentPage)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {
                                callback.onMovieResponseSuccess(moviesResponse);
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

    public void getTVTopRated(final OnGetMoviesResponseCallback callback, int currentPage) {

        api.getTVTopRated(API_KEY, LANGUAGE, currentPage)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {
                                callback.onMovieResponseSuccess(moviesResponse);
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

    public void getSearchResults(final OnGetMoviesResponseCallback callback, int currentPage, String query) {

        api.getSearchResults(API_KEY, query, currentPage)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            final MovieResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMoviesId() != null) {
                                callback.onMovieResponseSuccess(moviesResponse);
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

    public void getMovieDetails(MovieId moviesId, final OnGetMoviesCallback callback) {

        api.getDetails(moviesId.getId(), API_KEY)
                .enqueue(new Callback<Movie>() {
                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        if (response.isSuccessful()) {
                            Movie movie = response.body();
                            if (movie != null) {
                                callback.onMovieSuccess(movie);
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
