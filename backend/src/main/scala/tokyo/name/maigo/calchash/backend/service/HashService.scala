package tokyo.name.maigo.calchash.backend.service

trait HashService {
  def hash(input: String): Int
}
