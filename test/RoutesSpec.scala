import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.test.FakeRequest
import play.api.test.Helpers._

/**
  * Created by knoldus on 9/3/17.
  */
class RoutesSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/badpath")).map(status(_)) mustBe Some(NOT_FOUND)
    }

    "respond to the index Action" in new App() {
      val Some(result) = route(app, FakeRequest(GET, "/"))
      status(result) mustBe OK
      contentType(result) mustBe ("text/html")
      charset(result) mustBe ("utf-8")
      contentAsString(result) must include("Welcome. Please Sign in or sign up.")
    }

    "respond to the signUp Action" in new App() {
      val Some(result) = route(app, FakeRequest(GET, "/signup"))
      status(result) mustBe OK
      contentType(result) mustBe ("text/html")
      charset(result) mustBe ("utf-8")
      contentAsString(result) must include("Please fill details below (Fields marked with * are mandatory)")
    }

    "respond to the signIn Action" in new App() {
      val Some(result) = route(app, FakeRequest(GET, "/signin"))
      status(result) mustBe OK
      contentType(result) mustBe ("text/html")
      charset(result) mustBe ("utf-8")
      contentAsString(result) must include("Sign in below")
    }

    "respond somethingwrong" in new App() {
      val Some(result) = route(app, FakeRequest(POST, "/somethingwron"))
      status(result) mustBe OK
      contentType(result) mustBe ("text/html")
      charset(result) mustBe ("utf-8")
      contentAsString(result) must include("anything")
    }
  }

}
