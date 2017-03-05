package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.mvc.Controller
import services.User

class FormController extends Controller{

  var pankhurie = User("pankhurie", "demo", 24)

  val userForm = Form(
    mapping(
      "name" -> text,
      "password" -> text,
      "age" -> number

    )(User.apply)(User.unapply)
  )
//
//
//  val myData = Map("name" -> "bob","password" -> "anmol", "age" -> "18")
//  val pankhurieData = getCCParams(pankhurie)
  val user1: User = User("pankhurie", "demo", 24)
//
//  def getCCParams(cc: AnyRef) =
//    (Map[String, String]() /: cc.getClass.getDeclaredFields) {(a, f) =>
//      f.setAccessible(true)
//      a + (f.getName -> f.get(cc).toString)
//    }
}