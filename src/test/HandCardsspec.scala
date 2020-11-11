import main.model.HandCard
import org.scalatest.{Matchers, WordSpec}


class HandCardsspec extends WordSpec with Matchers {
  "Handcards are a Vector of Cards from the Deck. Handcards" should {
    "copy 10 random Cards from the Deck and lose Cards once they are played" in {
      val hand = new HandCard()
      val testCard = hand.draw
      hand.size should be (10)
      hand.playCard(0) should be (testCard)
      hand.size should be (9)
    }
    "lose Cards once they are played"

  }
}