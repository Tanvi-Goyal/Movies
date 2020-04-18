package com.tmovies.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.tmovies.R;
import com.tmovies.model.Movie;
import com.tmovies.utils.MoviesRepository;
import com.tmovies.utils.OnGetMoviesCallback;
import com.tmovies.utils.SQLiteDatabaseHelper;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    RecyclerView recyclerView_one;
    android.support.v7.widget.SearchView searchView;
    public static MyRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;
    ArrayList<Movie> movies;
    private MoviesRepository moviesRepository;
    SQLiteDatabaseHelper dbHelper = null;
    private static String CHANNEL_ID = "1234";
    ProgressDialog pd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_screen);

        moviesRepository = MoviesRepository.getInstance();
        dbHelper = new SQLiteDatabaseHelper(this);
//        createNotificationChannel();

        movies = new ArrayList<>();
        recyclerView_one = findViewById(R.id.recyler_one);
        searchView = findViewById(R.id.search_view);

        showProcessDialog();

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView_one.setLayoutManager(layoutManager);
        adapter = new MyRecyclerViewAdapter(HomeScreen.this, movies);
        adapter.setClickListener(HomeScreen.this);
        recyclerView_one.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);

                return false;
            }
        });

        moviesRepository.getPopularMovies(new OnGetMoviesCallback() {
            @Override
            public void onSuccess(Movie movie) {

                hideProcessDialog();
                dbHelper.addMovie(movie);
//                sendNotification(movie);
                movies.add(dbHelper.getMovie(movie.getId()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {
                Toast.makeText(HomeScreen.this, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(HomeScreen.this, WebViewActivity.class);
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
