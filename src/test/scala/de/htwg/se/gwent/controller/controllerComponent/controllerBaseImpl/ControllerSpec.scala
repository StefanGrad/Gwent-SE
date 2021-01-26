package de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.gwent.controller.controllerComponent.GameStatus.{INPUTFAIL, LOADED, PASSED, PLAYING, SAVED}
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

        ctrl.field.playerTop.name should be("Player Top")
        ctrl.field.playerTop.handCard.size should be(10)

        ctrl.field.playerBot.name should be("Player Bottom")
        ctrl.field.playerBot.handCard.size should be(10)
      }
      "convert a Field into a String" in {
        val ctrl = new Controller(field)
        ctrl.createField
        ctrl.fieldToString should be ("\n+---------------+---------------+\n" + "|None|None|None|None|\n|None|None|None|None|\n+---------------+---------------+\n|None|None|None|None|\n|None|None|None|None|\n+---------------+---------------+\n")
      }
      "clear the Field" in {
        val ctrl = new Controller(field)
        ctrl.playCardAt(0,0,TOP,0)
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

        ctrl.playCardAt(0,0,TOP,0)
        ctrl.evaluate(ctrl.field)

        ctrl.field.playerTop.wins should be(1)
        ctrl.field.playerBot.wins should be(0)

        ctrl.field = ctrl.field.doTurn
        ctrl.playCardAt(3,3,BOT,2)
        ctrl.evaluate(ctrl.field)

        ctrl.field.playerTop.wins should be(1)
        ctrl.field.playerBot.wins should be(1)

        ctrl.evaluate(ctrl.field)
        ctrl.field.playerTop.wins should be(1)
        ctrl.field.playerBot.wins should be(1)
      }
      "turn a player into a String" in {
        val ctrl = new Controller(field)
        ctrl.playerToString(ctrl.field.playerTop) should be("Adrian has won 0 times and holds in his Hand: Archer S1 R1")
      }
      "play a Card at a chosen Cell" in {
        val player1 = Player(TOP,"Adrian",HandCard(Vector[Card](testRanged,testRanged,testRanged,testRanged,testCloseCombat,testCloseCombat,testCloseCombat,testCloseCombat,testCloseCombat)),0)
        val player2 = Player(BOT,"Stefan",HandCard(Vector[Card](testRanged,testRanged,testRanged,testRanged,testCloseCombat,testCloseCombat,testCloseCombat,testCloseCombat,testCloseCombat)),0)
        val field2 = Field(Vector[Vector[Option[Card]]](),new Sunshine,player1,player2,0,0)
        val ctrl = new Controller(field2)
        ctrl.playCardAt( 0,0,TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 3,0,BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 0,1,TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 3,1,BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 0,2,TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 3,2,BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.field.isEmpty(3,0) should be (true)
        ctrl.playCardAt( 0,3,TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 3,3,BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 1,0,TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 2,0,BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 1,1,TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 2,1,BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 1,2,TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 2,2,BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 1,3,TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 2,3,BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCardAt( 1,0,TOP,0)
        ctrl.gameState should be(INPUTFAIL)

        ctrl.field.getCard(0,0).get should be(testRanged)
      }
      "play a Card into the next empty Cell" in {
        val player1 = Player(TOP,"Adrian",HandCard(Vector[Card](testRanged,testRanged,testRanged,testRanged,testCloseCombat,testCloseCombat,testCloseCombat,testCloseCombat,testCloseCombat)),0)
        val player2 = Player(BOT,"Stefan",HandCard(Vector[Card](testRanged,testRanged,testRanged,testRanged,testCloseCombat,testCloseCombat,testCloseCombat,testCloseCombat,testCloseCombat)),0)
        val field2 = Field(Vector[Vector[Option[Card]]](),new Sunshine,player1,player2,0,0)
        val ctrl = new Controller(field2)
        ctrl.playCard(TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(TOP,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(BOT,0)
        ctrl.gameState should be(PLAYING)
        ctrl.playCard(TOP,0)
        ctrl.gameState should be(INPUTFAIL)
      }
      "can undo and then redo" in {
        val ctrl = new Controller(field)
        ctrl.playCardAt( 0,0,TOP,0)
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
        controller.playCard(TOP,0)
        controller.field.weather.weather should be(FOG)
        controller.playCard(BOT,1)
        controller.field.weather.weather should be(FROST)
        controller.playCard(TOP,1)
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
        ctrl.passRound()
        ctrl.field.round should be (1)
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