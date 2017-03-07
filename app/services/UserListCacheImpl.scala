package services

import com.google.inject.Inject
import models.User
import play.api.cache.CacheApi

import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 7/3/17.
  */
class UserListCacheImpl@Inject()(cache: CacheApi) extends UserListService{

  cache.set("userList", ListBuffer[User]())
  val my = User("pankhurie", "pankhurie", "mname", "lname", "demo", "demo",  "9999999999", "female", 24, true, true, true, false, true, true)
  val my2 = User("pankhuriegupta", "pankhuriegupta", "mname", "lname", "demo", "demo",  "9999999999", "female", 24, true, true, true, false, false, true)
  val my3 = User("gupta", "gupta", "mname", "lname", "demo", "demo",  "9999999999", "female", 24, true, true, true, false, false, false)
  this.addUser(my)
  this.addUser(my2)
  this.addUser(my3)

  override def addUser(user: User): Boolean = {
    try{

      val encryptedUser = user.copy(password = MD5.hash(user.password))
      val userList:ListBuffer[User] = cache.get[ListBuffer[User]]("userList").get


      cache.set("userList",userList+=encryptedUser)
      true

    }catch{
      case e:Exception => false
    }
  }

  override def getUser(username: String): User = {

    val userList:ListBuffer[User] = cache.get[ListBuffer[User]]("userList").get
    userList.filter(user=> (user.name==username))(0)
  }

  override def checkUser(username: String, password: String): Boolean = {
    val userList:ListBuffer[User] = cache.get[ListBuffer[User]]("userList").get
    userList.filter(user=> (user.name==username)&&(user.password==MD5.hash(password))).length==1
  }

  def userExists(username:String):Boolean={
    val userList:ListBuffer[User] = cache.get[ListBuffer[User]]("userList").get
    (userList.filter(user=> (username==username)).length==1)

  }

  override def getList(): ListBuffer[User] = {
    cache.get[ListBuffer[User]]("userList").get
  }


/*  def disableUser(user:User):Boolean ={
    val userList:ListBuffer[User] = cache.get[ListBuffer[User]]("userList").get
    val modifiableUser = getUser(user.name, user.password)
    val modifiedUser = modifiableUser.copy(isEnabled = false)
    userList-modifiableUser
    userList.append(modifiableUser)
    true
  }

  def enableUser(user:User):Boolean ={
    val userList:ListBuffer[User] = cache.get[ListBuffer[User]]("userList").get
    val modifiableUser = getUser(user.name, user.password)
    val modifiedUser = modifiableUser.copy(isEnabled = true)
    userList-modifiableUser
    userList.append(modifiableUser)
    true
  }*/

  def toggleEnable(username:String):Boolean={
    val userList:ListBuffer[User] = cache.get[ListBuffer[User]]("userList").get
    val modifiableUser = getUser(username)
    val modifiedUser = modifiableUser.copy(isEnabled = !modifiableUser.isEnabled)
    cache.set("userList",(userList-modifiableUser)+=modifiedUser)
    true
  }

}
