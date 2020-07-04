package com.stvc.persistence;

import com.stvc.entity.Movie;
import com.stvc.persistence.rowmapper.MovieDetailRowMapper;
import com.stvc.persistence.rowmapper.MovieListRowMapper;
import com.stvc.persistence.util.ContentDaoUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
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
        genres = ContentDaoUtil.getGenresFormatDB(movie.getGenres());
        casts = ContentDaoUtil.getCastsFormatDB(movie.getCasts());

        String query = "{call register_movie(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        jdbcTemplate = new JdbcTemplate(dataSource);
        Object[] parems = new Object[]{
                movie.getTitle(),
                movie.getYear(),
                movie.getDescription(),
                movie.getAudio().getId(),
                movie.getAudio().getName(),
                movie.getQuality().getId(),
                movie.getQuality().getName(),
                movie.getQuality().getDetail(),
                movie.getImdbRate(),
                movie.getDirector().getId(),
                movie.getDirector().getName(),
                movie.isFree(),genres,casts};
        jdbcTemplate.update(query, parems);

        return true;
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
