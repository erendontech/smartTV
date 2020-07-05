package com.stvc.persistence.rowmapper;

import com.stvc.entity.Audio;
import com.stvc.entity.Director;
import com.stvc.entity.Movie;
import com.stvc.entity.Quality;
import com.stvc.persistence.util.RowMapperUtil;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieListAllDataRowMapper implements RowMapper<List<Movie>> {

    @Override
    public List<Movie> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<Movie> movies = new ArrayList<Movie>();
        Movie movie;

        do{
            movie = new Movie();
            Audio audio = new Audio();
            Quality quality = new Quality();
            Director director = new Director();

            movie.setId(rs.getInt("movie_id"));
            movie.setTitle(rs.getString("movie_name"));
            movie.setYear(rs.getInt("year"));
            movie.setDescription(rs.getString("description"));

            audio.setId(rs.getInt("audio_id"));
            audio.setName(rs.getString("audio_name"));
            movie.setAudio(audio);

            quality.setId(rs.getInt("quality_id"));
            quality.setName(rs.getString("quality_name"));
            quality.setDetail(rs.getString("quality_detail"));
            movie.setQuality(quality);

            movie.setImdbRate(rs.getDouble("imdb_rate"));

            director.setId(rs.getInt("director_id"));
            director.setName(rs.getString("director_name"));
            movie.setDirector(director);

            movie.setFree(rs.getBoolean("free"));
            movie.setFree(rs.getBoolean("free"));

            movie.setGenres(RowMapperUtil.getGenresSet(rs.getString("genres")));
            movie.setCasts(RowMapperUtil.getCastSet(rs.getString("casts")));

            movies.add(movie);
        }while(rs.next());
        return movies;
    }
}