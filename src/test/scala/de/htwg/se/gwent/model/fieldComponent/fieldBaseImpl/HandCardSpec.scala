package de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl

import de.htwg.se.gwent.model.fieldComponent.{CardInterface, FieldInterface}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class HandCardSpec extends AnyWordSpec with Matchers {
  "Handcards are an Array of 0 up to 10 Cards from the Deck. Handcards" when {
    "a Handcard" should {
      "be empty when all cards are played or no Cards have been drawn" in {
        val hand = HandCard(Vector[CardInterface]())
        hand.size should be(0)
        hand.handIsEmpty should be (true)
      }
      "have 10 Cards and not be empty when a new HandCard is drawn" in {
        val hand = HandCard(Vector[CardInterface]()).newHandCard()
        hand.size should be(10)
        hand.handIsEmpty should be (false)
      }
      "show the indexed Card" in {
        val hand = HandCard(Vector[CardInterface](Card("Archer",0,3,1)))
        hand.show(0) should be (Card("Archer",0,3,1))
      }
      "play a card at a certain Index in an empty field at a specific place" in {
        val hand = HandCard(Vector[CardInterface](Card("Archer",0,3,1)))
        hand.size should be(1)
        val field = Field(Vector[Vector[Option[Card]]](),new Sunshine,Player(TOP,"Adrian",HandCard(Vector[Card]()).newHandCard(),0),Player(BOT,"Stefan",HandCard(Vector[Card]()).newHandCard(),0),0,0).clear
        val tuplePlayed = hand.playCard(0,field,0,0)
        tuplePlayed._1 should be (Card("Archer",0,3,1))
        tuplePlayed._2 shouldBe a [HandCard]
        tuplePlayed._2.size should be(0)
        tuplePlayed._3 shouldBe a [FieldInterface]
      }
      "draw a random Card" in {
        val hand = HandCard(Vector[CardInterface]())
        hand.drawRandom.hand.size should be(1)
      }
      "delete a card" in {
        val oldHand = HandCard(Vector[CardInterface](Card("Archer", 0, 3, 1), Card("Zwerg", 0, 2, 0)))
        oldHand.size should be(2)
        val firstDelete = oldHand.deleteCard(Card("Archer", 0, 3, 1))
        firstDelete.size should be(1)
        firstDelete should be (HandCard(Vector[Card](Card("Zwerg", 0, 2, 0))))
        val secondDelete = firstDelete.deleteCard(Card("Elf", 0, 2, 1))
        secondDelete should be (HandCard(Vector[Card](Card("Zwerg", 0, 2, 0))))
      }
      "not delete a card" in {
        val hand = HandCard(Vector[CardInterface](Card("Archer", 0, 3, 1)))
        hand.deleteCard(Card("",0,0,0)) should be (HandCard(Vector[Card](Card("Archer", 0, 3, 1))))
      }
      "draw a specific Card" in {
        val hand = HandCard(Vector[CardInterface]())
        hand.draw(Card("Archer", 0, 3, 1)) should be(HandCard(Vector[Card](Card("Archer", 0, 3, 1))))
      }
      "can be converted" in {
        HandCard(Vector[CardInterface](Card("Archer", 0, 3, 1))).toString should be("Archer S3 R1")
      }
    }
  }
}