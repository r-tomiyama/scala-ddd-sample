package domainModel.user

import scala.util.{Success, Try}

class UserRepositoryForTest private (
    userFoundByName: Option[User],
    userFoundById: Option[User],
    userSaved: Try[User],
    deleteResult: Try[Unit]
) extends IUserRepository {

  def find(name: UserName): Option[User] = userFoundByName
  def find(userId: UserId): Option[User] = userFoundById
  def find(userIds: List[UserId]): List[User] = List()
  def save(user: User): Try[User] = userSaved
  def delete(user: User): Try[Unit] = deleteResult
}

object UserRepositoryForTest {
  val user = User(UserId("id"), UserName("なまえ"))

  def apply(
      userFoundByName: Option[User] = Some(user),
      userFoundById: Option[User] = Some(user),
      userSaved: Try[User] = Success(user),
      deleteResult: Try[Unit] = Success(Unit)
  ): UserRepositoryForTest =
    new UserRepositoryForTest(
      userFoundByName,
      userFoundById,
      userSaved,
      deleteResult
    )
}
