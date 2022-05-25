package domainService

import domainModel.user.{User, UserRepositoryForTest}
import org.scalatest.FlatSpec

class UserServiceSpec extends FlatSpec {

  def userServiceForTest(
      userRepository: UserRepositoryForTest = UserRepositoryForTest()
  ): UserService = {
    new UserService(userRepository)
  }

  "exist" should "trueを返す" in {
    val targetUser = User("id", "なまえ")
    val service = userServiceForTest()
    assert(service.exist(targetUser))
  }

  "exist" should "falseを返す" in {
    val targetUser = User("id", "なまえ")
    val service =
      userServiceForTest(UserRepositoryForTest(userFoundByName = None))
    assert(!service.exist(targetUser))
  }

}
