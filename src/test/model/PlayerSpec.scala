import main.Gwent.hand
import main.model.{Card, Player}
import org.scalatest.{Matchers, WordSpec}

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Your Name")
      "have a name" in {
        player.name should be("Your Name")
      }
      "have a nice String representation" in {
        player.toString should be("Your Name")
      }
      "and hold Cards" in {
        player.hand should be (new scala.collection.mutable.ArrayBuffer[Card]())
      }
    }



  }
}
}