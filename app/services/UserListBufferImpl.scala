package services

import models.User


import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 5/3/17.
  */

class UserListBufferImpl extends UserListService{
  val userList:ListBuffer[User] = new ListBuffer[User]()
  val my = User("pankhurie", "fname", "mname", "lname", "demo", "demo",  "9999999999", "female", 24, true, true, true, false)

  this.addUser(my)


  def addUser(user:User)={
    try{
      val encryptedUser = user.copy(password = MD5.hash(user.password))
      userList.append(encryptedUser)
      true
    }catch{
      case e:Exception => false
    }
  }

  def getUser(username:String, password:String):User={
    userList.filter(user=> (user.name==username)&&(user.password==MD5.hash(password)))(0)
  }

  def checkUser(username:String, password:String):Boolean={
    userList.filter(user=> (user.name==username)&&(user.password==MD5.hash(password))).length==1
  }
}
