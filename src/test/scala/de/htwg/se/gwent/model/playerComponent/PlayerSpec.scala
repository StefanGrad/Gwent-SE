package de.htwg.se.gwent.model.playerComponent

import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, HandCard}
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayerSpec extends AnyWordSpec with Matchers {
  "A Player" when {
    "a new Player" should {
      var playerTop = Player(TOP, "Your Name", HandCard(Vector[Card](Card("Archer",0,3,1))),0)
      var playerBot = Player(BOT,"Your Name", HandCard(Vector[Card](Card("Archer",0,3,1))),0)
      "have a name" in {
        playerTop.name should be("Your Name")
        playerBot.name should be("Your Name")
      }
      "His wins can be updated" in {
        playerTop = playerTop.updateWins(playerTop)
        playerBot = playerBot.updateWins(playerBot)
        playerTop.wins should be(1)
        playerBot.wins should be(1)
      }
      "have a nice String representation" in {
        playerTop.toString should be("Your Name has won 1 times and holds in his Hand: Archer S3 R1")
        playerBot.toString should be("Your Name has won 1 times and holds in his Hand: Archer S3 R1")
      }
      "get the specified playing field" in {
        PlayerArea.handel(TOP) should be (Vector[Int](1,0))
        PlayerArea.handel(BOT) should be (Vector[Int](2,3))
      }
    }
  }
}