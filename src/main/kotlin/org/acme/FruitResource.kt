package org.acme

import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import java.net.URI
import java.util.*


@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class FruitResource(private val repository: FruitRepository) {

  @GET
  fun list(): List<Fruit> = repository.listAll()

  @GET
  @Path("/{id}")
  fun get(id: UUID): Fruit = repository.findById(id) ?: throw NotFoundException()

  @POST
  @Transactional
  fun create(fruit: Fruit): Response {
    repository.persist(fruit)
    return Response.created(URI.create("/fruits/${fruit.id}")).build()
  }

  @DELETE
  @Path("/{id}")
  @Transactional
  fun delete(id: UUID) {
    val fruit = repository.findById(id) ?: throw NotFoundException()
    repository.delete(fruit)
  }

}