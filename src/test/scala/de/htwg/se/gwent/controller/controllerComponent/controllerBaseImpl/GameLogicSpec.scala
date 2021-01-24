package de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.gwent.controller.controllerComponent.GameStatus.{INPUTFAIL, PLAYING}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Field, HandCard}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.playerComponent
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GameLogicSpec extends AnyWordSpec with Matchers {
  "The GameLogic decides if the Player Aktion is with in the Rules" when {
    "A Logic is in place" should {
      val logic = new GameLogic
      val archer = Card("Archer", 0, 3, 1)
      val playerTop = Player(TOP, "Top", HandCard(Vector[Card](archer,archer,archer)),0)
      val playerBot = playerComponent.Player(BOT, "Bot", HandCard(Vector[Card](archer,archer)),0)
      var f = Field(Vector[Vector[Option[Card]]](),new Sunshine,playerTop,playerBot,0,0).clear
      f = f.setCard(3,3,Some(archer))
      f = f.setCard(2,3,Some(archer))
      f = f.setCard(1,3,Some(archer))
      f = f.setCard(0,3,Some(archer))
      f = f.setCard(3,2,Some(archer))
      //f = f.setCard(2,2,Some(archer))
      f = f.setCard(1,2,Some(archer))
      f = f.setCard(0,2,Some(archer))
      f = f.setCard(3,1,Some(archer))
      f = f.setCard(2,1,Some(archer))
      //f = f.setCard(1,1,Some(archer))
      f = f.setCard(0,1,Some(archer))
      f = f.setCard(3,0,Some(archer))
      f = f.setCard(2,0,Some(archer))
      f = f.setCard(1,0,Some(archer))

      "when a card shall be played in the field" in {
        logic.applyTryLogic(f,0,0,playerTop,0)._1 should be(PLAYING)
      }
      "when a Card is played wrongly" in {
        logic.applyTryLogic(f,1,1,playerBot,0)._1 should be(INPUTFAIL)
        logic.applyTryLogic(f,1,1,playerBot,0)._2 should be("You cannot Play a Card in this row.")

        logic.applyTryLogic(f,2,2,playerBot,11)._1 should be(INPUTFAIL)
        logic.applyTryLogic(f,2,2,playerBot,11)._2 should be("You don't have such a card.")

        logic.applyTryLogic(f,4,1,playerBot,0)._1 should be(INPUTFAIL)
        logic.applyTryLogic(f,4,1,playerBot,0)._2 should be("Please play within the game area")

        logic.applyTryLogic(f,3,3,playerBot,0)._1 should be(INPUTFAIL)
        logic.applyTryLogic(f,3,3,playerBot,0)._2 should be("The Position is already filled.")
      }
    }
  }
}
