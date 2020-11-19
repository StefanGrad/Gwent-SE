package model
import org.scalatest.{Matchers, WordSpec}

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Your Name", HandCard(Vector[Card](Card("Archer",0,3,1))))
      "have a name" in {
        player.name should be("Your Name")
      }
      "have a nice String representation" in {
        player.toString should be("Your Name holds in his Hand: Archer A0 S3")
      }
    }
  }
}