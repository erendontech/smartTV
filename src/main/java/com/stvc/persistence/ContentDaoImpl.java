package com.stvc.persistence;

import com.stvc.entity.Movie;
import com.stvc.persistence.rowmapper.MovieDetailRowMapper;
import com.stvc.persistence.rowmapper.MovieListRowMapper;
import com.stvc.persistence.util.ContentDaoUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ContentDaoImpl implements IContentDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean createMovie(Movie movie) {
        String genres, casts;
        boolean success = false;
        genres = ContentDaoUtil.getGenresFormatDB(movie.getGenres());
        casts = ContentDaoUtil.getCastsFormatDB(movie.getCasts());

        try {
            ResultSet rs;
            jdbcTemplate = new JdbcTemplate(dataSource);
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            CallableStatement callableStatement;
            callableStatement = connection.prepareCall("{call register_movie(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1, movie.getTitle());
            callableStatement.setInt(2, movie.getYear());
            callableStatement.setString(3, movie.getDescription());
            callableStatement.setInt(4, movie.getAudio().getId());
            callableStatement.setString(5, movie.getAudio().getName());
            callableStatement.setInt(6, movie.getQuality().getId());
            callableStatement.setString(7, movie.getQuality().getName());
            callableStatement.setString(8, movie.getQuality().getDetail());
            callableStatement.setDouble(9, movie.getImdbRate());
            callableStatement.setInt(10, movie.getDirector().getId());
            callableStatement.setString(11, movie.getDirector().getName());
            callableStatement.setBoolean(12, movie.isFree());
            callableStatement.setString(13, genres);
            callableStatement.setString(14, casts);

            callableStatement.execute();
            rs = callableStatement.getResultSet();
            if (rs.next()) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean deleteMovie(Integer id) {
        return false;
    }

    @Override
    public List<Movie> getMovies() {
        String query = "{call getMoviesList()}";
        jdbcTemplate = new JdbcTemplate(dataSource);
        List<Movie> movies = jdbcTemplate.queryForObject(query, new Object[]{}, new MovieListRowMapper());
        return movies;
    }

    @Override
    public Movie getMovieDetail(Integer id) {
        String query = "{call getMovieDetail(?)}";
        jdbcTemplate = new JdbcTemplate(dataSource);
        Movie movie = jdbcTemplate.queryForObject(query, new Object[]{id}, new MovieDetailRowMapper());
        return movie;
    }
}
