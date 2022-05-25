package infrastructure.rdb

import domainModel.user.{IUserRepository, User, UserId, UserName}

import scala.util.Try

class UserRepository extends IUserRepository {
  // TODO: 永続化サービスアクセスする（ORMを利用するのでDAOクラスは用意するが、入出力の型は変えない想定）
  // TODO: ID採番方法をDB依存に変える（ファクトリ）
  // TODO: トランザクションを取り入れる

  def find(userName: UserName): Option[User] =
    if (userName.value == "すでにある名前")
      Some(User("1", userName.value))
    else
      None

  def find(userId: UserId): Option[User] =
    if (userId.value == "1")
      Some(User("1", "すでにある名前"))
    else
      None

  def find(userIds: List[UserId]): List[User] =
    List()

  def save(user: User): Try[User] =
    Try(user)

  def delete(user: User): Try[Unit] =
    Try(Unit)
}
