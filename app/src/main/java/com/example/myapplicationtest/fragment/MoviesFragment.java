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
import com.example.myapplicationtest.adapter.MoviesAdapter;
import com.example.myapplicationtest.dao.MovieAReviewDBOpenHelper;
import com.example.myapplicationtest.model.Movie;

import java.util.ArrayList;

public class MoviesFragment extends Fragment {

    private EditText editTextSearch;
    private ListView listViewMovies;
    private Button buttonAddMovie;

    private ArrayList<Movie> moviesList;
    private MoviesAdapter moviesAdapter;

    private MovieAReviewDBOpenHelper dbHelper;

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        // Initialize views
        editTextSearch = view.findViewById(R.id.editTextSearch);
        listViewMovies = view.findViewById(R.id.listViewMovies);
        buttonAddMovie = view.findViewById(R.id.buttonAddMovie);

        // Set click listener for Add Movie button
        buttonAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddMovieDialog();
            }
        });

        // Set text change listener for search
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterMovies(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Initialize database helper
        dbHelper = new MovieAReviewDBOpenHelper(getActivity());

        // Load movies from database
        moviesList = dbHelper.getAllMovies();


        moviesAdapter = new MoviesAdapter(getActivity(), moviesList);

        // Set adapter for ListView
        listViewMovies.setAdapter(moviesAdapter);

        // Set click listener for movie item in the list
        listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openEditMovieDialog(position);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Close the database helper when the fragment is destroyed
        dbHelper.close();
    }

    private void openAddMovieDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Movie");

        // Inflate the dialog layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_movie, null);
        builder.setView(dialogView);

        // Find the input fields in the dialog layout
        final EditText editTextTitle = dialogView.findViewById(R.id.editTextTitle);
        final EditText editTextDirector = dialogView.findViewById(R.id.editTextDirector);
        final EditText editTextYear = dialogView.findViewById(R.id.editTextYear);
        final EditText editTextRate = dialogView.findViewById(R.id.editTextRate);

        // Set the positive button click listener
        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the input values from the dialog
                String title = editTextTitle.getText().toString();
                String director = editTextDirector.getText().toString();
                int year = Integer.parseInt(editTextYear.getText().toString());
                float rate = Float.parseFloat(editTextRate.getText().toString());

                // Add the movie to the database or perform the desired action
                dbHelper.addMovie(title, director, year, rate);

                // Refresh the movie list
                refreshMovieList();
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

    private void openEditMovieDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Edit Movie");

        // Inflate the dialog layout
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_movie, null);
        builder.setView(dialogView);

        // Find the input fields in the dialog layout
        final EditText editTextTitle = dialogView.findViewById(R.id.editTextTitle);
        final EditText editTextDirector =dialogView.findViewById(R.id.editTextDirector);
        final EditText editTextYear = dialogView.findViewById(R.id.editTextYear);
        final EditText editTextRate = dialogView.findViewById(R.id.editTextRate);

        // Retrieve the movie at the selected position
        final Movie selectedMovie = moviesList.get(position);

        // Set the initial values in the input fields
        editTextTitle.setText(selectedMovie.getTitle());
        editTextDirector.setText(selectedMovie.getDirector());
        editTextYear.setText(String.valueOf(selectedMovie.getYear()));
        editTextRate.setText(String.valueOf(selectedMovie.getRating()));

        // Set the positive button click listener
        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Get the updated input values from the dialog
                String title = editTextTitle.getText().toString();
                String director = editTextDirector.getText().toString();
                int year = Integer.parseInt(editTextYear.getText().toString());
                float rate = Float.parseFloat(editTextRate.getText().toString());

                // Update the movie in the database or perform the desired action
                dbHelper.updateMovie(selectedMovie.getId(), title, director, year, rate);

                // Refresh the movie list
                refreshMovieList();

                Toast.makeText(getActivity(), "Movie updated", Toast.LENGTH_SHORT).show();
            }
        });

        // Set the negative button click listener
        builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete the movie from the database or perform the desired action
                dbHelper.deleteMovie(selectedMovie.getId());

                // Refresh the movie list
                refreshMovieList();

                Toast.makeText(getActivity(), "Movie deleted", Toast.LENGTH_SHORT).show();
            }
        });

        // Create and show the dialog
        builder.create().show();
    }

    private void filterMovies(String query) {
        ArrayList<Movie> filteredMoviesList = new ArrayList<>();
        for (Movie movie : moviesList) {
            if (movie.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredMoviesList.add(movie);
            }
        }
        moviesAdapter.setMoviesList(filteredMoviesList);
    }

    private void refreshMovieList() {
        // Clear the existing movie list
        moviesList.clear();

        // Retrieve the latest movie data from the database
        moviesList.addAll(dbHelper.getAllMovies());

        // Notify the adapter about the data set change
        moviesAdapter.setMoviesList(moviesList);
    }
}