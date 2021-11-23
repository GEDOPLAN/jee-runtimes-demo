package de.gedoplan.showcase.rest;

import de.gedoplan.showcase.entity.Person;
import de.gedoplan.showcase.persistence.PersonRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationScoped
@Path("person")
public class PersonResource {

  @Inject
  PersonRepository personRepository;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Person> getAll() {
    return this.personRepository.findAll();
  }

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Person getById(@PathParam("id") Integer id) {
    Person person = this.personRepository.findById(id);
    if (person != null) {
      return person;
    }

    throw new NotFoundException();
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public void updatePerson(@PathParam("id") Integer id, Person Person) {
    if (!id.equals(Person.getId())) {
      throw new BadRequestException("id of updated object must be unchanged");
    }

    this.personRepository.merge(Person);
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createPerson(Person Person, @Context UriInfo uriInfo) throws URISyntaxException {
    if (Person.getId() != null) {
      throw new BadRequestException("id of new entry must not be set");
    }

    this.personRepository.persist(Person);

    URI createdUri = uriInfo.getAbsolutePathBuilder().path("{" + "id" + "}").resolveTemplate("id", Person.getId()).build();
    return Response.created(createdUri).build();
  }

  @DELETE
  @Path("{id}")
  public void deletePerson(@PathParam("id") Integer id) {
    this.personRepository.removeById(id);
  }

  @PostConstruct
  void fillDb() {
    if (this.personRepository.findAll().isEmpty()) {
      this.personRepository.persist(new Person("Duck", "Dagobert"));
      this.personRepository.persist(new Person("Duck", "Donald"));
    }
  }

}
