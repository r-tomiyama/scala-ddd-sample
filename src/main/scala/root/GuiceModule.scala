package root

import application.{CircleUsecase, UserUsecase}
import com.google.inject.AbstractModule
import domainModel.circle.{
  CircleFullSpecification,
  ICircleFactory,
  ICircleRepository
}
import domainModel.user.IUserRepository
import domainService.{CircleService, UserService}
import infrastructure.rdb.{CircleFactory, CircleRepository, UserRepository}
import net.codingwell.scalaguice.ScalaModule
import presentation.Cli

class GuiceModule extends AbstractModule with ScalaModule {

  override def configure(): Unit = {
    bind[Cli]

    bind[UserUsecase]
    bind[CircleUsecase]

    bind[UserService]
    bind[CircleService]
    bind[CircleFullSpecification]

    bind[IUserRepository].to[UserRepository]
    bind[ICircleRepository].to[CircleRepository]
    bind[ICircleFactory].to[CircleFactory]
  }

}
