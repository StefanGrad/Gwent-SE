package scala.de.htwg.se.gwent.controller

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.de.htwg.se.gwent.controller.GameStatus.{PLAYING,INPUTFAIL}

import scala.de.htwg.se.gwent.model.{Card, Field, HandCard, Player}

class GameLogicSpec extends AnyWordSpec with Matchers {
  "The GameLogic decides if the Player Aktion is with in the Rules" when {
    "A Logic is in place" should {
      val logic = new GameLogic
      val archer = Card("Archer", 0, 3, 1)
      val field = Field(4, 4)
      val playerTop = Player("Top", HandCard(Vector[Card](archer,archer)),0,true)
      val playerBot = Player("Bot", HandCard(Vector[Card](archer,archer)),0,false)
      "when a card shall be played in the field" in {
        logic.applyLogic(field,1,1,playerTop,0) should be(PLAYING)
      }
      "when a Card is played wrongly" in {
        logic.applyLogic(field,1,1,playerBot,0) should be(INPUTFAIL)
      }
    }
  }
}
