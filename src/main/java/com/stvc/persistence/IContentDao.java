package com.stvc.persistence;

import com.stvc.entity.Movie;

import java.util.List;

public interface IContentDao {

    boolean createMovie(Movie movie);

    boolean deleteMovie(Integer id);

    List<Movie> getMovies();

    Movie getMovieDetail(Integer id);

    List<Movie> getMoviesAllData();
}