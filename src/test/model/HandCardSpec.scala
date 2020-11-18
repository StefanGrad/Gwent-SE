package model
import main.model.{Card, Field, HandCard}
import org.scalatest.{Matchers, WordSpec}

class HandCardSpec extends WordSpec with Matchers {
  "Handcards are an Array of  10 Cards from the Deck. Handcards" when {
    "a Handcard" should {
      "be empty when all cards are played" in {
        val hand = HandCard(Vector[Card]())
        hand.handIsEmpty should be (true)
      }
      "not be empty when it contains Cards" in {
        val hand = HandCard(Vector[Card]()).newDeck()
        hand.handIsEmpty should be (false)
      }
      "show the indexed Card" in {
        val hand = HandCard(Vector[Card](Card("Archer",0,3,1)))
        hand.show(0) should be (Card("Archer",0,3,1))
      }
      "play a card in the field" in {
        val hand = HandCard(Vector[Card](Card("Archer",0,3,1)))
        val field = Field(4,4)
        hand.playCard(0,field,0,0) should be (Card("Archer",0,3,1))
      }
    }

  }
}