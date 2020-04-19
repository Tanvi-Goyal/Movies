package com.tmovies.activities;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmovies.R;
import com.tmovies.constants.AppConstants;
import com.tmovies.utils.RecyclerViewClickListener;

import java.util.ArrayList;

class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mData;
    private LayoutInflater mInflater;
    private RecyclerViewClickListener mListener;

    CategoriesRecyclerViewAdapter(Context context, ArrayList<String> data, RecyclerViewClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_categories, parent, false);
        return new ViewHolder(view, mListener);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.textCategory.setText(mData.get(position));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                viewHolder.textCategory.setBackground(holder.itemView.getContext().getDrawable(getBackgroundFromPosition(position)));
                viewHolder.textCategory.setCompoundDrawablesWithIntrinsicBounds(holder.itemView.getContext().getDrawable(getCategoryDrawable(mData.get(position))), null, null, null);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textCategory;
        private RecyclerViewClickListener mListener;

        ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            mListener = listener;
            textCategory = itemView.findViewById(R.id.textCategory);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) mListener.onClick(view, getAdapterPosition());
        }
    }

    private int getBackgroundFromPosition(int position) {
        if (position == 0) return R.drawable.bg_green;
        else if (position == 1) return R.drawable.bg_red_light;
        else if (position == 2) return R.drawable.bg_purple;
        else if (position == 3) return R.drawable.bg_blue;
        else if (position == 4) return R.drawable.bg_pink;
        else if (position == 5) return R.drawable.bg_orange;
        else return 0;
    }

    private int getCategoryDrawable(String category) {
        if (category.equals(AppConstants.CATEGORY_MOVIE_NOW_PLAYING) || category.equals(AppConstants.CATEGORY_TV_AIRING_TODAY))
            return R.drawable.ic_now_playing;
        else if (category.equals(AppConstants.CATEGORY_MOVIE_POPULAR) || category.equals(AppConstants.CATEGORY_TV_POPULAR))
            return R.drawable.ic_popular;
        else if (category.equals(AppConstants.CATEGORY_MOVIE_TOP_RATED) || category.equals(AppConstants.CATEGORY_TV_TOP_RATED))
            return R.drawable.ic_top_rated;
        else if (category.equals(AppConstants.CATEGORY_MOVIE_UPCOMING) || category.equals(AppConstants.CATEGORY_TV_ON_THE_AIR))
            return R.drawable.ic_upcoming;
        else if (category.equals(AppConstants.CATEGORY_MOVIE_TRENDING))
            return R.drawable.ic_trending;
        else return 0;
    }

    String getItem(int id) {
        return mData.get(id);
    }
}
