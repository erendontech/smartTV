package com.stvc.persistence;

import com.stvc.entity.Movie;
import java.util.List;

public class ContentDaoImpl implements IContentDao {

    @Override
    public boolean createMovie(Movie movie) {
        return false;
    }

    @Override
    public boolean deleteMovie(Integer id) {
        return false;
    }

    @Override
    public List<Movie> getMovies() {
        return null;
    }

    @Override
    public Movie getMovieDetail(Integer id) {
        return null;
    }
}
