import main.model.{Card, Field, HandCard}
import org.scalatest.{Matchers, WordSpec}


class HandCardsSpec extends WordSpec with Matchers {
  "Handcards are an Array of  10 Cards from the Deck. Handcards" when {
    "a Handcard" should {
      "be empty when all cards are played" in {
        val hand = HandCard(HandCard(List[Card]()).drawTen)
        val field = Field(4,4)
        hand.playCard(0,field,0,0)
        hand.playCard(0,field,0,1)
        hand.playCard(0,field,0,2)
        hand.playCard(0,field,0,3)
        hand.playCard(0,field,1,0)
        hand.playCard(0,field,1,1)
        hand.playCard(0,field,1,2)
        hand.playCard(0,field,1,3)
        hand.playCard(0,field,2,0)
        hand.playCard(0,field,2,1)
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
      "print it out as a String" in {
        val hand = HandCard()
        val archer = Card("Archer",0,3,1)
        val s = archer.toString
        hand.set(0,archer)
        hand.set(1,archer)
        hand.set(2,archer)
        hand.set(3,archer)
        hand.set(4,archer)
        hand.set(5,archer)
        hand.set(6,archer)
        hand.set(7,archer)
        hand.set(8,archer)
        hand.set(9,Card("Hobbit", 2, 0, 0))
        hand.toString should be (s +", "+s+", "+s+", "+s+", "+s+", "+s+", "+s+", "+s+", "+s+", "+Card("Hobbit", 2, 0, 0).toString)
      }

    }

  }
}