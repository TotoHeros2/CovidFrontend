package ch.hug.simed.covid.frontend.client.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

	

@Produces( MediaType.APPLICATION_JSON )
@Consumes(MediaType.APPLICATION_JSON)

public interface ResourceServiceAsync extends RestService {
    @GET
    public void getResources(MethodCallback<List<CovidResource>> callback);
}

