package com.stvc.api.responses;

import com.stvc.entity.Movie;

import java.util.List;

public class MovieListResponse {

    private int quantity;

    private List<Movie> movies;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
