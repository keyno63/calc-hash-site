package tokyo.name.maigo.calchash.backend

import tokyo.name.maigo.calchash.backend.app.controller.HashController
import tokyo.name.maigo.calchash.backend.service.{Murmur3HashService, RandomStringService}
import zio.*
import zio.http.*
import zio.http.Header.{Origin, AccessControlAllowOrigin}
import zio.http.Middleware.{CorsConfig, cors}

object AppServer extends ZIOAppDefault {
  // setup
  val hashService = Murmur3HashService()
  val idService = RandomStringService(10)
  val hashController = HashController(
    hashService,
    idService
  )

  // middleware settings
  val config: CorsConfig =
    CorsConfig(
      allowedOrigin = {
        case origin if origin == Origin.parse("http://localhost:3000").toOption.get =>
          Some(AccessControlAllowOrigin.Specific(origin))
        case _ => None
      },
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
  ) @@ cors(config)

  def run = Server.serve(routes).provide(Server.default)
}
