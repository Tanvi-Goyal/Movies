package com.example.tanvi.movies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieScreen extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{

    RecyclerView recyclerView_one,recyclerView_two; ;
    MyRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_screen);

        recyclerView_one = findViewById(R.id.recyler_one);
        recyclerView_two = findViewById(R.id.recyler_two);


        ArrayList<Movie> movies = new ArrayList<>();
        Movie movie = new Movie("Avengers","2019",3.4,"https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg","www.google.com");
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);
        movies.add(movie);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,true){
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {


                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(MovieScreen.this) {

                    private static final float SPEED = 1000f;

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }

                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };

        // set up the RecyclerView
        recyclerView_one.setLayoutManager(layoutManager);
        adapter = new MyRecyclerViewAdapter(this, movies);
        adapter.setClickListener(this);
        recyclerView_one.setAdapter(adapter);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView_one);


    }

    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public class FetchMovies extends AsyncTask<Void,Void,Void> {

        
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
