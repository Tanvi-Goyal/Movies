package com.example.tanvi.movies.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.tanvi.movies.R;
import com.example.tanvi.movies.model.Movie;
import com.example.tanvi.movies.utils.MoviesRepository;
import com.example.tanvi.movies.utils.OnGetMoviesCallback;
import com.example.tanvi.movies.utils.SQLiteDatabaseHelper;

import java.util.ArrayList;

public class MovieScreen extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

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
        createNotificationChannel();

        movies = new ArrayList<>();
        recyclerView_one = findViewById(R.id.recyler_one);
        searchView = findViewById(R.id.search_view);

        showProcessDialog();

        layoutManager = new GridLayoutManager(this, 2);
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

                hideProcessDialog();
                dbHelper.addMovie(movie);
                sendNotification(movie);
                movies.add(dbHelper.getMovie(movie.getId()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {
                Toast.makeText(MovieScreen.this, getString(R.string.no_internet_text), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void sendNotification(Movie movie) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.movie_icon)
                .setContentTitle(movie.getTitle())
                .setContentText(getString(R.string.notification_description))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, builder.build());

    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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
