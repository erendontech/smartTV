package com.stvc.persistence.rowmapper;

import com.stvc.entity.Cast;
import com.stvc.entity.Genre;

import java.util.Set;
import java.util.TreeSet;

public class RowMapperUtil {

    public static Set<Genre> getGenresSet(String inGenres){
        String[] genresWithId;
        String[] aux;
        Genre genre;
        Set<Genre> genres;

        genresWithId = inGenres.split(",");
        genres = new TreeSet<Genre>();
        for(String item : genresWithId){
            genre = new Genre();
            aux = item.split("-");
            genre.setId(Integer.valueOf(aux[0]));
            genre.setName(aux[1]);
            genres.add(genre);
        }
        return genres;
    }

    public static Set<Cast> getCastSet(String inCast){
        String[] castWithId;
        String[] aux;
        Cast cast;
        Set<Cast> casts;

        castWithId = inCast.split(",");
        casts = new TreeSet<Cast>();
        for(String item : castWithId){
            cast = new Cast();
            aux = item.split("-");
            cast.setId(Integer.valueOf(aux[0]));
            cast.setName(aux[1]);
            casts.add(cast);
        }
        return casts;
    }
}
