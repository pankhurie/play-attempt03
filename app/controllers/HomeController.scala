package controllers

import java.util.concurrent.TimeoutException
import javax.inject._

import akka.actor.ActorSystem
import play.api.data.Forms._
import play.api.data._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.concurrent.Timeout
import play.api.libs.json._
import play.api.mvc._
import services._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import scala.concurrent.duration._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(actorSystem: ActorSystem) extends Controller {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */



  val pankhurie = User("pankhurie", "fname", "mname", "lname", "demo", "demo",  "9999999999", "female", 24)


  val json: JsValue = JsObject(Seq(
    "name" -> JsString("Watership Down"),
    "location" -> JsObject(Seq("lat" -> JsNumber(51.235685), "long" -> JsNumber(-1.309197))),
    "residents" -> JsArray(Seq(
      JsObject(Seq(
        "name" -> JsString("Fiver"),
        "age" -> JsNumber(4),
        "role" -> JsNull
      )),
      JsObject(Seq(
        "name" -> JsString("Bigwig"),
        "age" -> JsNumber(6),
        "role" -> JsString("Owsla")
      ))
    ))
  ))
  val name1 = (json \ "name").as[String]
  //not preferred
  val nameSome = (json \ "name").asOpt[String] //gets option , preferred


  implicit val userFormatter = Json.format[User]
  val me = Json.toJson(pankhurie)


  val formObj = new FormController

  /*def myMethod():Either[String,Int]={
    val num=1
    num match{
      case 1 => Left(1)
      case _ => Right("")
    }
  }*/

  def index = Action {
    Ok(views.html.welcome())
  }

  def signin = Action {
    Ok(views.html.signin());

  }

  def signup = Action {
    Ok(views.html.signup());
  }


  def profile = Action {
    Ok(views.html.profile(pankhurie))
  }

  def calculate = Action.async {
    Timeout.timeout(actorSystem, 0.0001.microseconds) {
      (new FutureDemo).factorial(20).map { product =>
        Ok("Factorial of 20: " + product)
      }
    }.recover {
      case e: TimeoutException =>
        InternalServerError("timeout")
    }
  }

  def getProfile() = Action { implicit request =>
    val (name,password) = formObj.loginForm.bindFromRequest.get
//    val foundUser: User = UserList.getUser(user.name, user.password)

     if (UserList.checkUser(name,password)) Ok(views.html.profile(UserList.getUser(name,password)))
     else Ok("No user found")
//    val user: User = UserList.getUser(name, password)


//    val formData = Form(
//      mapping(
//        "name" -> text,
//        "password" -> text,
//        "age" -> number
//
//      )(User.apply)(User.unapply)
//    )

    //Form<StudentFormData> formData = Form.form(StudentFormData.class).bindFromRequest();
//    val myData = Map("name" -> "bob", "password" -> "anmol", "age" -> "18")
//    val user1: User = formData.bind(myData).get
    //Student student = Student.makeInstance(formData.get());
//    val freshData = formData.bindFromRequest();
//    val freshUser: User = freshData.get
//    val existingUser:User = UserList.getUser(username)

//    val user: User = services.UserList.getUser(existingUser.name)
//    Ok(views.html.profile(user))
  }

  def postProfile() = Action{ implicit request =>
    val user:User = formObj.userForm.bindFromRequest.get
    UserList.addUser(user) //adding user not tested yet
    Ok(views.html.profile(user))
  }
}
