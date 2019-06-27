package com.example.tanvi.movies.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tanvi.movies.R;
import com.example.tanvi.movies.model.Movie;

import java.util.ArrayList;
import java.util.List;

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> implements Filterable {

    private String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private List<Movie> mData;
    private List<Movie> filteredData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    MyRecyclerViewAdapter(Context context, List<Movie> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.filteredData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.vertical_movie_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(filteredData.get(position));

    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return filteredData.size();
        else return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filteredData = mData;
                } else {
                    List<Movie> filteredList = new ArrayList<>();
                    for (Movie row : mData) {

                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getReleaseDate().contains(constraint)) {
                            filteredList.add(row);
                        }
                    }

                    filteredData = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<Movie>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView movie_poster;
        TextView movie_name;
        TextView movie_year;
        RatingBar movie_rating;

        ViewHolder(View itemView) {
            super(itemView);
            movie_poster = itemView.findViewById(R.id.movie_img);
            movie_name = itemView.findViewById(R.id.movie_title);
            movie_year = itemView.findViewById(R.id.movie_year);
            movie_rating = itemView.findViewById(R.id.movie_rating);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        private void bind(Movie movie) {
            movie_year.setText(movie.getReleaseDate().split("-")[0]);
            movie_name.setText(movie.getTitle());
            movie_rating.setRating(movie.getRating());

            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + movie.getPosterPath())
                    .into(movie_poster);

        }
    }

    Movie getItem(int id) {
        return mData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
