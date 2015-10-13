package com.TempoTeste.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.TempoTeste.data.WeatherRepository;
import com.TempoTeste.model.Weather;
import com.TempoTeste.service.WeatherRegistration;


@Path("/weather")
@RequestScoped
@Stateful
public class WeatherService {
       
    @Inject
    private WeatherRepository repository;
    
    
    @Inject
    private WeatherRegistration registration;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Weather> listAllWeather() {
        return repository.findAllOrderedByData();
    }

    @GET
    @Path("/{data:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Weather lookupWeatherByData(@PathParam("data") Date data) {
    	Weather member = repository.findByData(data);
        if (member == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return member;
    }
    /**
     * Creates a new member from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addOrUpdateTempo(Weather tempo) {

        Response.ResponseBuilder builder = null;

        try {

        	Weather old = DataAlreadyExists(tempo.getData());
        	if (old != null){
        		tempo.setId(old.getId());
        		registration.update(tempo);
        	} else {
        		registration.register(tempo);
        	}

            builder = Response.ok().entity(tempo);
        } catch (Exception e) {
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }


    public Weather DataAlreadyExists(Date data) {
        try {
            return repository.findByData(data);
        } catch (NoResultException e) {
        }
        return null;
    }
    
    
}
