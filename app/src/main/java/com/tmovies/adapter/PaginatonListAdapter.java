package com.tmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.tmovies.R;
import com.tmovies.model.Movie;
import com.tmovies.interfaces.RecyclerViewClickListener;

import java.util.ArrayList;

public class PaginatonListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private Context mContext;
    private RecyclerViewClickListener mListener;
    private String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";
    private ArrayList<Movie> list;

    public PaginatonListAdapter(Context context, ArrayList<Movie> list, RecyclerViewClickListener listener) {
        this.list = list;
        this.mContext = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Movie movie = list.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                ListVH listVH = (ListVH) holder;

                listVH.movie_year.setText(movie.getReleaseDate().split("-")[0]);
                listVH.movie_name.setText(movie.getTitle());

                listVH.movie_rating.setRating(movie.getRating() / 2);
                RequestOptions requestOptions = new RequestOptions().transform(new RoundedCorners(20))
                        .diskCacheStrategy(DiskCacheStrategy.ALL);

                Glide.with(holder.itemView.getContext())
                        .applyDefaultRequestOptions(requestOptions)
                        .load(IMAGE_BASE_URL + movie.getPosterPath())
                        .into(listVH.movie_poster);
                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_movie, parent, false);
        viewHolder = new ListVH(v1, mListener);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == list.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;
    }

    public Movie getItem(int id) {
        return list.get(id);
    }

    public class ListVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView movie_poster;
        TextView movie_name;
        TextView movie_year;
        RatingBar movie_rating;
        private RecyclerViewClickListener mListener;

        public ListVH(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mListener = listener;
            movie_poster = itemView.findViewById(R.id.movie_img);
            movie_name = itemView.findViewById(R.id.movie_title);
            movie_year = itemView.findViewById(R.id.movie_year);
            movie_rating = itemView.findViewById(R.id.movie_rating);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) mListener.onClick(view, getAdapterPosition());
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }
}
