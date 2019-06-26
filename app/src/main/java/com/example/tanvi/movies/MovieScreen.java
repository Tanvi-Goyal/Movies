package com.example.tanvi.movies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//import com.example.tanvi.movies.utils.NetworkUtils;

public class MovieScreen extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{

    RecyclerView recyclerView_one, recyclerView_two;
    MyRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    String popularMoviesURL;
    ArrayList<Movie> movies;

    private MoviesRepository moviesRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_screen);
        moviesRepository = MoviesRepository.getInstance();



        recyclerView_one = findViewById(R.id.recyler_one);

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

        moviesRepository.getMovies(new OnGetMoviesCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
//                adapter.notify();
                recyclerView_one.setLayoutManager(layoutManager);
                adapter = new MyRecyclerViewAdapter(MovieScreen.this, movies);
                adapter.setClickListener(MovieScreen.this);
                recyclerView_one.setAdapter(adapter);
                adapter.notifyDataSetChanged();


//                PagerSnapHelper snapHelper = new PagerSnapHelper();
//                snapHelper.attachToRecyclerView(recyclerView_one);

            }

            @Override
            public void onError() {
                Toast.makeText(MovieScreen.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(this, "You clicked " + adapter.getItem(position).getImdb_id(), Toast.LENGTH_SHORT).show();
    }

//    public class FetchMovies extends AsyncTask<Void,Void,Void> {
//
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            popularMoviesURL = "https://api.themoviedb.org/3/movie/550?api_key=f237940c743ded3e0dfd0193a5b6fb5b";
//            movies = new ArrayList<>();
//
//            try {
//                if(NetworkUtils.networkStatus(MovieScreen.this)){
//                    movies = NetworkUtils.fetchData(popularMoviesURL); //Get popular movies
////                    mTopTopRatedList = NetworkUtils.fetchData(topRatedMoviesURL); //Get top rated movies
//                }else{
//                    Toast.makeText(MovieScreen.this,"No Internet Connection",Toast.LENGTH_LONG).show();
//                }
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//    }
}
