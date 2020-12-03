package scala.de.htwg.se.gwent.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {
  "A Player" when {
    "a new Player" should {
      var playerTop = Player("Your Name", HandCard(Vector[Card](Card("Archer",0,3,1))),0, true)
      //var playerBot = Player(1,"Your Name", HandCard(Vector[Card](Card("Archer",0,3,1))),0)
      "have a name" in {
        playerTop.name should be("Your Name")
        //playerBot.name should be("Your Name")
      }
      "His wins can be updated" in {
        playerTop = playerTop.updateWins(playerTop)
        playerTop.wins should be(1)
        //val playerNew1 = playerBot.updateWins(playerBot)
        //playerNew1.wins should be(1)
      }
      "have a nice String representation" in {
        playerTop.toString should be("Your Name has won 1 times and holds in his Hand: Archer A0 S3 R1")
        //playerBot.toString should be("Your Name has won 0 times and holds in his Hand: Archer A0 S3 R1")
      }
    }
  }
}