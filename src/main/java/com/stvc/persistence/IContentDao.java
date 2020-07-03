package com.stvc.persistence;

import com.stvc.entity.Movie;

import java.util.List;

public interface IContentDao {

    public boolean createMovie(Movie movie);

    public boolean deleteMovie(Integer id);

    public List<Movie> getMovies();

    public Movie getMovieDetail(Integer id);
}