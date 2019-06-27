package com.example.tanvi.movies;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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
    public static MyRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    ArrayList<Movie> movies;

    private MoviesRepository moviesRepository;

    SQLiteDatabaseHelper dbHelper = null;
    private static String CHANNEL_ID = "1234";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_screen);

        moviesRepository = MoviesRepository.getInstance();
        dbHelper = new SQLiteDatabaseHelper(this);
        createNotificationChannel();

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

                dbHelper.addMovie(movie);
                sendNotification(movie);
                movies.add(dbHelper.getMovie(movie.getId()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {
                Toast.makeText(MovieScreen.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void sendNotification(Movie movie) {

//        Intent intent = new Intent(this, MovieScreen.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.movie_icon)
                .setContentTitle(movie.getTitle())
                .setContentText("Checkout this new movie only on Movies App")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        int id = 1;
        notificationManager.notify(id++, builder.build());

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";
            String description = "Channel Description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(MovieScreen.this, WebViewActivity.class);
        Log.w("TAG", String.valueOf(position));
        intent.putExtra("position", String.valueOf(position));
        intent.putExtra("detail_url", adapter.getItem(position).getDetail_url());
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

}
