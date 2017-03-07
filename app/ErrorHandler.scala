
import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._
import scala.concurrent._
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future

/**
  * Created by knoldus on 6/3/17.
  */
class ErrorHandler extends HttpErrorHandler{
  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    statusCode match{
      case 400 => Future.successful(Status(statusCode)(statusCode + " Bad request."))
      case 401 => Future.successful(Status(statusCode)(statusCode + " Unauthorized."))
      case 403 => Future.successful(Status(statusCode)(statusCode + " Forbidden."))
      case 404 => Future.successful(Status(statusCode)(statusCode + " Page not found."))
      case _ => Future.successful(Status(statusCode)(statusCode + " Some unknown error occured!"))
    }
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful(
      Ok(views.html.signup())
    )
  }
}
