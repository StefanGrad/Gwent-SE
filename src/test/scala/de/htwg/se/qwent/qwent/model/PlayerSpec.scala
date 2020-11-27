package de.htwg.se.qwent.model
import org.scalatest.{Matchers, WordSpec}

class PlayerSpec extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
      val player = Player("Your Name", HandCard(Vector[Card](Card("Archer",0,3,1))),0)
      "have a name" in {
        player.name should be("Your Name")
      }
      "His wins can be updated" in {
        val playerNew = player.updateWins(player)
        playerNew.wins should be(1)
      }
      "have a nice String representation" in {
        player.toString should be("Your Name has won 0 times and holds in his Hand: Archer A0 S3 R1")
      }
    }
  }
}