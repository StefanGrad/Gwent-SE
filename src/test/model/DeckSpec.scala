package model
import org.scalatest.{Matchers, WordSpec}

class DeckSpec extends WordSpec with Matchers {
  "A Deck holds all Cards playable in the game" when {
    "a Card is drawn" should {
      "get a Card" in {
        val d = Deck()
        d.getRandomCard.isInstanceOf[Card] should be(true)
      }
    }
  }
}
