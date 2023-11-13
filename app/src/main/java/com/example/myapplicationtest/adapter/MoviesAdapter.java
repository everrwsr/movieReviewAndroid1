package com.example.myapplicationtest.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.model.Movie;

import java.util.ArrayList;


public class MoviesAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Movie> moviesList;

    public MoviesAdapter(Context context, ArrayList<Movie> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public int getCount() {
        return moviesList.size();
    }

    @Override
    public Object getItem(int position) {
        return moviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_movie, parent, false);

            holder = new ViewHolder();
            holder.textViewTitle = convertView.findViewById(R.id.textViewTitle);
            holder.textViewDirector = convertView.findViewById(R.id.textViewDirector);
            holder.textViewYear = convertView.findViewById(R.id.textViewYear);
            holder.textViewRating = convertView.findViewById(R.id.textViewRating);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Movie movie = moviesList.get(position);

        holder.textViewTitle.setText("电影标题："+movie.getTitle());
        holder.textViewDirector.setText("导演："+movie.getDirector());
        holder.textViewYear.setText("发行时间："+String.valueOf(movie.getYear()));
        holder.textViewRating.setText("评分："+String.valueOf(movie.getRating()));

        return convertView;
    }

    public void setMoviesList(ArrayList<Movie> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView textViewTitle;
        TextView textViewDirector;
        TextView textViewYear;
        TextView textViewRating;
    }
}