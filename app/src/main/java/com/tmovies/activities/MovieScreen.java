package com.tmovies.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.tmovies.R;
import com.tmovies.adapter.PaginatonListAdapter;
import com.tmovies.constants.AppConstants;
import com.tmovies.model.Movie;
import com.tmovies.model.MovieResponse;
import com.tmovies.repository.MoviesRepository;
import com.tmovies.interfaces.OnGetMoviesCallback;
import com.tmovies.interfaces.OnGetMoviesResponseCallback;
import com.tmovies.utils.PaginationScrollListener;
import com.tmovies.interfaces.RecyclerViewClickListener;
import com.tmovies.db.SQLiteDatabaseHelper;

import java.util.ArrayList;

public class MovieScreen extends AppCompatActivity implements RecyclerViewClickListener {

    RecyclerView recyclerView_one;
    TextView title;
    TextView searchResults;
    private ArrayList<Movie> list = new ArrayList<>();
    public static PaginatonListAdapter adapter;
    private MoviesRepository moviesRepository;
    SQLiteDatabaseHelper dbHelper = null;
    ProgressDialog pd = null;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 0;
    private int currentPage = PAGE_START;
    private int total_results = 0;

    String type = "";
    String category = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_screen);
        setStatusBarColor();

        type = getIntent().getExtras().getString("type");
        category = getIntent().getExtras().getString("category");

        moviesRepository = MoviesRepository.getInstance();
        dbHelper = new SQLiteDatabaseHelper(this);
        pd = new ProgressDialog(this);

        title = findViewById(R.id.movie_category);
        searchResults = findViewById(R.id.searchResults);
        recyclerView_one = findViewById(R.id.recyler_one);

        if(type.equals(AppConstants.TYPE_MOVIES) || type.equals(AppConstants.TYPE_TV)) title.setText(category);
        else title.setText("Search Results for - " + category);

        showProcessDialog();
        getMovies(type, category);
        adapter = new PaginatonListAdapter(MovieScreen.this, list, this);
        setRecyclerScroll(recyclerView_one, adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        list.clear();
    }

    private void setRecyclerScroll(RecyclerView recyclerView_one, final PaginatonListAdapter adapter) {
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        recyclerView_one.setLayoutManager(linearLayoutManager);

        recyclerView_one.setAdapter(adapter);

        recyclerView_one.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                currentPage += 1;
                getMovies(type, category);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                adapter.addLoadingFooter();
                return isLoading;
            }

            @Override
            public int getCurrentPage() {
                return currentPage;
            }
        });
    }

    private ArrayList<Movie> getMovies(String type, String category) {
        final ArrayList<Movie> movies = new ArrayList<>();
        switch (type) {
            case AppConstants.TYPE_MOVIES:
                switch (category) {
                    case AppConstants.CATEGORY_MOVIE_NOW_PLAYING:
                        moviesRepository.getNowPlayingMovies(new OnGetMoviesResponseCallback() {

                            @Override
                            public void onMovieResponseSuccess(MovieResponse movieResponse) {
                                total_results = movieResponse.getTotalResults();
                                TOTAL_PAGES = movieResponse.getTotalPages();

                                if (total_results == 0) {
                                    hideProcessDialog();
                                    searchResults.setText("No Results Found.");
                                    recyclerView_one.setVisibility(View.GONE);
                                } else {
                                    searchResults.setText(total_results + " Results Found.");
                                    for (int index = 0; index < movieResponse.getMoviesId().size(); index++) {
                                        moviesRepository.getMovieDetails(movieResponse.getMoviesId().get(index), new OnGetMoviesCallback() {
                                            @Override
                                            public void onMovieSuccess(Movie movie) {
                                                hideProcessDialog();
                                                dbHelper.addMovie(movie);
                                                list.add(dbHelper.getMovie(movie.getId()));
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onError() {
                                                hideProcessDialog();
                                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    if (currentPage == TOTAL_PAGES) adapter.removeLoadingFooter();
                                }
                            }

                            @Override
                            public void onError() {
                                hideProcessDialog();
                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                            }

                        }, currentPage);
                        break;
                    case AppConstants.CATEGORY_MOVIE_POPULAR:
                        moviesRepository.getPopularMovies(new OnGetMoviesResponseCallback() {

                            @Override
                            public void onMovieResponseSuccess(MovieResponse movieResponse) {
                                total_results = movieResponse.getTotalResults();
                                TOTAL_PAGES = movieResponse.getTotalPages();

                                if (total_results == 0) {
                                    hideProcessDialog();
                                    searchResults.setText("No Results Found.");
                                    recyclerView_one.setVisibility(View.GONE);
                                } else {
                                    searchResults.setText(total_results + " Results Found.");
                                    for (int index = 0; index < movieResponse.getMoviesId().size(); index++) {
                                        moviesRepository.getMovieDetails(movieResponse.getMoviesId().get(index), new OnGetMoviesCallback() {
                                            @Override
                                            public void onMovieSuccess(Movie movie) {
                                                hideProcessDialog();
                                                dbHelper.addMovie(movie);
                                                list.add(dbHelper.getMovie(movie.getId()));
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onError() {
                                                hideProcessDialog();
                                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    if (currentPage == TOTAL_PAGES) adapter.removeLoadingFooter();
                                }
                            }

                            @Override
                            public void onError() {
                                hideProcessDialog();
                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                            }

                        }, currentPage);
                        break;

                    case AppConstants.CATEGORY_MOVIE_TOP_RATED:
                        moviesRepository.getTopRatedMovies(new OnGetMoviesResponseCallback() {

                            @Override
                            public void onMovieResponseSuccess(MovieResponse movieResponse) {
                                total_results = movieResponse.getTotalResults();
                                TOTAL_PAGES = movieResponse.getTotalPages();

                                if (total_results == 0) {
                                    hideProcessDialog();
                                    searchResults.setText("No Results Found.");
                                    recyclerView_one.setVisibility(View.GONE);
                                } else {
                                    searchResults.setText(total_results + " Results Found.");
                                    for (int index = 0; index < movieResponse.getMoviesId().size(); index++) {
                                        moviesRepository.getMovieDetails(movieResponse.getMoviesId().get(index), new OnGetMoviesCallback() {
                                            @Override
                                            public void onMovieSuccess(Movie movie) {
                                                hideProcessDialog();
                                                dbHelper.addMovie(movie);
                                                list.add(dbHelper.getMovie(movie.getId()));
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onError() {
                                                hideProcessDialog();
                                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    if (currentPage == TOTAL_PAGES) adapter.removeLoadingFooter();
                                }
                            }

                            @Override
                            public void onError() {
                                hideProcessDialog();
                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                            }

                        }, currentPage);
                        break;

                    case AppConstants.CATEGORY_MOVIE_UPCOMING:
                        moviesRepository.getUpcomingMovies(new OnGetMoviesResponseCallback() {

                            @Override
                            public void onMovieResponseSuccess(MovieResponse movieResponse) {
                                total_results = movieResponse.getTotalResults();
                                TOTAL_PAGES = movieResponse.getTotalPages();

                                if (total_results == 0) {
                                    hideProcessDialog();
                                    searchResults.setText("No Results Found.");
                                    recyclerView_one.setVisibility(View.GONE);
                                } else {
                                    searchResults.setText(total_results + " Results Found.");
                                    for (int index = 0; index < movieResponse.getMoviesId().size(); index++) {
                                        moviesRepository.getMovieDetails(movieResponse.getMoviesId().get(index), new OnGetMoviesCallback() {
                                            @Override
                                            public void onMovieSuccess(Movie movie) {
                                                hideProcessDialog();
                                                dbHelper.addMovie(movie);
                                                list.add(dbHelper.getMovie(movie.getId()));
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onError() {
                                                hideProcessDialog();
                                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    if (currentPage == TOTAL_PAGES) adapter.removeLoadingFooter();
                                }
                            }

                            @Override
                            public void onError() {
                                hideProcessDialog();
                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                            }

                        }, currentPage);
                        break;

                    case AppConstants.CATEGORY_MOVIE_TRENDING:
                        moviesRepository.getTrendingMovies(new OnGetMoviesResponseCallback() {

                            @Override
                            public void onMovieResponseSuccess(MovieResponse movieResponse) {
                                total_results = movieResponse.getTotalResults();
                                TOTAL_PAGES = movieResponse.getTotalPages();

                                if (total_results == 0) {
                                    hideProcessDialog();
                                    searchResults.setText("No Results Found.");
                                    recyclerView_one.setVisibility(View.GONE);
                                } else {
                                    searchResults.setText(total_results + " Results Found.");
                                    for (int index = 0; index < movieResponse.getMoviesId().size(); index++) {
                                        moviesRepository.getMovieDetails(movieResponse.getMoviesId().get(index), new OnGetMoviesCallback() {
                                            @Override
                                            public void onMovieSuccess(Movie movie) {
                                                hideProcessDialog();
                                                dbHelper.addMovie(movie);
                                                list.add(dbHelper.getMovie(movie.getId()));
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onError() {
                                                hideProcessDialog();
                                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    if (currentPage == TOTAL_PAGES) adapter.removeLoadingFooter();
                                }
                            }

                            @Override
                            public void onError() {
                                hideProcessDialog();
                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                            }

                        });
                        break;
                    default:
                        Toast.makeText(this, "Invalid Category", Toast.LENGTH_SHORT).show();
                }
                break;
            case AppConstants.TYPE_TV:
                switch (category) {

                    case AppConstants.CATEGORY_TV_AIRING_TODAY:
                        moviesRepository.getTVAiringToday(new OnGetMoviesResponseCallback() {

                            @Override
                            public void onMovieResponseSuccess(MovieResponse movieResponse) {
                                total_results = movieResponse.getTotalResults();
                                TOTAL_PAGES = movieResponse.getTotalPages();

                                if (total_results == 0) {
                                    hideProcessDialog();
                                    searchResults.setText("No Results Found.");
                                    recyclerView_one.setVisibility(View.GONE);
                                } else {
                                    searchResults.setText(total_results + " Results Found.");
                                    for (int index = 0; index < movieResponse.getMoviesId().size(); index++) {
                                        moviesRepository.getMovieDetails(movieResponse.getMoviesId().get(index), new OnGetMoviesCallback() {
                                            @Override
                                            public void onMovieSuccess(Movie movie) {
                                                hideProcessDialog();
                                                dbHelper.addMovie(movie);
                                                list.add(dbHelper.getMovie(movie.getId()));
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onError() {
                                                hideProcessDialog();
                                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    if (currentPage == TOTAL_PAGES) adapter.removeLoadingFooter();
                                }
                            }

                            @Override
                            public void onError() {
                                hideProcessDialog();
                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                            }

                        }, currentPage);
                        break;
                    case AppConstants.CATEGORY_TV_ON_THE_AIR:
                        moviesRepository.getTVOnTheAir(new OnGetMoviesResponseCallback() {

                            @Override
                            public void onMovieResponseSuccess(MovieResponse movieResponse) {
                                total_results = movieResponse.getTotalResults();
                                TOTAL_PAGES = movieResponse.getTotalPages();

                                if (total_results == 0) {
                                    hideProcessDialog();
                                    searchResults.setText("No Results Found.");
                                    recyclerView_one.setVisibility(View.GONE);
                                } else {
                                    searchResults.setText(total_results + " Results Found.");
                                    for (int index = 0; index < movieResponse.getMoviesId().size(); index++) {
                                        moviesRepository.getMovieDetails(movieResponse.getMoviesId().get(index), new OnGetMoviesCallback() {
                                            @Override
                                            public void onMovieSuccess(Movie movie) {
                                                hideProcessDialog();
                                                dbHelper.addMovie(movie);
                                                list.add(dbHelper.getMovie(movie.getId()));
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onError() {
                                                hideProcessDialog();
                                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    if (currentPage == TOTAL_PAGES) adapter.removeLoadingFooter();
                                }
                            }

                            @Override
                            public void onError() {
                                hideProcessDialog();
                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                            }

                        }, currentPage);
                        break;

                    case AppConstants.CATEGORY_TV_POPULAR:
                        moviesRepository.getTVPopular(new OnGetMoviesResponseCallback() {

                            @Override
                            public void onMovieResponseSuccess(MovieResponse movieResponse) {
                                total_results = movieResponse.getTotalResults();
                                TOTAL_PAGES = movieResponse.getTotalPages();

                                if (total_results == 0) {
                                    hideProcessDialog();
                                    searchResults.setText("No Results Found.");
                                    recyclerView_one.setVisibility(View.GONE);
                                } else {
                                    searchResults.setText(total_results + " Results Found.");
                                    for (int index = 0; index < movieResponse.getMoviesId().size(); index++) {
                                        moviesRepository.getMovieDetails(movieResponse.getMoviesId().get(index), new OnGetMoviesCallback() {
                                            @Override
                                            public void onMovieSuccess(Movie movie) {
                                                hideProcessDialog();
                                                dbHelper.addMovie(movie);
                                                list.add(dbHelper.getMovie(movie.getId()));
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onError() {
                                                hideProcessDialog();
                                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    if (currentPage == TOTAL_PAGES) adapter.removeLoadingFooter();
                                }
                            }

                            @Override
                            public void onError() {
                                hideProcessDialog();
                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                            }

                        }, currentPage);
                        break;

                    case AppConstants.CATEGORY_TV_TOP_RATED:
                        moviesRepository.getTVTopRated(new OnGetMoviesResponseCallback() {

                            @Override
                            public void onMovieResponseSuccess(MovieResponse movieResponse) {
                                total_results = movieResponse.getTotalResults();
                                TOTAL_PAGES = movieResponse.getTotalPages();

                                if (total_results == 0) {
                                    hideProcessDialog();
                                    searchResults.setText("No Results Found.");
                                    recyclerView_one.setVisibility(View.GONE);
                                } else {
                                    searchResults.setText(total_results + " Results Found.");
                                    for (int index = 0; index < movieResponse.getMoviesId().size(); index++) {
                                        moviesRepository.getMovieDetails(movieResponse.getMoviesId().get(index), new OnGetMoviesCallback() {
                                            @Override
                                            public void onMovieSuccess(Movie movie) {
                                                hideProcessDialog();
                                                dbHelper.addMovie(movie);
                                                list.add(dbHelper.getMovie(movie.getId()));
                                                adapter.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onError() {
                                                hideProcessDialog();
                                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                    if (currentPage == TOTAL_PAGES) adapter.removeLoadingFooter();
                                }
                            }

                            @Override
                            public void onError() {
                                hideProcessDialog();
                                Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                            }

                        }, currentPage);
                        break;
                    default:
                        Toast.makeText(this, "Invalid Category", Toast.LENGTH_SHORT).show();
                }
                break;
            case AppConstants.TYPE_SEARCH:
                moviesRepository.getSearchResults(new OnGetMoviesResponseCallback() {

                    @Override
                    public void onMovieResponseSuccess(MovieResponse movieResponse) {
                        total_results = movieResponse.getTotalResults();
                        TOTAL_PAGES = movieResponse.getTotalPages();

                        if (total_results == 0) {
                            hideProcessDialog();
                            searchResults.setText("No Results Found.");
                            recyclerView_one.setVisibility(View.GONE);
                        } else {
                            searchResults.setText(total_results + " Results Found.");
                            for (int index = 0; index < movieResponse.getMoviesId().size(); index++) {
                                moviesRepository.getMovieDetails(movieResponse.getMoviesId().get(index), new OnGetMoviesCallback() {
                                    @Override
                                    public void onMovieSuccess(Movie movie) {
                                        hideProcessDialog();
                                        dbHelper.addMovie(movie);
                                        list.add(dbHelper.getMovie(movie.getId()));
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onError() {
                                        hideProcessDialog();
                                        Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            if (currentPage == TOTAL_PAGES) adapter.removeLoadingFooter();
                        }
                    }

                    @Override
                    public void onError() {
                        hideProcessDialog();
                        Toast.makeText(getApplicationContext(), R.string.no_internet_text, Toast.LENGTH_SHORT).show();
                    }

                }, currentPage, category);  // here category is the query parameter

                break;
        }
        return movies;
    }

    @Override
    public void onClick(View view, int position) {
        Intent intent = new Intent(MovieScreen.this, WebViewActivity.class);
        intent.putExtra("position", String.valueOf(position));
        intent.putExtra("detail_url", adapter.getItem(position).getDetail_url());
        startActivity(intent);
    }

    public void showProcessDialog() {
        if (!pd.isShowing()) {
            pd.setTitle("Please Wait...");
            pd.setMessage("Loading Info");
            pd.setCancelable(false);
            pd.show();
        }
    }

    public void hideProcessDialog() {
        if (pd.isShowing()) {
            pd.dismiss();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
    }
}
