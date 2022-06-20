package ch.hug.simed.covid.frontend.client.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

@Produces( MediaType.APPLICATION_JSON )
@Consumes(MediaType.APPLICATION_JSON)
//@Path("db/services/endpoint/info")
public interface InfoServiceAsync extends RestService {
    @GET
    public void getInfo(MethodCallback<List<Info>> callback);
}
