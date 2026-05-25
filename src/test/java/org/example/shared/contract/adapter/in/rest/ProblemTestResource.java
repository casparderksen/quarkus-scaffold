package org.example.shared.contract.adapter.in.rest;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;

@IfBuildProfile("test")
@Path("/_test/problems")
public class ProblemTestResource {

    @GET
    @Path("/{scenario}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String fail(@PathParam("scenario") String scenario) {
        throw ProblemScenario.fromPath(scenario).newException();
    }
}
