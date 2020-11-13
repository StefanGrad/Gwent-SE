import main.model.{Card, Field, HandCard}
import org.scalatest.{Matchers, WordSpec}


class HandCardsSpec extends WordSpec with Matchers {
  "Handcards are an Array of  10 Cards from the Deck. Handcards" when {
    "a Handcard" should {
      val field = Field(4, 4)
      val hand = new HandCard()
      hand.size should be(10)
      "be empty when" in {
        hand.handIsEmpty should be (true)
      }
    }
    "can be set"  should{
      val hand = new HandCard()
      hand.set(0, new Card("Archer", 0, 3, 1))
      "have set that Card in the hand" in {
      hand.show(0) should be (Card("Archer", 0, 3, 1))
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