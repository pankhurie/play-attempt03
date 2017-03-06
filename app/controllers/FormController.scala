package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.mvc.Controller
import services.User

class FormController extends Controller{

  val pankhurie = User("pankhurie", "fname", "mname", "lname", "demo", "demo",  "9999999999", "female", 24)

  /**
    * User(name: String, fname:String, mname:String, lname:String,
                password: String, repassword:String, mobile:String, gender:String,
                age: Int)
    */

  val userForm = Form(
    mapping(
      "name" -> text,
      "fname" -> text,
      "mname" -> text,
      "lname" -> text,
      "password" -> text,
      "repassword" -> text,
      "mobile" -> text,
      "gender" -> text,
      "age" -> number

    )(User.apply)(User.unapply)
  )

  val loginForm = Form(
    tuple(
      "name" -> text,
      "password" -> text
    )
  )
//
//
//  val myData = Map("name" -> "bob","password" -> "anmol", "age" -> "18")
//  val pankhurieData = getCCParams(pankhurie)
//  val user1: User = User("pankhurie", "demo", 24)
//
//  def getCCParams(cc: AnyRef) =
//    (Map[String, String]() /: cc.getClass.getDeclaredFields) {(a, f) =>
//      f.setAccessible(true)
//      a + (f.getName -> f.get(cc).toString)
//    }
}