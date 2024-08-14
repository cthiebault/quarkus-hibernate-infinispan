package org.acme

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import jakarta.ws.rs.core.MediaType.APPLICATION_JSON
import org.junit.jupiter.api.Test
import java.util.UUID.randomUUID

@QuarkusTest
class FruitResourceTest {

  @Test
  fun createAndRead() {

    val apple = Fruit(id = randomUUID(), name = "Apple")

    given()
      .header("Content-Type", APPLICATION_JSON)
      .body(apple)
      .`when`().post("/fruits")
      .then().statusCode(201)

    given()
      .`when`()["/fruits/${apple.id}"]
      .then().statusCode(200)

  }

}