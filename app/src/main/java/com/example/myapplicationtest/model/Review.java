package com.example.myapplicationtest.model;

public class Review {
    private long id;
    private String movieTitle;
    private String reviewerName;
    private float rating;
    private String comment;

    public Review(long id, String movieTitle, String reviewerName, float rating, String comment) {
        this.id = id;
        this.movieTitle = movieTitle;
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", movieTitle='" + movieTitle + '\'' +
                ", reviewerName='" + reviewerName + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public float getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}