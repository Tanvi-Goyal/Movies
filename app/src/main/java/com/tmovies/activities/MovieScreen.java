package com.tmovies.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tmovies.R;
import com.tmovies.constants.AppConstants;
import com.tmovies.model.Movie;
import com.tmovies.utils.MoviesRepository;
import com.tmovies.utils.OnGetMoviesCallback;
import com.tmovies.utils.SQLiteDatabaseHelper;

import java.util.ArrayList;

public class MovieScreen extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    RecyclerView recyclerView_one;
    TextView title;
    LinearLayoutManager layoutManager;
    public static MyRecyclerViewAdapter adapter;
    private MoviesRepository moviesRepository;
    SQLiteDatabaseHelper dbHelper = null;
    ProgressDialog pd = null;

    String type = "";
    String category = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_screen);

        type = getIntent().getExtras().getString("type");
        category = getIntent().getExtras().getString("category");

        moviesRepository = MoviesRepository.getInstance();
        dbHelper = new SQLiteDatabaseHelper(this);

        title = findViewById(R.id.movie_category);
        recyclerView_one = findViewById(R.id.recyler_one);
        title.setText(category);
//        searchView = findViewById(R.id.search_view);

        showProcessDialog();
        setMoviesRecycler();

//        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                adapter.getFilter().filter(s);
//
//                return false;
//            }
//        });
    }

    private void setMoviesRecycler() {
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView_one.setLayoutManager(layoutManager);
        adapter = new MyRecyclerViewAdapter(MovieScreen.this, getMovies(type, category));
        adapter.setClickListener(MovieScreen.this);
        recyclerView_one.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private ArrayList<Movie> getMovies(String type, String category) {
        final ArrayList<Movie> movies = new ArrayList<>();
        if (type.equals(AppConstants.TYPE_MOVIES)) {
            switch (category) {
                case AppConstants.CATEGORY_MOVIE_NOW_PLAYING:
                    moviesRepository.getNowPlayingMovies(new OnGetMoviesCallback() {
                        @Override
                        public void onSuccess(Movie movie) {
                            hideProcessDialog();
                            dbHelper.addMovie(movie);
                            movies.add(dbHelper.getMovie(movie.getId()));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {
                            hideProcessDialog();
                            Toast.makeText(MovieScreen.this, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                case AppConstants.CATEGORY_MOVIE_POPULAR:
                    moviesRepository.getPopularMovies(new OnGetMoviesCallback() {
                        @Override
                        public void onSuccess(Movie movie) {
                            hideProcessDialog();
                            dbHelper.addMovie(movie);
                            movies.add(dbHelper.getMovie(movie.getId()));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {
                            hideProcessDialog();
                            Toast.makeText(MovieScreen.this, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;

                case AppConstants.CATEGORY_MOVIE_TOP_RATED:
                    moviesRepository.getTopRatedMovies(new OnGetMoviesCallback() {
                        @Override
                        public void onSuccess(Movie movie) {
                            hideProcessDialog();
                            dbHelper.addMovie(movie);
                            movies.add(dbHelper.getMovie(movie.getId()));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {
                            hideProcessDialog();
                            Toast.makeText(MovieScreen.this, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;

                case AppConstants.CATEGORY_MOVIE_UPCOMING:
                    moviesRepository.getUpcomingMovies(new OnGetMoviesCallback() {
                        @Override
                        public void onSuccess(Movie movie) {
                            hideProcessDialog();
                            dbHelper.addMovie(movie);
                            movies.add(dbHelper.getMovie(movie.getId()));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {
                            hideProcessDialog();
                            Toast.makeText(MovieScreen.this, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;

                case AppConstants.CATEGORY_MOVIE_TRENDING:
                    moviesRepository.getTrendingMovies(new OnGetMoviesCallback() {
                        @Override
                        public void onSuccess(Movie movie) {
                            hideProcessDialog();
                            dbHelper.addMovie(movie);
                            movies.add(dbHelper.getMovie(movie.getId()));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {
                            hideProcessDialog();
                            Toast.makeText(MovieScreen.this, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                default:
                    Toast.makeText(this, "Invalid Category", Toast.LENGTH_SHORT).show();
            }
        } else if (type.equals(AppConstants.TYPE_TV)) {
            switch (category) {

                case AppConstants.CATEGORY_TV_AIRING_TODAY:
                    moviesRepository.getTVAiringToday(new OnGetMoviesCallback() {
                        @Override
                        public void onSuccess(Movie movie) {
                            hideProcessDialog();
                            dbHelper.addMovie(movie);
                            movies.add(dbHelper.getMovie(movie.getId()));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {
                            hideProcessDialog();
                            Toast.makeText(MovieScreen.this, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                case AppConstants.CATEGORY_TV_ON_THE_AIR:
                    moviesRepository.getTVOnTheAir(new OnGetMoviesCallback() {
                        @Override
                        public void onSuccess(Movie movie) {
                            hideProcessDialog();
                            dbHelper.addMovie(movie);
                            movies.add(dbHelper.getMovie(movie.getId()));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {
                            hideProcessDialog();
                            Toast.makeText(MovieScreen.this, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;

                case AppConstants.CATEGORY_TV_POPULAR:
                    moviesRepository.getTVPopular(new OnGetMoviesCallback() {
                        @Override
                        public void onSuccess(Movie movie) {
                            hideProcessDialog();
                            dbHelper.addMovie(movie);
                            movies.add(dbHelper.getMovie(movie.getId()));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {
                            hideProcessDialog();
                            Toast.makeText(MovieScreen.this, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;

                case AppConstants.CATEGORY_TV_TOP_RATED:
                    moviesRepository.getTVTopRated(new OnGetMoviesCallback() {
                        @Override
                        public void onSuccess(Movie movie) {
                            hideProcessDialog();
                            dbHelper.addMovie(movie);
                            movies.add(dbHelper.getMovie(movie.getId()));
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onError() {
                            hideProcessDialog();
                            Toast.makeText(MovieScreen.this, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                default:
                    Toast.makeText(this, "Invalid Category", Toast.LENGTH_SHORT).show();
            }
        }

        return movies;
    }

    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(MovieScreen.this, WebViewActivity.class);
        intent.putExtra("position", String.valueOf(position));
        intent.putExtra("detail_url", adapter.getItem(position).getDetail_url());
        startActivity(intent);
    }

    public void showProcessDialog() {
        pd = new ProgressDialog(this);
        pd.setTitle("Please Wait...");
        pd.setMessage("Loading Info");
        pd.setCancelable(false);
        pd.show();
    }

    public void hideProcessDialog() {
        if (pd.isShowing()) {
            pd.dismiss();
        }
    }

}
