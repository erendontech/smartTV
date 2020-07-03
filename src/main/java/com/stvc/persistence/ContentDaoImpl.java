package com.stvc.persistence;

import com.stvc.entity.Movie;
import com.stvc.persistence.rowmapper.MovieDetailRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContentDaoImpl implements IContentDao {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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
        jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM movie";

        List<Movie> movies = new ArrayList<Movie>();

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            Movie movie = new Movie();
            movie.setTitle((String)row.get("name"));
            movies.add(movie);
        }
        return movies;
    }

    @Override
    public Movie getMovieDetail(Integer id) {
        String query = "{call getMovieDetail(?)}";
        jdbcTemplate = new JdbcTemplate(dataSource);
        Movie user = jdbcTemplate.queryForObject(query, new Object[]{id}, new MovieDetailRowMapper());
        return user;
    }
}
