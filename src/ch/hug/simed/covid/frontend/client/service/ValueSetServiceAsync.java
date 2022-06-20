package ch.hug.simed.covid.frontend.client.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import ch.hug.simed.covid.frontend.client.json.JSONValueset;

@Produces( MediaType.APPLICATION_JSON )
@Consumes(MediaType.APPLICATION_JSON)
public interface ValueSetServiceAsync extends RestService {
    @POST
    public void valueset(JSONValueset request, 
            MethodCallback<List<Reponse>> callback);
}
