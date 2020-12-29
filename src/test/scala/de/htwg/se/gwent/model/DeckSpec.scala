package scala.de.htwg.se.gwent.model

import de.htwg.se.gwent.model.cardComponent.cardBaseImpl.{Card, Deck}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DeckSpec extends AnyWordSpec with Matchers {
  "A Deck holds all Cards playable in the game" when {
    "a Card is drawn" should {
      "get a Card" in {
        val d = Deck()
        d.getRandomCard.isInstanceOf[Card] should be(true)
      }
    }
  }
}
