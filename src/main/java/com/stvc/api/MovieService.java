package com.stvc.api;

import com.stvc.api.entity.ResponseSTVC;
import com.stvc.api.handlers.MovieHandlers;
import com.stvc.api.request.DeleteMovieRequest;
import com.stvc.api.responses.MovieListResponse;
import com.stvc.api.validators.MovieValidator;
import com.stvc.api.validators.ResultValidation;
import com.stvc.entity.Movie;
import com.stvc.persistence.FacadeDao;
import com.stvc.persistence.IContentDao;
import org.springframework.context.ConfigurableApplicationContext;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.ResourceBundle;

@Path("/api/movie")
public class MovieService {

    @Inject
    ConfigurableApplicationContext context;

    MovieHandlers mh = new MovieHandlers();

    @GET
    @Produces("application/json")
    public ResponseSTVC getMovies() {
        FacadeDao<IContentDao> facadeDao = new FacadeDao<IContentDao>(context, "IContentDao");
        MovieListResponse mData;
        List<Movie> movies = facadeDao.contentDao.getMovies();
        ResponseSTVC<MovieListResponse> response;
        if (movies != null) {
            mData = mh.processListMovies(movies);
            response = new ResponseSTVC<MovieListResponse>(Response.Status.ACCEPTED, mData);
        } else {
            response = new ResponseSTVC<MovieListResponse>(Response.Status.NO_CONTENT, null);
        }

        return response;
    }

    @GET
    @Path("/{id_movie}")
    @Produces("application/json")
    public ResponseSTVC getMovieDetail(@PathParam("id_movie") Integer idMovie) {
        FacadeDao<IContentDao> facadeDao = new FacadeDao<IContentDao>(context, "IContentDao");
        ResponseSTVC<Movie> response;
        if (idMovie != null && idMovie >= 1) {
            Movie movie = facadeDao.contentDao.getMovieDetail(idMovie);
            if (movie != null) {
                response = new ResponseSTVC<Movie>(Response.Status.ACCEPTED, movie);
            } else {
                response = new ResponseSTVC<Movie>(Response.Status.NO_CONTENT, movie);
            }
        } else {
            response = new ResponseSTVC<Movie>(Response.Status.BAD_REQUEST, null);
        }

        return response;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseSTVC createMovie(Movie movie) {
        FacadeDao<IContentDao> facadeDao = new FacadeDao<IContentDao>(context, "IContentDao");
        ResultValidation rv;
        boolean movieSaved;

        rv = MovieValidator.isValidMovie(movie);
        ResponseSTVC<String> response;
        if (rv.isValid) {
            movieSaved = facadeDao.contentDao.createMovie(movie);
            if (movieSaved) {
                response = new ResponseSTVC<String>(Response.Status.CREATED, null);
            } else {
                response = new ResponseSTVC<String>(Response.Status.INTERNAL_SERVER_ERROR, ResourceBundle.getBundle("message").getString("TRY_AGAIN_LATER"));
            }
        } else {
            response = new ResponseSTVC<String>(Response.Status.BAD_REQUEST, rv.error_messages);
        }

        return response;
    }

    @DELETE
    @Consumes("application/json")
    @Produces("application/json")
    public ResponseSTVC deleteMovie(DeleteMovieRequest request) {
        FacadeDao<IContentDao> facadeDao = new FacadeDao<IContentDao>(context, "IContentDao");
        boolean deleted;
        ResponseSTVC<String> response;

        if (request.getId_movie() != null && request.getId_movie() >= 1) {
            deleted = facadeDao.contentDao.deleteMovie(request.getId_movie());
            if (deleted) {
                response = new ResponseSTVC<String>(Response.Status.ACCEPTED, ResourceBundle.getBundle("message").getString("MOVIE_DELETED"));
            } else {
                response = new ResponseSTVC<String>(Response.Status.INTERNAL_SERVER_ERROR, ResourceBundle.getBundle("message").getString("TRY_AGAIN_LATER"));
            }

        } else {
            response = new ResponseSTVC<String>(Response.Status.BAD_REQUEST, ResourceBundle.getBundle("message").getString("MOVIE_INVALID_ID"));
        }

        return response;
    }


    /**
     * The Ideal scenario is to have here a Solr o Elastic search implementation
     *
     * @param keyword
     * @return
     */
    @GET
    @Path("/search/{keyword}")
    @Produces("application/json")
    public ResponseSTVC getMoviesByKeyword(@PathParam("keyword") String keyword) {
        FacadeDao<IContentDao> facadeDao = new FacadeDao<IContentDao>(context, "IContentDao");
        MovieListResponse mData;
        ResponseSTVC<MovieListResponse> response;

        if (keyword != null && keyword.length() >= 3) {
            List<Movie> movies = facadeDao.contentDao.getMoviesAllData();
            movies = mh.filtersMoviesByKeyword(keyword, movies);
            if (movies.size() > 0) {
                mData = mh.processListMovies(movies);
                response = new ResponseSTVC<MovieListResponse>(Response.Status.ACCEPTED, mData);
            } else {
                response = new ResponseSTVC<MovieListResponse>(Response.Status.NO_CONTENT,ResourceBundle.getBundle("message").getString("MOVIE_NOT_FUND_BY_KEYWORD"), null);
            }
        }else{
            response = new ResponseSTVC<MovieListResponse>(Response.Status.BAD_REQUEST,ResourceBundle.getBundle("message").getString("MOVIE_INVALID_KEYWORD"), null);
        }

        return response;
    }



}
