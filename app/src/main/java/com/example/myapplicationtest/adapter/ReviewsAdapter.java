package com.example.myapplicationtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.model.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Review> reviewsList;

    public ReviewsAdapter(Context context, ArrayList<Review> reviewsList) {
        this.context = context;
        this.reviewsList = reviewsList;
    }

    @Override
    public int getCount() {
        return reviewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewsList.get(position);
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
            convertView = inflater.inflate(R.layout.item_review, parent, false);

            holder = new ViewHolder();
            holder.textViewMovieTitle = convertView.findViewById(R.id.textViewMovieTitle);
            holder.textViewReviewerName = convertView.findViewById(R.id.textViewReviewerName);
            holder.textViewRating = convertView.findViewById(R.id.textViewRating);
            holder.textViewComment = convertView.findViewById(R.id.textViewComment);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Review review = reviewsList.get(position);

        holder.textViewMovieTitle.setText("电影标题：" + review.getMovieTitle());
        holder.textViewReviewerName.setText("评论者姓名：" + review.getReviewerName());
        holder.textViewRating.setText("评分：" + String.valueOf(review.getRating()));
        holder.textViewComment.setText("评论：" + review.getComment());

        return convertView;
    }

    public void setReviewsList(ArrayList<Review> reviewsList) {
        this.reviewsList = reviewsList;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView textViewMovieTitle;
        TextView textViewReviewerName;
        TextView textViewRating;
        TextView textViewComment;
    }
}