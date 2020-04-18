package com.tmovies.activities;

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
import com.tmovies.R;
import com.tmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    CategoriesRecyclerViewAdapter(Context context, ArrayList<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_categories, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textCategory;

        ViewHolder(View itemView) {
            super(itemView);
            textCategory = itemView.findViewById(R.id.textCategory);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        private void bind(String category, int position) {
            textCategory.setText(category);
//            textCategory.setBackground(itemView.getContext().getColor(getBackgroundFromPosition(position)));
        }
    }

    private int getBackgroundFromPosition(int position) {
        if(position == 0) return R.color.green;
        else if(position == 1) return R.color.redLight;
        else if(position == 2) return R.color.purple;
        else if(position == 3) return R.color.blue;
        else if(position == 4) return R.color.pink;
        else if(position == 5) return R.color.orange;
        else return 0;
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
