package com.example.myapplicationtest.model;


public class Movie {
    private long id;
    private String title;
    private String director;
    private int year;
    private float rating;

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                '}';
    }

    public Movie(long id, String title, String director, int year, float rating) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.year = year;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getYear() {
        return year;
    }

    public float getRating() {
        return rating;
    }


}