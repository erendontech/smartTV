package com.stvc.api.validators;

import com.stvc.entity.Movie;

public class MovieValidator {

    public static ResultValidation isValidMovie(Movie movie){
        ResultValidation resultValidation = new ResultValidation();
        boolean isValid = true;
        StringBuilder messages;

        if(movie.getTitle().isEmpty()){
            isValid = false;
        }


        return resultValidation;
    }
}
