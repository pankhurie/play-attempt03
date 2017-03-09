package controllers

import java.io.File
import java.util.concurrent.TimeoutException
import javax.inject._

import akka.actor.ActorSystem
import models.{FormMapping, User}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.concurrent.Timeout
import play.api.libs.json._
import play.api.mvc._
import services._

import scala.concurrent.duration._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(actorSystem: ActorSystem, accountService: UserListService, formMapping: FormMapping) extends Controller {

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */


  val pankhurie = User("pankhurie", "fname", "mname", "lname", "demo", "demo", "9999999999", "female", 24, true, true, true, false, false, true)

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


  def management() = Action { implicit  request =>
    request.session.get("connected").map { sessionname =>
    Ok(views.html.management(accountService.getList(), accountService.getUser(sessionname)))
    }.getOrElse {
      Unauthorized("Oops, you are not logged in")
    }
  }

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
    request.session.get("connected").map { sessionname =>
      Ok(views.html.profile(accountService.getUser(sessionname)))
    }.getOrElse {
      Unauthorized("Oops, you are not logged in")
    }
  }

  def getProfile() = Action { implicit request =>
    val (name, password) = formMapping.loginForm.bindFromRequest.get
    val user = accountService.getUser(name)
    if (accountService.checkUser(name, password))
      if (user.isEnabled)
        Ok(views.html.profile(user)).withSession("connected" -> name)
      else
        Ok("Account disabled. Please contact administrator")
    else Ok("Incorrect Username/Password")
  }

  def postProfile() = Action { implicit request =>
    val regForm = formMapping.userForm.bindFromRequest
    val user: User = regForm.get

    regForm.fold(formWithErrors => {
      BadRequest(" ")
    }, success => {
      accountService.addUser(user)
      if (user.isEnabled) {
        //adding user not tested yet
        Ok(views.html.profile(user)).withSession("connected" -> user.name)
      }
      else {
        Ok("Account Disabled")
      }
    })

  }

  def logout() = Action { implicit request =>
    Ok(views.html.welcome()).withNewSession
  }

  def upload() = Action(parse.temporaryFile) { request =>
    val file = request.body.file
    file.renameTo(new File("./public/images/" + file.getName))
    Ok("File uploaded")
  }

  def toggle() = Action { implicit request =>
    request.session.get("connected").map { sessionname =>
      val (username) = formMapping.toggleForm.bindFromRequest.get
      accountService.toggleEnable(username)
      Ok(views.html.profile(accountService.getUser(sessionname)))
    }.getOrElse {
      Unauthorized("Oops, you are not connected")
    }

  }


}
