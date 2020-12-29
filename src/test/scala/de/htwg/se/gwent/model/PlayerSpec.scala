package scala.de.htwg.se.gwent.model

import de.htwg.se.gwent.model.playerComponent.{Player, PlayerArea}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

class PlayerSpec extends AnyWordSpec with Matchers {
  "A Player" when {
    "a new Player" should {
      var playerTop = Player(TOP, "Your Name", HandCard(Vector[Card](Card("Archer",0,3,1))),0)
      //var playerBot = Player(1,"Your Name", HandCard(Vector[Card](Card("Archer",0,3,1))),0)
      "have a name" in {
        playerTop.name should be("Your Name")
        //playerBot.name should be("Your Name")
      }
      "His wins can be updated" in {
        playerTop = playerTop.updateWins(playerTop)
        playerTop.wins should be(1)
      }
      "have a nice String representation" in {
        playerTop.toString should be("Your Name has won 1 times and holds in his Hand: Archer A0 S3 R1")
        //playerBot.toString should be("Your Name has won 0 times and holds in his Hand: Archer A0 S3 R1")
      }
      "get the Topside Area" in {
        PlayerArea.handel(TOP) should be (Vector[Int](1,0))
        PlayerArea.handel(BOT) should be (Vector[Int](2,3))
      }
    }
  }
}