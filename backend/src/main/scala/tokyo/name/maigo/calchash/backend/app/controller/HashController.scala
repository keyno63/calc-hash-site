package tokyo.name.maigo.calchash.backend.app.controller

import tokyo.name.maigo.calchash.backend.service.{HashService, RandomIdService}

case class HashController(private val hashService: HashService, private val randomIdService: RandomIdService) {
  def getHash(id: String): Int = {
    hashService.hash(id)
  }

  def createAndGetHash(): (String, Int) = {
    val id = randomIdService.randomId()
    (id, hashService.hash(id))
  }
}