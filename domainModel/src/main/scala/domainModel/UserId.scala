package domainModel

case class UserId private (value: String)

object UserId {
  def apply(): UserId = {
    new UserId("id")
  }

  def apply(value: String): UserId = {
    if (value.length < 1) {
      throw new Exception("IDは1文字以上")
    }
    new UserId(value)
  }
}
