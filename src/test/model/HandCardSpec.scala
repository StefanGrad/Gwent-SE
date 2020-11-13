import main.model.{Card, Field, HandCard}
import org.scalatest.{Matchers, WordSpec}


class HandCardsSpec extends WordSpec with Matchers {
  "Handcards are an Array of  10 Cards from the Deck. Handcards" when {
    "a Handcard" should {
      "be empty when all cards are played" in {
        val hand = HandCard()
        hand.set(0,Card("",0,0,0))
        hand.set(1,Card("",0,0,0))
        hand.set(2,Card("",0,0,0))
        hand.set(3,Card("",0,0,0))
        hand.set(4,Card("",0,0,0))
        hand.set(5,Card("",0,0,0))
        hand.set(6,Card("",0,0,0))
        hand.set(7,Card("",0,0,0))
        hand.set(8,Card("",0,0,0))
        hand.set(9,Card("",0,0,0))
        hand.handIsEmpty should be (true)
      }
      "not be empty when it contains Cards" in {
        val hand = HandCard()
        hand.handIsEmpty should be (false)
      }
      "show the indexed Card" in {
        val hand = HandCard()
        hand.show(0) should be (hand.hand(0))
      }
      "show that Cards can be set" in {
        val hand = HandCard()
        val old = hand.show(0)
        hand.set(0,Card("Archer",0,3,1)) should be (old)
      }
      "play a card in the field" in {
        val hand = HandCard()
        val field = Field(4,4)
        hand.set(0,Card("Archer",0,3,1))
        hand.playCard(0,field,0,0) should be (Card("Archer",0,3,1))
      }
    }

  }
}