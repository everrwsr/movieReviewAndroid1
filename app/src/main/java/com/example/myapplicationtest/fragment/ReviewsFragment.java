package com.example.myapplicationtest.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplicationtest.R;
import com.example.myapplicationtest.adapter.ReviewsAdapter;
import com.example.myapplicationtest.dao.MovieAReviewDBOpenHelper;
import com.example.myapplicationtest.model.Review;

import java.util.ArrayList;

public class ReviewsFragment extends Fragment {


    private EditText editTextSearch;
    private ListView listViewReviews;
    private Button buttonAddReview;

    private ArrayList<Review> reviewsList;
    private ReviewsAdapter reviewsAdapter;

    private MovieAReviewDBOpenHelper dbHelper;

    public  ReviewsFragment () {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        // Initialize views
        editTextSearch = view.findViewById(R.id.editTextSearchReview);
        listViewReviews = view.findViewById(R.id.listViewReviews);
        buttonAddReview = view.findViewById(R.id.buttonAddReview);

        // Set click listener for Add Movie button
        buttonAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddReviewDialog();
            }
        });

        // Set text change listener for search
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterReviews(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Initialize database helper
        dbHelper = new MovieAReviewDBOpenHelper(getActivity());

        // Load movies from database
        reviewsList = dbHelper.getAllReviews();


        reviewsAdapter = new ReviewsAdapter(getActivity(), reviewsList);

        // Set adapter for ListView

        listViewReviews.setAdapter(reviewsAdapter);

        // Set click listener for movie item in the list
        listViewReviews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openEditReviewDialog(position);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //
        dbHelper.close();
    }

    private void openAddReviewDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Review ");

        // Inflate the dialog layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_review, null);
        builder.setView(dialogView);

        // Find the input fields in the dialog layout
        final EditText editTextMovieTitle = dialogView.findViewById(R.id.editTextMovieTitle);
        final EditText editTextReviewerName = dialogView.findViewById(R.id.editTextReviewerName);
        final EditText editReviewRate = dialogView.findViewById(R.id.editReviewRate);
        final EditText editTextComment = dialogView.findViewById(R.id.editTextComment);

        // Set the positive button click listener
        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the input values from the dialog
                String title = editTextMovieTitle.getText().toString();
                String director = editTextReviewerName.getText().toString();
                float rate = Float.parseFloat(editReviewRate.getText().toString());
                String year = editTextComment.getText().toString();



                dbHelper.addReview(title,director,rate,year);


                refreshReviewList();
            }
        });

        // Set the negative button click listener
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        builder.create().show();
    }

    private void openEditReviewDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Movie");

        // Inflate the dialog layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_review, null);
        builder.setView(dialogView);

        // Find the input fields in the dialog layout
        final EditText editTextMovieTitle = dialogView.findViewById(R.id.editTextMovieTitle);
        final EditText editTextReviewerName = dialogView.findViewById(R.id.editTextReviewerName);
        final EditText editReviewRate = dialogView.findViewById(R.id.editReviewRate);
        final EditText editTextComment = dialogView.findViewById(R.id.editTextComment);




        final  Review selectedReview = reviewsList.get(position);

        // Set the initial values in the input fields
        editTextMovieTitle.setText(selectedReview.getMovieTitle());
        editTextReviewerName.setText(selectedReview.getReviewerName());
        editReviewRate.setText(String.valueOf(selectedReview.getRating()));
        editTextComment.setText(selectedReview.getComment());

        // Set the positive button click listener
        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the updated input values from the dialog
                String title = editTextMovieTitle.getText().toString();
                String director = editTextReviewerName.getText().toString();
                float rate = Float.parseFloat(editReviewRate.getText().toString());
                String comment = editTextComment.getText().toString();

                // Update the review's comment
                Review selectedReview = reviewsList.get(position);


                // Update the review in the database
                dbHelper.updateReview(selectedReview.getId(), title,director,rate,comment);

                // Refresh the review list
                refreshReviewList();

                Toast.makeText(getActivity(), "Review updated", Toast.LENGTH_SHORT).show();
            }
        });

        // Set the negative button click listener
        builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                dbHelper.deleteReview(selectedReview.getId());


                refreshReviewList();

                Toast.makeText(getActivity(), "Reviews deleted", Toast.LENGTH_SHORT).show();
            }
        });

        // Create and show the dialog
        builder.create().show();
    }




    private void filterReviews(String query) {
        ArrayList<Review> filteredReviewsList = new ArrayList<>();

        for (Review review: reviewsList) {
            if (review.getMovieTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredReviewsList.add(review);
            }
        }

        reviewsAdapter.setReviewsList(filteredReviewsList);
    }

    private void refreshReviewList() {


        reviewsList.clear();

        // Retrieve the latest movie data from the database


        reviewsList.addAll(dbHelper.getAllReviews());

        // Notify the adapter about the data set change


        reviewsAdapter.setReviewsList(reviewsList);
    }
}