package com.stvc.api.handlers;

import com.stvc.api.responses.MovieListResponse;
import com.stvc.entity.Movie;

import java.util.List;

public class MovieHandlers {

    public MovieListResponse processListMovies(List<Movie> movies) {
        MovieListResponse result = new MovieListResponse();
        result.setQuantity(movies.size());
        result.setMovies(movies);

        return result;
    }
}
