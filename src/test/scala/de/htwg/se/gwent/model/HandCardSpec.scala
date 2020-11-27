package scala.de.htwg.se.gwent.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class HandCardSpec extends AnyWordSpec with Matchers {
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
        hand.playCard(0,field,0,0)._1 should be (Card("Archer",0,3,1))
      }
      "draws a random Card" in {
        val hand = HandCard(Vector[Card]())
        hand.drawRandom.hand.size should be(1)
      }
      "delete a card" in {
        val hand = HandCard(Vector[Card](Card("Archer", 0, 3, 1)))
        hand.deleteCard(Card("Archer", 0, 3, 1)) should be (HandCard(Vector[Card]()))
      }
      "not delete a card" in {
        val hand = HandCard(Vector[Card](Card("Archer", 0, 3, 1)))
        hand.deleteCard(Card("",0,0,0)) should be (HandCard(Vector[Card](Card("Archer", 0, 3, 1))))
      }
      "draws a specific Card" in {
        val hand = HandCard(Vector[Card]())
        hand.draw(Card("Archer", 0, 3, 1)) should be(HandCard(Vector[Card](Card("Archer", 0, 3, 1))))
      }
      "can be converted" in {
        HandCard(Vector[Card](Card("Archer", 0, 3, 1))).toString should be("Archer A0 S3 R1")
      }
    }
  }
}