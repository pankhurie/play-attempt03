package services

import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 5/3/17.
  */

object UserList {
  val userList:ListBuffer[User] = new ListBuffer[User]()
  val my = User("pankhurie", "fname", "mname", "lname", "demo", "demo",  "9999999999", "female", 24, true, true, true, false)


  userList.append(my)

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
