package com.example.tanvi.movies;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

//import com.example.tanvi.movies.utils.NetworkUtils;

public class MovieScreen extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{

    RecyclerView recyclerView_one, recyclerView_two;
    android.support.v7.widget.SearchView searchView;
    MyRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    String popularMoviesURL;
    ArrayList<Movie> movies;

    private MoviesRepository moviesRepository;

    SQLiteDatabaseHelper dbHelper = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_screen);

        moviesRepository = MoviesRepository.getInstance();
        dbHelper = new SQLiteDatabaseHelper(this);

        movies = new ArrayList<>();
        recyclerView_one = findViewById(R.id.recyler_one);
        searchView = findViewById(R.id.search_view);

//        new FetchMovies().execute();
//
//        Movie movie = new Movie("Avengers","2019",3.4,"https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg","www.google.com");
//        movies.add(movie);
//        movies.add(movie);
//        movies.add(movie);
//        movies.add(movie);
//        movies.add(movie);

        layoutManager = new GridLayoutManager(this, 2);

        // set up the RecyclerView
        recyclerView_one.setLayoutManager(layoutManager);
        adapter = new MyRecyclerViewAdapter(MovieScreen.this, movies);
        adapter.setClickListener(MovieScreen.this);
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


        moviesRepository.getMovies(new OnGetMoviesCallback() {
            @Override
            public void onSuccess(Movie movie) {

                // add movie to db
                Log.w("TAG", "Adding " + movie.getTitle());
                dbHelper.addMovie(movie);
                // shown notification
                Log.w("TAG", "Added " + movie.getTitle());
                sendNotification(movie);
                // update recylerview
                movies.add(dbHelper.getMovie(movie.getId()));
                Log.w("TAG", "Fetched " + movie.getTitle());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {
                Toast.makeText(MovieScreen.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void sendNotification(Movie movie) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_dialog_alert);
        Intent intent = new Intent(this, MovieScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("Notifications Title");
        builder.setContentText("Your notification content here.");
        builder.setSubText("Tap to view the website.");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(this, "You clicked " + adapter.getItem(position).getImdb_id(), Toast.LENGTH_SHORT).show();
    }
}
