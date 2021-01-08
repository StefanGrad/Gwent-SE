package scala.de.htwg.se.gwent.controller

import de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl.{Controller, TurnLogic}
import de.htwg.se.gwent.model.cardComponent.cardBaseImpl.{Card, HandCard}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.gwent.model.playerComponent
import de.htwg.se.gwent.model.playerComponent.Player
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherStatus.{FOG, FROST, SUNSHINE}
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

class ControllerSpec extends AnyWordSpec with Matchers {
  "the Controller acts as a medium between de.htwg.se.de.htwg.se.qwent.qwent.model and de.htwg.se.de.htwg.se.qwent.qwent.aview" when {
    "A Controller " should {
      val archer = Card("Archer", 0, 3, 1)
      val field = Field(Vector[Vector[Option[Card]]]()).clear
      val playerTop = Player(TOP,"Top", HandCard(Vector[Card](archer,archer,archer)),0)
      val playerBot = playerComponent.Player(BOT,"Bot", HandCard(Vector[Card](archer,archer,archer)),0)
      val ctrl = new Controller(field, playerTop, playerBot,TurnLogic(0,1))

      "create a playing Field" in {
        ctrl.createField
        ctrl.field.size should be(4)

      }
      "convert a Field into a String" in {
        ctrl.createField
        ctrl.fieldToString should be ("\n+---------------+---------------+\n" + "|None|None|None|None|\n|None|None|None|None|\n+---------------+---------------+\n|None|None|None|None|\n|None|None|None|None|\n+---------------+---------------+\n")
      }
      "clear the Field" in {
        ctrl.playCardAt(field, 1,1,TOP,0)
        ctrl.clearField(field)
        ctrl.field.isEmpty(1,1) should be(true)
      }
      "evaluate the Game" in {
        val ctrl = new Controller(field,playerTop,playerBot, TurnLogic(0,0))
        ctrl.playCardAt(ctrl.field,0,0,TOP,0)
        ctrl.evaluate(ctrl.field,ctrl.playerTop,ctrl.playerBot)
        ctrl.playerTop.wins should be(1)
        ctrl.playerBot.wins should be(0)
        ctrl.turnLogic = ctrl.turnLogic.doTurn
        ctrl.playCardAt(ctrl.field,3,3,BOT,0)
        ctrl.evaluate(ctrl.field,ctrl.playerTop,ctrl.playerBot)
        ctrl.playerTop.wins should be(1)
        ctrl.playerBot.wins should be(1)
        ctrl.evaluate(ctrl.field,ctrl.playerTop,ctrl.playerBot)
        ctrl.playerTop.wins should be(1)
        ctrl.playerBot.wins should be(1)
      }
      "create the Top Player" in {
        ctrl.createPlayer("Stefan",TOP)
        ctrl.playerTop.name should be("Stefan")
      }
      "create the Bot Player" in {
        ctrl.createPlayer("Stefan",BOT)
        ctrl.playerBot.name should be("Stefan")
      }
      "turn a player into a String" in {
        ctrl.playerTop = playerComponent.Player(TOP,"Adrian", HandCard(Vector[Card](archer)),0)
        ctrl.playerToString(ctrl.playerTop) should be("Adrian has won 0 times and holds in his Hand: Archer A0 S3 R1")
      }
      "play a Card at a chosen Cell" in {
        ctrl.clearField(field)
        ctrl.playCardAt(field, 0,0,TOP,0)
        ctrl.field.getCard(0,0).get should be(archer)
      }
      "can undo and than redo" in {
        ctrl.field.getCard(0,0).get should be(archer)
        ctrl.undo
        ctrl.field.getCard(0,0) should be(None)
        ctrl.redo
        ctrl.field.getCard(0,0).get should be(archer)
      }
      "can change the Weather" in {
        val nF = field.clear
        val testFog = Card("fog",2,0,0)
        val testFrost = Card("frost", 1,0,0)
        val testClear = Card("sunny", 3,0,0)
        val playerTop2 = playerComponent.Player(TOP,"Top", HandCard(Vector[Card](testFog,archer,testClear)),0)
        val playerBot2 = playerComponent.Player(BOT,"Bot", HandCard(Vector[Card](archer,testFrost,testClear)),0)
        val controller = new Controller(nF,playerTop2,playerBot2, TurnLogic(0,1))
        controller.field.weather.weather should be(SUNSHINE)
        controller.playCardAt(controller.field,1,0,TOP,0)
        controller.field.weather.weather should be(FOG)
        controller.playCardAt(controller.field,2,1,BOT,1)
        controller.field.weather.weather should be(FROST)
        controller.playCardAt(controller.field,1,1,TOP,1)
        controller.field.weather.weather should be(SUNSHINE)
      }
    }
  }
}