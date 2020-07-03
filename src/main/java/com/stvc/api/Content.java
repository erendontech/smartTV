package com.stvc.api;

import com.stvc.api.entity.ResponseSTVC;
import com.stvc.api.validators.MovieValidator;
import com.stvc.api.validators.ResultValidation;
import com.stvc.entity.Movie;
import com.stvc.persistence.FacadeDao;
import com.stvc.persistence.IContentDao;
import org.springframework.context.ConfigurableApplicationContext;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/api/movie")
public class Content {

    @Inject
    ConfigurableApplicationContext context;

    @GET
    @Produces("application/json")
    public ResponseSTVC getMovies(@Context UriInfo uriInfo) {
        FacadeDao<IContentDao> facadeDao = new FacadeDao<IContentDao>(context,"IContentDao");
        List<Movie> movies = facadeDao.contentDao.getMovies();
        ResponseSTVC<List<Movie>> response = new ResponseSTVC<List<Movie>>(uriInfo.getPath(),Response.Status.ACCEPTED,movies);
        return response;
    }

    @GET
    @Path("/{id_movie}")
    @Produces("application/json")
    public ResponseSTVC getMovieDetail(@PathParam("id_movie") Integer idMovie, @Context UriInfo uriInfo) {
        FacadeDao<IContentDao> facadeDao = new FacadeDao<IContentDao>(context,"IContentDao");
        Movie movie = facadeDao.contentDao.getMovieDetail(idMovie);
        ResponseSTVC<Movie> response = new ResponseSTVC<Movie>(uriInfo.getPath(),Response.Status.ACCEPTED,movie);
        return response;
    }

    @POST
    @Path("/post")
    @Consumes("application/json")
    public ResponseSTVC createMovie(Movie movie,@Context UriInfo uriInfo) {
        FacadeDao<IContentDao> facadeDao = new FacadeDao<IContentDao>(context,"IContentDao");
        ResultValidation rv;
        boolean movieSaved;
        rv = MovieValidator.isValidMovie(movie);
        ResponseSTVC<String> response;

        if(rv.result){
            movieSaved = facadeDao.contentDao.createMovie(movie);
            if(movieSaved){
                response = new ResponseSTVC<String>(uriInfo.getPath(),Response.Status.ACCEPTED,null);
            }else{
                response = new ResponseSTVC<String>(uriInfo.getPath(),Response.Status.INTERNAL_SERVER_ERROR,"Please try again later");
            }
        }else{
            response = new ResponseSTVC<String>(uriInfo.getPath(),Response.Status.BAD_REQUEST,rv.error_messages);
        }

        return response;
    }

    @DELETE
    @Path("/delete")
    @Consumes("application/json")
    public ResponseSTVC deleteMovie(Integer id,@Context UriInfo uriInfo) {
        FacadeDao<IContentDao> facadeDao = new FacadeDao<IContentDao>(context,"IContentDao");
        boolean deleted;
        ResponseSTVC<String> response;

        if(id != null && id != 0){
            deleted = facadeDao.contentDao.deleteMovie(id);
            if(deleted){
                response = new ResponseSTVC<String>(uriInfo.getPath(),Response.Status.ACCEPTED,null);
            }else{
                response = new ResponseSTVC<String>(uriInfo.getPath(),Response.Status.INTERNAL_SERVER_ERROR,"Please try again later");
            }

        }else{
            response = new ResponseSTVC<String>(uriInfo.getPath(),Response.Status.BAD_REQUEST,"Id not valid");
        }

        return response;
    }

}
