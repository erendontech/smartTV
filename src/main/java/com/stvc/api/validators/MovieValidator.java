package com.stvc.api.validators;

import com.stvc.entity.Cast;
import com.stvc.entity.Genre;
import com.stvc.entity.Movie;

import java.util.Calendar;
import java.util.ResourceBundle;

public class MovieValidator {

    static String init = "Invalid: ";

    public static ResultValidation isValidMovie(Movie movie) {
        ResultValidation resultValidation = new ResultValidation();
        boolean isValid = true;
        StringBuilder messages = new StringBuilder(init);

        if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
            isValid = false;
            appendError(messages,"MOVIE_INVALID_TITLE");
        }
        if (movie.getYear() == null || (movie.getYear() <= 1500 || movie.getYear() > Calendar.getInstance().get(Calendar.YEAR))) {
            isValid = false;
            appendError(messages,"MOVIE_INVALID_YEAR");
        }
        if (movie.getGenres() == null || movie.getGenres().size() == 0) {
            isValid = false;
            appendError(messages,"MOVIE_INVALID_GENRES");
        }else{
            for(Genre g : movie.getGenres()){
                if(g.getId() == 0 && (g.getName() == null || g.getName().isEmpty())){
                    isValid = false;
                    appendError(messages,"MOVIE_INVALID_GENRES");
                    break;
                }
            }
        }
        if (movie.getDescription() == null || movie.getDescription().isEmpty()) {
            isValid = false;
            appendError(messages,"MOVIE_INVALID_DESCRIPTION");
        }
        if (movie.getAudio() == null || (movie.getAudio().getId() == 0 && (movie.getAudio().getName() == null || movie.getAudio().getName().isEmpty()))) {
            isValid = false;
            appendError(messages,"MOVIE_INVALID_AUDIO");
        }
        if (movie.getQuality() == null || (movie.getQuality().getId() == 0 && (movie.getQuality().getName() == null || movie.getQuality().getName().isEmpty()))) {
            isValid = false;
            appendError(messages,"MOVIE_INVALID_QUALITY");
        }
        if (movie.getImdbRate() == null || movie.getImdbRate() < 0) {
            isValid = false;
            appendError(messages,"MOVIE_INVALID_IMDB");
        }
        if (movie.getDirector() == null || (movie.getDirector().getId() == 0 && (movie.getDirector().getName() == null || movie.getDirector().getName().isEmpty()))) {
            isValid = false;
            appendError(messages,"MOVIE_INVALID_DIRECTOR");
        }
        if (movie.getCasts() == null || movie.getCasts().size() == 0) {
            isValid = false;
            appendError(messages,"MOVIE_INVALID_CAST");
        }else{
            for(Cast c : movie.getCasts()){
                if(c.getId() == 0 && (c.getName() == null || c.getName().isEmpty())){
                    isValid = false;
                    appendError(messages,"MOVIE_INVALID_CAST");
                    break;
                }
            }
        }

        resultValidation.isValid = isValid;
        resultValidation.error_messages = isValid ? null : messages.append(".").toString();

        return resultValidation;
    }

    public static void appendError(StringBuilder _in, String messagekey){
        if(_in.length()>init.length()){
            _in.append(", ");
        }
        _in.append(ResourceBundle.getBundle("message").getString(messagekey));

    }
}
