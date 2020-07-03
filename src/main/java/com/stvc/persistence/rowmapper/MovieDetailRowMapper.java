package com.stvc.persistence.rowmapper;

import com.stvc.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieDetailRowMapper implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie = new Movie();

        movie.setTitle(rs.getString("movie_name"));

        return movie;
    }
}