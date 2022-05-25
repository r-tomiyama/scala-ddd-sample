package application

import domainModel.user.UserRepositoryForTest
import domainService.UserService
import org.scalatest.FlatSpec
import org.scalatest.Matchers._

import scala.util.{Failure, Success}

class UserUsecaseSpec extends FlatSpec {

  def userUsecaseForTest(
      userRepository: UserRepositoryForTest = UserRepositoryForTest()
  ): UserUsecase = {
    val userService = new UserService(userRepository)
    new UserUsecase(userRepository, userService)
  }

  "create" should "ユーザーを作成し、ユーザー情報を返す" in {
    assert(
      userUsecaseForTest(
        UserRepositoryForTest(userFoundByName = None)
      ).create("なまえ") == Success(dto.User("id", "なまえ"))
    )
  }

  it should "ユーザーを作成せず、失敗を返す （ユーザー情報が不正）" in {
    assert(
      userUsecaseForTest(UserRepositoryForTest(userFoundByName = None))
        .create("なま")
        .isFailure
    )
  }

  it should "ユーザーを作成せず、失敗を返す（すでに存在するユーザーの場合）" in {
    assert(userUsecaseForTest().create("すでにある名前").isFailure)
  }

  it should "ユーザーを作成せず、失敗を返す（DB保存の失敗）" in {
    assert(
      userUsecaseForTest(
        UserRepositoryForTest(userSaved = Failure(new Exception))
      ).create("なまえ").isFailure
    )
  }

  "find" should "ユーザーを検索し、ユーザー情報を返す" in {
    assert(userUsecaseForTest().find("id") == Success(dto.User("id", "なまえ")))
  }

  it should "ユーザーを検索せず、失敗を返す" in {
    assert(userUsecaseForTest().find("").isFailure)
  }

  "update" should "ユーザーを名更新し、ユーザー情報を返す" in {
    assert(
      userUsecaseForTest(
        UserRepositoryForTest(userFoundByName = None)
      ).update("id", "新しい名前") == Success(dto.User("id", "新しい名前"))
    )
  }

  it should "ユーザーを更新せず、失敗を返す（存在しないID）" in {
    assert(
      userUsecaseForTest(
        UserRepositoryForTest(userSaved = Failure(new Exception))
      ).update("999", "新しい名前").isFailure
    )
  }

  it should "ユーザーを更新せず、失敗を返す（すでに存在するユーザー名の場合）" in {
    assert(userUsecaseForTest().update("1", "すでにある名前").isFailure)
  }

  it should "ユーザーを更新せず、失敗を返す（不正な名前）" in {
    assert(
      userUsecaseForTest(
        UserRepositoryForTest(userFoundByName = None)
      ).update("id", "なま").isFailure
    )
  }

  "delete" should "ユーザーを削除し、成功を返す" in {
    assert(userUsecaseForTest().delete("id").isSuccess)
  }

  it should "ユーザーを削除せず、失敗を返す（存在しないユーザー）" in {
    assert(
      userUsecaseForTest(
        UserRepositoryForTest(userFoundById = None)
      ).delete("1").isFailure
    )
  }

}
