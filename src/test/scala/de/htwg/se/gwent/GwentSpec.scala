package scala.de.htwg.se.gwent

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.de.htwg.se.gwent.Gwent

class GwentSpec extends AnyWordSpec with Matchers {

    "The Gwent main class" should {
      "accept text input as argument without readline loop, to test it from command line " in {
        Gwent.main(Array[String]("q"))
      }
    }
}
