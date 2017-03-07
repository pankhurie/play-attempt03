package services

import models.User

import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 7/3/17.
  */
trait UserListService {

  def addUser(user:User):Boolean

  def getUser(username:String):User

  def checkUser(username:String, password:String):Boolean

  def getList():ListBuffer[User]

/*  def disableUser(user:User):Boolean

  def enableUser(user:User):Boolean*/

  def toggleEnable(username:String):Boolean


}
