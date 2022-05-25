package application

import com.google.inject.Inject
import domainModel.user.{IUserRepository, User, UserId}
import domainService.UserService

import scala.util.{Failure, Success, Try}

class UserUsecase @Inject() (
    userRepository: IUserRepository,
    userService: UserService
) {

  def create(name: String): Try[dto.User] =
    (for {
      user <- User.from(name)
      _ <- if (userService.exist(user))
        Failure(new RuntimeException("すでに利用されている名前"))
      else Success(())
      _ <- userRepository.save(user)
    } yield user).map(dto.User.from)

  def find(id: String): Try[dto.User] =
    for {
      userId <- UserId.from(id)
      user <- userRepository.find(userId) match {
        case Some(u) => Success(dto.User.from(u))
        case None    => Failure(new RuntimeException("存在しないID"))
      }
    } yield user

  def update(id: String, name: String): Try[dto.User] =
    (for {
      userId <- UserId.from(id)
      user <- userRepository.find(userId) match {
        case Some(u) => Success(u)
        case None    => Failure(new RuntimeException("存在しないID"))
      }
      newUser <- user.changeName(name)
      _ <- if (userService.exist(newUser))
        Failure(new RuntimeException("すでに利用されている名前"))
      else Success(())
      _ <- userRepository.save(newUser)
    } yield newUser).map(dto.User.from)

  def delete(id: String): Try[Unit] =
    for {
      userId <- UserId.from(id)
      user <- userRepository.find(userId) match {
        case Some(u) => Success(u)
        case None    => Failure(new RuntimeException("存在しないID"))
      }
      result <- userRepository.delete(user)
    } yield result
}
