package tokyo.name.maigo.calchash.backend.service

import scala.util.Random

class RandomStringService(private val size: Int) extends RandomIdService {
  val chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

  override def randomId(): String = {
    val random = new Random
    (1 to size).map(_ => chars(random.nextInt(chars.length))).mkString
  }
}
