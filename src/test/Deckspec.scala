import org.scalatest.{Matchers, WordSpec}

class Deckspec extends WordSpec with Matchers {
  "A Deck is a immutable List of all existing Cards. A Deck" should {
    "never lose a Card" in {
      List hand = new HandCards()
      hand.draw(0)
      hand.show(0) should be deck.get(0)
      hand.draw(0)
      hand.show(1) should be deck.get(0)
    }
  }
}