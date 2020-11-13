import main.model.{Card, Field, HandCard}
import org.scalatest.{Matchers, WordSpec}


class HandCardsSpec extends WordSpec with Matchers {
  "Handcards are a Vector of Cards from the Deck. Handcards" when {
    "played in row 0 and Col 0" should {
      val hand = new HandCard()
      val hand += deck(0)
      hand.size should be(10)
      //hand.playCard(0) should be (testCard)
      hand.playCard(0, field, 0,0)
      hand.size should be(9)
      "and when used should be deleted"in {
        hand.show(0) should be field.get(0)(0)
      }

    }
  }
}