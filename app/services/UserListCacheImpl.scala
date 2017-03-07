package services

import com.google.inject.Inject
import models.User
import play.api.cache.CacheApi

/**
  * Created by knoldus on 7/3/17.
  */
class UserListCacheImpl@Inject()(cache: CacheApi) extends UserListService{
  override def addUser(user: User): Boolean = {
    try{
      cache.set(user.name, user)
      true
    }catch{
      case e:Exception => false
    }
  }

  override def getUser(username: String, password: String): User = {
    cache.get[User](username).getOrElse(new User("", "", "", "", "","", "", "", 0, false, false, false, false))
  }

  override def checkUser(username: String, password: String): Boolean = {
    val user = getUser(username, password)
    if (user.name==password)
      true
    else
      false
  }
}
