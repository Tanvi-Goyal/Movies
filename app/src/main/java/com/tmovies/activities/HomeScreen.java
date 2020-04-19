package com.tmovies.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tmovies.R;
import com.tmovies.adapter.CategoriesRecyclerViewAdapter;
import com.tmovies.constants.AppConstants;
import com.tmovies.interfaces.RecyclerViewClickListener;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    RecyclerView recyclerView_one;
    RecyclerView recyclerView_two;
    SearchView searchView;

    public static CategoriesRecyclerViewAdapter movieAdapter;
    public static CategoriesRecyclerViewAdapter tvAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        setStatusBarColor();

        recyclerView_one = findViewById(R.id.recyler_one);
        recyclerView_two = findViewById(R.id.recyler_two);
        searchView = findViewById(R.id.search_view);

        setMoviesCategoryRecycler();
        setTvCategoryRecycler();

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Intent intent = new Intent(HomeScreen.this, MovieScreen.class);
                intent.putExtra("type", AppConstants.TYPE_SEARCH);
                intent.putExtra("category", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Calling twice: first empty text field, second iconify the view
        searchView.setIconified(true);
        searchView.setIconified(true);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor() {
        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
    }
}
