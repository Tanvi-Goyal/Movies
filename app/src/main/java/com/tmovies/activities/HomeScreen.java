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
import android.widget.Toast;

import com.tmovies.R;
import com.tmovies.constants.AppConstants;
import com.tmovies.model.Movie;
import com.tmovies.utils.MoviesRepository;
import com.tmovies.utils.OnGetMoviesCallback;
import com.tmovies.utils.RecyclerViewClickListener;
import com.tmovies.utils.SQLiteDatabaseHelper;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    RecyclerView recyclerView_one;
    RecyclerView recyclerView_two;
    SearchView searchView;

    public static CategoriesRecyclerViewAdapter movieAdapter;
    public static CategoriesRecyclerViewAdapter tvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        recyclerView_one = findViewById(R.id.recyler_one);
        recyclerView_two = findViewById(R.id.recyler_two);
        searchView = findViewById(R.id.search_view);

        setMoviesCategoryRecycler();
        setTvCategoryRecycler();

//        showProcessDialog();

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
//
//        moviesRepository.getPopularMovies(new OnGetMoviesCallback() {
//            @Override
//            public void onSuccess(Movie movie) {
//
//                hideProcessDialog();
//                dbHelper.addMovie(movie);
////                sendNotification(movie);
//                movies.add(dbHelper.getMovie(movie.getId()));
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onError() {
//                Toast.makeText(HomeScreen.this, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void setMoviesCategoryRecycler() {
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView_one.setLayoutManager(layoutManager);
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(HomeScreen.this, MovieScreen.class);
                intent.putExtra("type", AppConstants.TYPE_MOVIES);
                intent.putExtra("category", movieAdapter.getItem(position));
                startActivity(intent);
            }
        };

        movieAdapter = new CategoriesRecyclerViewAdapter(HomeScreen.this, getMovieCategories(), listener);
        recyclerView_one.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }

    private ArrayList<String> getMovieCategories() {
        return new ArrayList<String>() {
            {
                add(AppConstants.CATEGORY_MOVIE_NOW_PLAYING);
                add(AppConstants.CATEGORY_MOVIE_POPULAR);
                add(AppConstants.CATEGORY_MOVIE_TOP_RATED);
                add(AppConstants.CATEGORY_MOVIE_UPCOMING);
                add(AppConstants.CATEGORY_MOVIE_TRENDING);
            }
        };
    }


    private void setTvCategoryRecycler() {
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView_two.setLayoutManager(layoutManager);
        RecyclerViewClickListener listener = new RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(HomeScreen.this, MovieScreen.class);
                intent.putExtra("type", AppConstants.TYPE_TV);
                intent.putExtra("category", tvAdapter.getItem(position));
                startActivity(intent);
            }
        };

        tvAdapter = new CategoriesRecyclerViewAdapter(HomeScreen.this, getTvCategories(), listener);
        recyclerView_two.setAdapter(tvAdapter);
        tvAdapter.notifyDataSetChanged();
    }

    private ArrayList<String> getTvCategories() {
        return new ArrayList<String>() {
            {
                add(AppConstants.CATEGORY_TV_AIRING_TODAY);
                add(AppConstants.CATEGORY_TV_ON_THE_AIR);
                add(AppConstants.CATEGORY_TV_POPULAR);
                add(AppConstants.CATEGORY_TV_TOP_RATED);
            }
        };
    }
}
