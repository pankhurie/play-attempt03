package services

import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 5/3/17.
  */

object UserList {
  val userList:ListBuffer[User] = new ListBuffer[User]()

  userList.append(new User("myname", "mypassword", 24))

  def addUser(user:User)={
    userList.append(user)
  }

  def getUser(username:String, password:String):User={
    userList.filter(user=> (user.name==username)&&(user.password==password))(0)
  }

  def checkUser(username:String, password:String):Boolean={
    userList.filter(user=> (user.name==username)&&(user.password==password)).length==1
  }
}
