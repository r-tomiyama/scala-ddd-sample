package infrastructure.rdb

import domainModel.user.{User, UserId}
import org.scalatest.FlatSpec

class UserRepositoryForTestSpec extends FlatSpec {

  val userRepository = new UserRepository

  "find" should "一致するユーザーを返す（名前検索）" in {
    val user = User("1", "すでにある名前")
    assert(userRepository.find(user.name).contains(user))
  }

  it should "一致するユーザーを返す（ID検索）" in {
    val user = User("1", "すでにある名前")
    assert(userRepository.find(UserId("1")).contains(user))
  }

  it should "Noneを返す（名前検索）" in {
    val user = User("id", "なまえ")
    assert(userRepository.find(user.name).isEmpty)
  }

  it should "Noneを返す（ID検索）" in {
    val user = User("2", "なまえ")
    assert(userRepository.find(user.id).isEmpty)
  }

  "save" should "ユーザーを保存し、ユーザーを返す" in {
    val user = User("id", "なまえ")
    assert(userRepository.save(user).get.eq(user))

    //val savedUser = userRepository.find(user.id)
    //assert(savedUser.get.eq(user))
  }

  "delete" should "ユーザーを削除する" in {
    val user = User("id", "なまえ")
    assert(userRepository.delete(user).isSuccess)

    // assert(userRepository.find(user.id).isEmpty)
  }

}
