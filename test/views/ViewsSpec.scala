package views

import models.User
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.mvc.RequestHeader
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.collection.mutable.ListBuffer

/**
  * Created by knoldus on 9/3/17.
  */
class ViewsSpec extends PlaySpec with MockitoSugar with OneAppPerTest{

  "Home Controller" should{
    "render the welcome page" in{
      val html = views.html.welcome()
      contentAsString(html) must include("Welcome. Please Sign in or sign up")
    }

    //Please fill details below (Fields marked with * are mandatory)

    "render management console" in {
      val header = mock[RequestHeader]
      val user =User("", "", "", "", "", "",  "", "", 24, true, true, true, true, true, true)
      val html = views.html.management(ListBuffer[User](), user)
      contentAsString(html) must include("Management Console")
    }

    "render sign in page" in {
      val html = views.html.signin()
      contentAsString(html) must include("Sign in below")
    }

    "render sign up page" in {

      val html = views.html.signup()
      contentAsString(html) must include("Please fill details below (Fields marked with * are mandatory)")
    }

    "render profile page" in new App{
     val header = mock[RequestHeader]
     val user =User("", "", "", "", "", "",  "", "", 24, true, true, true, true, true, true)
     val html = views.html.profile(user)(header)
     contentAsString(html) must include("Add Profile Picture")
   }
  }

}
