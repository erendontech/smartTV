package com.stvc.api;

import com.stvc.api.entity.ResponseSTVC;
import com.stvc.entity.Movie;
import com.stvc.persistence.FacadeDao;
import com.stvc.persistence.IContentDao;
import org.springframework.context.ConfigurableApplicationContext;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/api/content")
public class Content {

    @Inject
    ConfigurableApplicationContext context;

    @GET
    @Path("/get")
    @Produces("application/json")
    public ResponseSTVC getProductInJSON(@Context UriInfo uriInfo) {
        FacadeDao<IContentDao> facadeDao = new FacadeDao<IContentDao>(context,"IContentDao");
        List<Movie> movies = facadeDao.contentDao.getMovies();
        ResponseSTVC<List<Movie>> response = new ResponseSTVC<List<Movie>>(uriInfo.getPath(),Response.Status.ACCEPTED,movies);
        return response;
    }

    @POST
    @Path("/post")
    @Consumes("application/json")
    public Response createProductInJSON(Movie movie) {

        String result = "Product created : " + movie;
        return Response.status(201).entity(result).build();

    }

}
