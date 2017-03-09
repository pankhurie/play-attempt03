package services

import models.User

import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 7/3/17.
  */
trait UserListService {

  def addUser(user:User):Boolean

  def userExists(username:String):Boolean

  def getUser(username:String):User

  def checkUser(username:String, password:String):Boolean

  def getList():ListBuffer[User]

  def toggleEnable(username:String):Boolean


}
