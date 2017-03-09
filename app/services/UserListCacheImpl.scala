package services

import com.google.inject.Inject
import models.User
import play.api.cache.CacheApi

import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 7/3/17.
  */
class UserListCacheImpl@Inject()(cache: CacheApi, md5: MD5) extends UserListService{

  cache.set("userList", ListBuffer[User]())
  /*val my = User("pankhurie", "pankhurie", "mname", "lname", "demo", "demo",  "9999999999", "female", 24, true, true, true, false, true, true)
  val my2 = User("pankhuriegupta", "pankhuriegupta", "mname", "lname", "demo", "demo",  "9999999999", "female", 24, true, true, true, false, false, true)
  val my3 = User("gupta", "gupta", "mname", "lname", "demo", "demo",  "9999999999", "female", 24, true, true, true, false, false, false)
  this.addUser(my)
  this.addUser(my2)
  this.addUser(my3)*/

  override def addUser(user: User): Boolean = {
    try{
      val encryptedUser = user.copy(password = md5.hash(user.password))
      val userList:ListBuffer[User] = cache.get[ListBuffer[User]]("userList").getOrElse(ListBuffer[User]())
      cache.set("userList",userList+=encryptedUser)
      true
    }catch{
      case e:Exception => false
    }
  }

  override def getUser(username: String): User = {
    val userList:ListBuffer[User] = cache.get[ListBuffer[User]]("userList").getOrElse(ListBuffer[User]())
    userList.filter(user=> (user.name==username))(0)
  }

  override def checkUser(username: String, password: String): Boolean = {
    val userList:ListBuffer[User] = cache.get[ListBuffer[User]]("userList").getOrElse(ListBuffer[User]())
    userList.filter(user=> (user.name==username)&&(user.password==md5.hash(password))).length==1
  }

  override def userExists(username:String):Boolean={
    val userList:ListBuffer[User] = cache.get[ListBuffer[User]]("userList").getOrElse(ListBuffer[User]())
    (userList.filter(user=> (username==username)).length==1)
  }

  override def getList(): ListBuffer[User] = {
    cache.get[ListBuffer[User]]("userList").getOrElse(ListBuffer[User]())
  }

  override def toggleEnable(username:String):Boolean={
    try{
    val userList:ListBuffer[User] = cache.get[ListBuffer[User]]("userList").getOrElse(ListBuffer[User]())
    val modifiableUser = getUser(username)
    val modifiedUser = modifiableUser.copy(isEnabled = !modifiableUser.isEnabled)
    cache.set("userList",(userList-modifiableUser)+=modifiedUser)
    true
    }catch{
      case e:Exception => false
    }
  }

}
