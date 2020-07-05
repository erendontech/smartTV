package com.stvc.api.handlers;

import com.stvc.api.responses.MovieListResponse;
import com.stvc.entity.Cast;
import com.stvc.entity.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieHandlers {

    public MovieListResponse processListMovies(List<Movie> movies) {
        MovieListResponse result = new MovieListResponse();
        result.setQuantity(movies.size());
        result.setMovies(movies);

        return result;
    }


    public List<Movie> filtersMoviesByKeyword(String keyword, List<Movie> movies){
        List<Movie> result = new ArrayList<Movie>();
        boolean fCast;
        for(Movie movie : movies){
            if(contains(keyword,movie.getTitle())){
                result.add(movie);
                continue;
            }
            if(contains(keyword,movie.getDirector().getName())){
                result.add(movie);
                continue;
            }
            fCast = false;
            for(Cast c : movie.getCasts()){
                if(contains(keyword,c.getName())){
                    fCast = true;
                    break;
                }
            }
            if(fCast){
                result.add(movie);
            }

        }
        return result;
    }

    public static boolean contains(String key, String s){
        return s.toUpperCase().contains(key.toUpperCase());
    }
}
