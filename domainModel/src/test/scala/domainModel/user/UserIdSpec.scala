package domainModel.user

import org.scalatest.FlatSpec
import org.scalatest.Matchers._

class UserIdSpec extends FlatSpec {

  "apply" should "例外を投げる（空文字）" in {
    an[RuntimeException] should be thrownBy UserId("")
  }

  "from" should "ユーザーを作成する" in {
    assert(UserId.from("id").isSuccess)
  }

  it should "ユーザーに失敗する" in {
    assert(User.from("").isFailure)
  }

}
