import main.model.{Card, Field, HandCard}
import org.scalatest.{Matchers, WordSpec}


class HandCardsSpec extends WordSpec with Matchers {
  "Handcards are an Array of  10 Cards from the Deck. Handcards" when {
    "played in row 0 and Col 0" should {
      val field = Field(4, 4)
      val hand = new HandCard()
      hand.size should be(10)
      "place a Card in a Spot and lose it from its Array" in {
        hand.playCard(0, field, 0, 0)
        hand.show(0) should be(field.get(0, 0))
        hand.size should be(9)
      }
    }
    "can be set"  should{
      val hand = new HandCard()
      hand.set(0, new Card("Archer", 0, 3, 1))
      "have set that Card in the hand" in {
      hand.show(0) should be (Card("Archer", 0, 3, 1))
      }
    }
  }
}