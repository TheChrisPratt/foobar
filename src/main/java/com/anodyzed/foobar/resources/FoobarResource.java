package com.anodyzed.foobar.resources;

import com.anodyzed.foobar.services.FoobarService;

import com.anodyzed.foobar.model.Foobar;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * FoobarResource - Example Resource
 *
 * @author Chris Pratt
 * @since 2020-03-07
 */
@Path("/foobars")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FoobarResource extends BaseResource {

  @Resource
  private FoobarService foobarService;

  @GET
  public List<Foobar> getFoobars () {
    return foobarService.getFoobars();
  } //getFoobars

  @POST
  public Response createFoobar (Foobar foobar) {
    foobarService.save(foobar);
    return created(foobar.getId());
  } //createFoobar

} //*FoobarResource
