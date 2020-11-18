package model
import org.scalatest.{Matchers, WordSpec}

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Your Name")
      "have a name" in {

        player.name should be("Your Name")
      }
      "have a nice String representation" in {

        Player("Stefan").toString should be("Stefan")
      }
    }
  }
}