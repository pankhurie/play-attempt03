package controllers

import play.api.data.Forms._
import play.api.data._
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}
import play.api.mvc.Controller
import services.User

class FormController extends Controller{

  val pankhurie = User("pankhurie", "fname", "mname", "lname", "demo", "demo",  "9999999999", "female", 24, true, true, true, false)

  /**
    * User(name: String, fname:String, mname:String, lname:String,
    * password: String, repassword:String, mobile:String, gender:String,
    * age: Int)
    */

  val userForm = Form(
    mapping(
      "name" -> nonEmptyText,
      "fname" -> nonEmptyText,
      "mname" -> text,
      "lname" -> text,
      "password" -> nonEmptyText(minLength = 8),
      "repassword" -> nonEmptyText(minLength = 8),
      "mobile" -> text(minLength = 10, maxLength = 10),
      "gender" -> nonEmptyText,
      "age" -> number(min = 18, max = 75),
      "singing" -> boolean,
      "dancing" -> boolean,
      "reading" -> boolean,
      "sports" -> boolean

    )(User.apply)(User.unapply).verifying("Passwords do not match", fields => fields match {
      case data => (data.password == data.repassword)
    }
  ))

  val loginForm = Form(
    tuple(
      "name" -> text,
      "password" -> text
    )
  )

  val allNumbers = """\d*""".r
  val allLetters = """[A-Za-z]*""".r


  val passwordCheckConstraint: Constraint[String] = Constraint("constraints.passwordcheck")({
    plainText =>
      val errors = plainText match {
        case allNumbers() => Seq(ValidationError("Password is all numbers"))
        case _ => Nil
      }
      if (errors.isEmpty) {
        Valid
      } else {
        Invalid(errors)
      }
  })

  val nameCheckConstraint: Constraint[String] = Constraint("constraints.mobilecheck")({
    plainText =>
      val errors = plainText match {
        case allLetters => Nil
        case _ => Seq(ValidationError("Name can have only letters"))
      }
      if (errors.isEmpty) {
        Valid
      } else {
        Invalid(errors)
      }
  })


}