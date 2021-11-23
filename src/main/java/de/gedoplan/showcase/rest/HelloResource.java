package de.gedoplan.showcase.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("hello")
public class HelloResource {

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String get() {
    return "Hello!";
  }
}
