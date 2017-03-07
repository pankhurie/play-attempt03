package controllers

import java.io.File
import java.util.concurrent.TimeoutException
import javax.inject._

import akka.actor.ActorSystem
import play.api.data.Forms._
import play.api.data._
import play.api.data.validation.{Valid, Invalid, ValidationError, Constraint}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.concurrent.Timeout
import play.api.libs.json._
import play.api.mvc._
import services._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import scala.concurrent.duration._
import play.api.mvc.RequestHeader
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



  val pankhurie = User("pankhurie", "fname", "mname", "lname", "demo", "demo",  "9999999999", "female", 24, true, true, true, false)


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

  def index = Action {
    Ok(views.html.welcome())
  }

  def signin = Action {
    Ok(views.html.signin())
  }

  def signup = Action {
    Ok(views.html.signup())
  }

  def profile = Action { implicit request =>
    Ok(views.html.profile(pankhurie)).withSession("connected" -> pankhurie.name)
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
     if (UserList.checkUser(name,password)) Ok(views.html.profile(UserList.getUser(name,password))).withSession("connected" -> name)
     else Ok("No user found")
  }

  def postProfile() = Action{ implicit request =>
    val regForm = formObj.userForm.bindFromRequest
    val user:User = regForm.get

    regForm.fold(formWithErrors => {
      BadRequest(" ")
    }, success => {

      UserList.addUser(user) //adding user not tested yet
      Ok(views.html.profile(user)).withSession("connected" -> user.name)
    })

  }

  def logout() = Action { implicit request =>
    Ok(views.html.welcome()).withNewSession
  }

  def upload() = Action(parse.temporaryFile) { request =>
    request.body.moveTo(new File("/home/knoldus/Documents/KIPSolutions/play/play-assignment03/app/uploadedfile"))
    Ok("File uploaded")
  }

}
