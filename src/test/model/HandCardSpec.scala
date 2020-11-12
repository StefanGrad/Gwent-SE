import main.model.{Card, Cell, Grid, Matrix, HandCard}
import org.scalatest.{Matchers, WordSpec}


class HandCardsSpec extends WordSpec with Matchers {
  "Handcards are a Vector of Cards from the Deck. Handcards" when {
    "drawn" should {
      val hand = new HandCard()
      val testCard = hand.draw
      hand.size should be(10)
      //hand.playCard(0) should be (testCard)
      hand.size should be(9)
      "and wenn played"in {

      }

    }
  }
}
