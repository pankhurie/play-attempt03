package controllers

import models.FormMapping
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.{OneAppPerTest, PlaySpec}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.UserListCacheImpl

/**
  * Created by knoldus on 7/3/17.
  */
class HomeControllerSpec extends PlaySpec with OneAppPerTest with MockitoSugar{

  "Home Controller" should {

    "perform the index action" in {

      val userService = mock[UserListCacheImpl]
      val formMapping = mock[FormMapping]
      val controller = new HomeController(userService, formMapping)
      val home = controller.index.apply(FakeRequest(GET, "/"))
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("Welcome. Please Sign in or sign up.")
      }

    "perform the sign in action" in {
      val userService = mock[UserListCacheImpl]
      val formMapping = mock[FormMapping]
      val controller = new HomeController(userService, formMapping)
      val home = controller.signin.apply(FakeRequest(GET, "/signin"))
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include ("Sign in below")
    }

    "perform the sign up action" in {
      val userService = mock[UserListCacheImpl]
      val formMapping = mock[FormMapping]
      val controller = new HomeController(userService, formMapping)
      val home = controller.signup.apply(FakeRequest(GET, "/signup"))
      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      val body = contentAsString(home)
      body must include ("Please fill details below (Fields marked with * are mandatory)")
    }

  }

}
