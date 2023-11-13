package com.example.myapplicationtest.dao;


import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplicationtest.model.Movie;
import com.example.myapplicationtest.model.Review;

import java.util.ArrayList;

public class MovieAReviewDBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "movie_review.db";
    private static final int DATABASE_VERSION = 1;

    public MovieAReviewDBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建电影表
        String createMoviesTableQuery = "CREATE TABLE IF NOT EXISTS movies (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "director TEXT," +
                "year INTEGER," +
                "rating REAL)";
        db.execSQL(createMoviesTableQuery);

        // 创建影评表
        String createReviewsTableQuery = "CREATE TABLE IF NOT EXISTS reviews (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "movie_title TEXT," +
                "reviewer_name TEXT," +
                "rating REAL," +
                "comment TEXT)";
        db.execSQL(createReviewsTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 如果需要升级数据库版本时的操作，可以在这里进行处理
    }

    // 电影表的增删改查方法

    public void addMovie(String title, String director, int year, float rating) {
        SQLiteDatabase db = getWritableDatabase();
        String insertQuery = "INSERT INTO movies (title, director, year, rating) VALUES (?, ?, ?, ?)";
        db.execSQL(insertQuery, new Object[]{title, director, year, rating});
        db.close();
    }
    public void addMovie(String title, String director, int year) {
        addMovie(title, director, year,0.0f);

    }

    public void deleteMovie(long movieId) {
        SQLiteDatabase db = getWritableDatabase();
        String deleteQuery = "DELETE FROM movies WHERE _id = ?";
        db.execSQL(deleteQuery, new Object[]{movieId});
        db.close();
    }

    public void updateMovieRating(long movieId, float rating) {
        SQLiteDatabase db = getWritableDatabase();
        String updateQuery = "UPDATE movies SET rating = ? WHERE _id = ?";
        db.execSQL(updateQuery, new Object[]{rating, movieId});
        db.close();
    }

    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> moviesList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("movies", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") long movieId = cursor.getLong(cursor.getColumnIndex("_id"));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") String director = cursor.getString(cursor.getColumnIndex("director"));
            @SuppressLint("Range") int year = cursor.getInt(cursor.getColumnIndex("year"));
            @SuppressLint("Range") float rating = cursor.getFloat(cursor.getColumnIndex("rating"));
            moviesList.add(new Movie(movieId, title, director, year, rating));
        }
        cursor.close();
        db.close();
        return moviesList;
    }

    // 影评表的增删改查方法



    public void addReview(String movieTitle, String reviewerName, float rating, String comment) {
        SQLiteDatabase db = getWritableDatabase();
        String insertQuery = "INSERT INTO reviews (movie_title, reviewer_name, rating, comment) VALUES (?, ?, ?, ?)";
        db.execSQL(insertQuery, new Object[]{movieTitle, reviewerName, rating, comment});
        db.close();
    }

    public void deleteReview(long reviewId) {
        SQLiteDatabase db = getWritableDatabase();
        String deleteQuery = "DELETE FROM reviews WHERE _id = ?";
        db.execSQL(deleteQuery, new Object[]{reviewId});
        db.close();
    }

    public void updateReviewComment(long reviewId, String comment) {
        SQLiteDatabase db = getWritableDatabase();
        String updateQuery = "UPDATE reviews SET comment = ? WHERE _id = ?";
        db.execSQL(updateQuery, new Object[]{comment, reviewId});
        db.close();
    }

    public ArrayList<Review> getAllReviews() {
        ArrayList<Review> reviewsList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("reviews", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") long reviewId = cursor.getLong(cursor.getColumnIndex("_id"));
            @SuppressLint("Range") String movieTitle = cursor.getString(cursor.getColumnIndex("movie_title"));
            @SuppressLint("Range") String reviewerName = cursor.getString(cursor.getColumnIndex("reviewer_name"));
            @SuppressLint("Range") float rating = cursor.getFloat(cursor.getColumnIndex("rating"));
            @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex("comment"));
            reviewsList.add(new Review(reviewId, movieTitle, reviewerName, rating, comment));
        }
        cursor.close();
        db.close();
        return reviewsList;
    }

    public void addReview(String movieTitle, String reviewText) {
        addReview(movieTitle, "", 0.0f, reviewText);
    }

    public void updateMovie(long id, String title, String director, int year, float rate) {
        SQLiteDatabase db = getWritableDatabase();
        String updateQuery = "UPDATE movies SET title = ?, director = ?, year = ?, rating = ? WHERE _id = ?";
        db.execSQL(updateQuery, new Object[]{title, director, year, rate, id});
        db.close();
    }
    public void updateReview(long id,String title, String reviewerName, float rating, String comment) {

        SQLiteDatabase db = getWritableDatabase();
        String updateQuery = "UPDATE reviews SET movie_title = ?, reviewer_name = ?, rating = ?, comment = ? WHERE _id = ?";
        db.execSQL(updateQuery, new Object[]{title, reviewerName, rating, comment,id});
        db.close();


    }
}