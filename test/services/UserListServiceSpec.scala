package services

import models.User
import org.scalatestplus.play.PlaySpec
import play.api.cache.CacheApi
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration

class UserListServiceSpec extends PlaySpec with MockitoSugar {

/*
    class MockCacheApi extends CacheApi{

    override def set(key: String, value: Any, expiration: Duration): Unit = {

    }

    override def get[T](key: String): Option[T] = {
      Option(T)
    }

    override def getOrElse[A](key: String, expiration: Duration)(orElse: => A)(implicit evidence$1: ClassManifest[A]): A = ???

    override def remove(key: String): Unit = ???
  }
  */

  "UserListService should" should{

    "add user" in{
      val cache = mock[CacheApi]
      val md5 = mock[MD5]
      val userListImpl = new UserListCacheImpl(cache, md5)
      val user =User("", "", "", "", "", "",  "", "", 24, true, true, true, true, true, true)

      when(md5.hash("")).thenReturn("")
      when(cache.get[ListBuffer[User]]("userList")).thenReturn(Option(ListBuffer[User]()))
//      when(cache.set("userList", ListBuffer[User]())).thenReturn()
      userListImpl.addUser(user) mustBe true
    }


    "get user" in{
      val cache = mock[CacheApi]
      val md5 = mock[MD5]
      val userListImpl = new UserListCacheImpl(cache, md5)
      val user =User("", "", "", "", "", "",  "", "", 24, true, true, true, true, true, true)

      when(md5.hash("")).thenReturn("")
      when(cache.get[ListBuffer[User]]("userList")).thenReturn(Option(ListBuffer[User](user)))
      //      when(cache.set("userList", ListBuffer[User]())).thenReturn

      userListImpl.getUser("") mustBe user
    }

    "check user & password" in{
      val cache = mock[CacheApi]
      val md5 = mock[MD5]
      val userListImpl = new UserListCacheImpl(cache, md5)
      val user =User("", "", "", "", "", "",  "", "", 24, true, true, true, true, true, true)

      when(md5.hash("")).thenReturn("")
      when(cache.get[ListBuffer[User]]("userList")).thenReturn(Option(ListBuffer[User](user)))

      userListImpl.checkUser("","") mustBe true

    }

    "check user exists" in{
      val cache = mock[CacheApi]
      val md5 = mock[MD5]
      val userListImpl = new UserListCacheImpl(cache, md5)
      val user =User("", "", "", "", "", "",  "", "", 24, true, true, true, true, true, true)

      when(md5.hash("")).thenReturn("")
      when(cache.get[ListBuffer[User]]("userList")).thenReturn(Option(ListBuffer[User](user)))

      userListImpl.userExists("") mustBe true

    }

    "get user list" in{
      val cache = mock[CacheApi]
      val md5 = mock[MD5]
      val userListImpl = new UserListCacheImpl(cache, md5)
      when(cache.get[ListBuffer[User]]("userList")).thenReturn(Option(ListBuffer[User]()))
      //      when(cache.set("userList", ListBuffer[User]())).thenReturn
      userListImpl.getList() mustBe ListBuffer[User]()
    }

    "enable user on toggling disabled user" in {
      val cache = mock[CacheApi]
      val md5 = mock[MD5]
      val userListImpl = new UserListCacheImpl(cache, md5)
      val user =User("", "", "", "", "", "",  "", "", 24, true, true, true, true, true, false)
      when(cache.get[ListBuffer[User]]("userList")).thenReturn(Option(ListBuffer[User](user)))
      //      when(cache.set("userList", ListBuffer[User]())).thenReturn

      userListImpl.toggleEnable("") mustBe true

    }

    "disable user on toggling enabled user" in {
      val cache = mock[CacheApi]
      val md5 = mock[MD5]
      val userListImpl = new UserListCacheImpl(cache, md5)
      val user =User("", "", "", "", "", "",  "", "", 24, true, true, true, true, true, true)
      when(cache.get[ListBuffer[User]]("userList")).thenReturn(Option(ListBuffer[User](user)))
      //      when(cache.set("userList", ListBuffer[User]())).thenReturn
      userListImpl.toggleEnable("") mustBe true

    }

  }

}