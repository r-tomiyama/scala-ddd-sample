package application

import com.google.inject.Inject
import domainModel.circle.{
  CircleFullSpecification,
  CircleId,
  ICircleFactory,
  ICircleRepository
}
import domainModel.user.{IUserRepository, UserId}
import domainService.{CircleService}

import scala.util.{Failure, Success, Try}

class CircleUsecase @Inject() (
    userRepository: IUserRepository,
    circleRepository: ICircleRepository,
    circleFactory: ICircleFactory,
    circleService: CircleService,
    circleFullSpecification: CircleFullSpecification
) {

  def create(userId: String, circleName: String): Try[Unit] =
    for {
      userId <- UserId.from(userId)
      user <- userRepository.find(userId) match {
        case Some(u) => Success(u)
        case None    => Failure(new RuntimeException("存在しないユーザーID"))
      }
      circle <- circleFactory.from(circleName, user.id)
      _ <- if (circleService.exist(circle))
        Failure(new RuntimeException("すでに利用されている名前"))
      else
        Success(())
      result <- circleRepository.save(circle)
    } yield result

  def join(userId: String, circleId: String): Try[Unit] =
    for {
      userId <- UserId.from(userId)
      user <- userRepository.find(userId) match {
        case Some(u) => Success(u)
        case None    => Failure(new RuntimeException("存在しないユーザーID"))
      }
      circle <- circleRepository.find(CircleId(circleId)) match {
        case Some(c) => Success(c)
        case None    => Failure(new RuntimeException("存在しないユーザーID"))
      }
      updatedCircle <- circle.addUser(user.id)
      result <- circleRepository.save(updatedCircle)
    } yield result

  def join2(userId: String, circleId: String): Try[Unit] = {
    // specific使用例
    for {
      userId <- UserId.from(userId)
      user <- userRepository.find(userId) match {
        case Some(u) => Success(u)
        case None    => Failure(new RuntimeException("存在しないユーザーID"))
      }
      circle <- circleRepository.find(CircleId(circleId)) match {
        case Some(c) => Success(c)
        case None    => Failure(new RuntimeException("存在しないサークルID"))
      }
      updatedCircle <- circle.addUser(user.id)
      result <- if (circleFullSpecification.isSatisfiedBy(updatedCircle))
        circleRepository.save(updatedCircle)
      else Failure(new RuntimeException("仕様外"))
    } yield result
  }
}
