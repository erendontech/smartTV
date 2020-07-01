package com.stvc.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.stvc.api.entity.ResponseSTVC;
import com.stvc.entity.Movie;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/api/content")
public class Content {

    @GET
    @Path("/get")
    @Produces("application/json")
    public ResponseSTVC getProductInJSON(@Context UriInfo uriInfo) {

        Movie movie = new Movie();
        movie.setTitle("Un jefe en pañales");
        movie.setDescription("El bebé más inusual llega a la casa de Tim en un taxi, vestido de traje y cargando un maletín. La rivalidad instantánea entre hermanos debe ser puesta de lado cuando el jefe en pañales hace equipo con su hermano de siete años para detener el plan maligno del presidente de Puppy Co.");

        ResponseSTVC<Movie> response = new ResponseSTVC<Movie>(uriInfo.getPath(),Response.Status.ACCEPTED,movie);

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
