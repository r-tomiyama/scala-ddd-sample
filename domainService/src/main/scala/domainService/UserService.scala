package domainService

import com.google.inject.Inject
import domainModel.user.{IUserRepository, User}

class UserService @Inject() (userRepository: IUserRepository) {
  def exist(user: User): Boolean =
    userRepository.find(user.name).nonEmpty

}
