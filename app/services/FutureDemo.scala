package services

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.duration._
import play.api.libs.concurrent.Timeout

class FutureDemo {
  def factorial(x: Long): Future[Long] = Future {

    def innerFact(num: Long, factorial: Long): Long = {
      num match {
        case 1 => factorial
        case _ => innerFact(num - 1, factorial * num)
      }

    }
    innerFact(x, 1)

  }
}
