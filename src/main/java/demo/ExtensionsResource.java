package demo;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Set;

@Path("/extensions")
public class ExtensionsResource {

    @Inject
    @RestClient
    ExtensionsService extensionsService;

    @GET
    @Path("/id/{id}")
    public Set<Extension> getById(@PathParam("id") String id) {
        return extensionsService.getById(id);
    }
}