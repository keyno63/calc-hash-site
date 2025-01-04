package tokyo.name.maigo.calchash.backend

import tokyo.name.maigo.calchash.backend.app.controller.HashController
import tokyo.name.maigo.calchash.backend.service.{Murmur3HashService, RandomStringService}
import zio.*
import zio.http.*

object AppServer extends ZIOAppDefault {
  // setup
  val hashService = Murmur3HashService()
  val idService = RandomStringService(10)
  val hashController = HashController(
    hashService,
    idService
  )

  // route
  val routes =
    Routes(
      Method.GET / Root -> handler(Response.text("Greetings at your service")),
      Method.GET / "hash" -> handler{ (req: Request) =>
        val id = req.queryParamToOrElse("id", "")
        val hash = hashController.getHash(id)
        Response.json(s"""{"id": "$id", "value":"$hash"}""")
      },
      Method.GET / "random" -> handler{ (req: Request) =>
        val (id, hash) = hashController.createAndGetHash()
        Response.json(s"""{"id": "$id", "value": "$hash"}""")
      }
  )

  def run = Server.serve(routes).provide(Server.default)
}
