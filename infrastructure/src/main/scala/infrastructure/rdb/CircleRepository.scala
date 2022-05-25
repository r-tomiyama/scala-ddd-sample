package infrastructure.rdb

import domainModel.circle.{Circle, CircleId, CircleName, ICircleRepository}

import scala.util.Try

class CircleRepository extends ICircleRepository {

  def save(circle: Circle): Try[Unit] = ???

  def find(id: CircleId): Option[Circle] = ???

  def find(name: CircleName): Option[Circle] = ???

}
