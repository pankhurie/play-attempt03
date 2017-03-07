import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Created by knoldus on 7/3/17.
  */
class HomeControllerSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/badpath")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

  "HomeController" should {

    "render the welcome page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Welcome. Please Sign in or sign up.")
    }

    "render the sign in page" in {
      val home = route(app, FakeRequest(GET, "/signin")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Sign in below")
    }

    "render the sign up page" in {
      val home = route(app, FakeRequest(GET, "/signup")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Please fill details below (Fields marked with * are mandatory)")
    }



  }



}
