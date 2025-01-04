package tokyo.name.maigo.calchash.backend.service

import scala.util.hashing.MurmurHash3

class Murmur3HashService extends HashService {
  override def hash(input: String): Int = MurmurHash3.stringHash(input)
}
