package de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.gwent.controller.controllerComponent.GameStatus.{LOADED, PASSED, PLAYING, SAVED}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Field, HandCard}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherStatus.{FOG, FROST, SUNSHINE}
import de.htwg.se.gwent.model.playerComponent
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with Matchers {
  "the Controller acts as a medium between de.htwg.se.de.htwg.se.qwent.qwent.model and de.htwg.se.de.htwg.se.qwent.qwent.aview" when {
    "A Controller " should {
      val testRanged = Card("Archer", 0, 1, 1)
      val testCloseCombat = Card("Knight",0,1,0)
      val playerTop = Player(TOP,"Adrian",HandCard(Vector[Card](testRanged)),0)
      val playerBot = Player(BOT,"Stefan",HandCard(Vector[Card](testCloseCombat,testCloseCombat,testRanged,testRanged)),0)
      val field = Field(Vector[Vector[Option[Card]]](),new Sunshine,playerTop,playerBot,0,0).clear

      "create a playing Field with our Basic inputs, for a new Game" in {
        val ctrl = new Controller(field)
        ctrl.createField

        ctrl.field.size should be(4)
        ctrl.field.weather.weather should be (SUNSHINE)
        ctrl.field.turn should be (0)
        ctrl.field.round should be(0)

        ctrl.field.playerTop.name should be("Adrian")
        ctrl.field.playerTop.handCard.size should be(10)

        ctrl.field.playerBot.name should be("Stefan")
        ctrl.field.playerBot.handCard.size should be(10)
      }
      "convert a Field into a String" in {
        val ctrl = new Controller(field)
        ctrl.createField
        ctrl.fieldToString should be ("\n+---------------+---------------+\n" + "|None|None|None|None|\n|None|None|None|None|\n+---------------+---------------+\n|None|None|None|None|\n|None|None|None|None|\n+---------------+---------------+\n")
      }
      "clear the Field" in {
        val ctrl = new Controller(field)
        ctrl.playCardAt(field, 0,0,TOP,0)
        ctrl.field.isEmpty(0,0) should be(false)

        ctrl.clearField(field)
        ctrl.field.isEmpty(0,0) should be(true)
      }
      "can update the wins of the Players" in {
        val ctrl = new Controller(field)

        ctrl.field.playerTop.wins should be(0)
        ctrl.updateWins(TOP)

        ctrl.field.playerTop.wins should be(1)
      }
      "evaluate the Game" in {
        val ctrl = new Controller(field)

        ctrl.playCardAt(ctrl.field,0,0,TOP,0)
        ctrl.evaluate(ctrl.field)

        ctrl.field.playerTop.wins should be(1)
        ctrl.field.playerBot.wins should be(0)

        ctrl.field = ctrl.field.doTurn
        ctrl.playCardAt(ctrl.field,3,3,BOT,2)
        ctrl.evaluate(ctrl.field)

        ctrl.field.playerTop.wins should be(1)
        ctrl.field.playerBot.wins should be(1)

        ctrl.evaluate(ctrl.field)
        ctrl.field.playerTop.wins should be(1)
        ctrl.field.playerBot.wins should be(1)
      }
      "turn a player into a String" in {
        val ctrl = new Controller(field)
        ctrl.playerToString(ctrl.field.playerTop) should be("Adrian has won 0 times and holds in his Hand: Archer A0 S1 R1")
      }
      "play a Card at a chosen Cell" in {
        val ctrl = new Controller(field)
        ctrl.clearField(field)
        ctrl.playCardAt(field, 0,0,TOP,0)
        ctrl.field.getCard(0,0).get should be(testRanged)
      }
      "can undo and then redo" in {
        val ctrl = new Controller(field)
        ctrl.playCardAt(field, 0,0,TOP,0)
        ctrl.field.getCard(0,0).get should be(testRanged)
        ctrl.undo
        ctrl.field.getCard(0,0) should be(None)
        ctrl.redo
        ctrl.field.getCard(0,0).get should be(testRanged)
      }
      "can change the Weather" in {
        val testFog = Card("fog",2,0,0)
        val testFrost = Card("frost", 1,0,0)
        val testClear = Card("sunny", 3,0,0)
        val playerTop2 = playerComponent.Player(TOP,"Top", HandCard(Vector[Card](testFog,testRanged,testClear)),0)
        val playerBot2 = playerComponent.Player(BOT,"Bot", HandCard(Vector[Card](testRanged,testFrost,testClear)),0)
        val newField = Field(Vector[Vector[Option[Card]]](),new Sunshine,playerTop2,playerBot2,0,0)
        val controller = new Controller(newField)
        controller.field.weather.weather should be(SUNSHINE)
        controller.playCard(controller.field,TOP,0)
        controller.field.weather.weather should be(FOG)
        controller.playCard(controller.field,BOT,1)
        controller.field.weather.weather should be(FROST)
        controller.playCard(controller.field,TOP,1)
        controller.field.weather.weather should be(SUNSHINE)
      }

      "handle the state of the the game" in {
        val ctrl = new Controller(field)
        ctrl.gameState should be (PLAYING)
        ctrl.passRound()
        ctrl.gameState should be (PASSED)
        ctrl.safe
        ctrl.gameState should be  (SAVED)
        ctrl.load
        ctrl.gameState should be (LOADED)

      }
      "evaluate the round when the second Player passes" in {
        val ctrl = new Controller(field)
        ctrl.passRound()
        ctrl.playCard(field,BOT, 0)
        ctrl.passRound()
        ctrl.passRound()
        playerBot.wins should be (1)
      }
      "change the state of the game" in {
        val ctrl = new Controller(field)
        ctrl.gameState should be (PLAYING)
        ctrl.changeGameStatus(PASSED)
        ctrl.gameState should be (PASSED)

      }
    }
  }
}