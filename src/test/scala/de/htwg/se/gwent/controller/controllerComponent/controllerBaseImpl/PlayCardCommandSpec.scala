package de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Field, HandCard}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherStatus.{FOG, FROST, SUNSHINE}
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PlayCardCommandSpec extends AnyWordSpec with Matchers {
  "is a Command Pattern to do, undo and redo Steps" when {
    "PlayCardCommand is used it" should {
      val playerTop = Player(TOP,"Adrian",HandCard(Vector[Card](Card("Test",1,1,1))),0)
      val playerBot = Player(BOT,"Stefan",HandCard(Vector[Card](Card("Test",2,1,1))),0)
      "be able to do,undo and redo Steps from the Top Player" in {
        val controller = new Controller(Field(Vector[Vector[Option[Card]]](),new Sunshine,playerTop,playerBot,0,0))
        val command = new PlayCardCommand(controller.field,0,0,TOP,0,controller)

        controller.field.isEmpty(0,0) should be (true)
        controller.field.playerTop.handCard.size should be (1)
        controller.field.weather.weather should be (SUNSHINE)

        command.doStep

        controller.field.isEmpty(0,0) should be (false)
        controller.field.playerTop.handCard.size should be (0)
        controller.field.weather.weather should be (FROST)

        command.undoStep

        controller.field.isEmpty(0,0) should be (true)
        controller.field.playerTop.handCard.size should be (1)
        controller.field.weather.weather should be (SUNSHINE)

        command.redoStep

        controller.field.isEmpty(0,0) should be (false)
        controller.field.playerTop.handCard.size should be (0)
        controller.field.weather.weather should be (FROST)
      }
      "be able to do,undo and redo Steps from the Bottom Player" in {
        val controller = new Controller(Field(Vector[Vector[Option[Card]]](),new Sunshine,playerTop,playerBot,0,0))
        val command = new PlayCardCommand(controller.field,0,0,BOT,0,controller)

        controller.field.isEmpty(0,0) should be (true)
        controller.field.playerBot.handCard.size should be (1)
        controller.field.weather.weather should be (SUNSHINE)

        command.doStep

        controller.field.isEmpty(0,0) should be (false)
        controller.field.playerBot.handCard.size should be (0)
        controller.field.weather.weather should be (FOG)

        command.undoStep

        controller.field.isEmpty(0,0) should be (true)
        controller.field.playerBot.handCard.size should be (1)
        controller.field.weather.weather should be (SUNSHINE)

        command.redoStep

        controller.field.isEmpty(0,0) should be (false)
        controller.field.playerBot.handCard.size should be (0)
        controller.field.weather.weather should be (FOG)
      }
    }
  }
}