package domainService

import com.google.inject.Inject
import domainModel.circle.{Circle, ICircleRepository}

class CircleService @Inject() (circleRepository: ICircleRepository) {
  def exist(circle: Circle): Boolean =
    circleRepository.find(circle.name).nonEmpty
}
