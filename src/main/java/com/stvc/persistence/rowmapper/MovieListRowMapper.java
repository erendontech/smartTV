package com.stvc.persistence.rowmapper;

import com.stvc.entity.Genre;
import com.stvc.entity.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MovieListRowMapper implements RowMapper<List<Movie>> {

    @Override
    public List<Movie> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<Movie> movies = new ArrayList<Movie>();
        Movie movie;
        String genresDB;
        String[] genresWithId;
        String[] aux;
        Genre genre;
        Set<Genre> genres;
        StringBuilder description;

        do{
            movie = new Movie();
            movie.setTitle(rs.getString("name"));
            description = new StringBuilder(rs.getString("description"));
            description = new StringBuilder(description.substring(0,150));
            description.append(" ...");
            movie.setDescription(description.toString());
            movie.setYear(rs.getInt("year"));
            movie.setFree(rs.getBoolean("free"));
            genresDB = rs.getString("genres");
            genresWithId = genresDB.split(",");
            genres = new TreeSet<Genre>();
            for(String item : genresWithId){
                genre = new Genre();
                aux = item.split("-");
                genre.setId(Integer.valueOf(aux[0]));
                genre.setName(aux[1]);
                genres.add(genre);
            }
            movie.setGenres(genres);
            movies.add(movie);
        }while(rs.next());
        return movies;
    }
}