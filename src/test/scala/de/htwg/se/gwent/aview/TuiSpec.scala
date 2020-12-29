package scala.de.htwg.se.gwent.aview

import de.htwg.se.gwent.model.playerComponent
import de.htwg.se.gwent.model.playerComponent.Player

import scala.de.htwg.se.gwent.controller.GameStatus.{INPUTFAIL, PASSED, PLAYING}
import scala.de.htwg.se.gwent.model.{Card, Field, HandCard}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.de.htwg.se.gwent.controller.Controller
import scala.de.htwg.se.gwent.controller.WeatherState.Sunshine
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

class TuiSpec extends AnyWordSpec with Matchers {
  val f = new Field(Vector[Vector[Option[Card]]]())
  "Tui works as a Text based User Interface" when {
    "a Tui" should {
      val archer = Card("Archer", 0, 3, 1)
      val field = f.clear
      val playerTop = Player(TOP,"Top", HandCard(Vector[Card](archer,archer)),0)
      val playerBot = playerComponent.Player(BOT,"Bot", HandCard(Vector[Card](archer,archer)),0)
      val controller = new Controller(field, playerTop, playerBot,new Sunshine)
      val tui = new Tui(controller)

      "have the active player pass on input 'c' and if he's the second one to pass the game is evaluated" in {
        controller.gameState should be (PLAYING)
        tui.processInputLine("c",TOP)
        controller.gameState should be (PASSED)
        tui.processInputLine("c",BOT)
        controller.gameState should be (PLAYING)
      }

      "place the card with the chosen index into the chosen cell" in {
        tui.processInputLine("0 0 0",TOP)
        //controller.playCardAt(field, 0, 0, playerTop, 0)
        controller.field.isEmpty(0,0) should be (false)
        tui.processInputLine("3 3 0",BOT)
        //controller.playCardAt(field, 2, 2, playerBot, 0)
        controller.field.isEmpty(3,3) should be (false)
      }
      "can undo a Turn" in {
        tui.processInputLine("z",BOT)
        controller.field.isEmpty(3,3) should be (true)
      }
      "can redo a Turn" in {
        tui.processInputLine("r",BOT)
        controller.field.isEmpty(3,3) should be (false)
      }
      "error input" in {
        controller.gameState = PLAYING
        //Wrong col
        tui.processInputLine("5 0 0",BOT)
        controller.gameState should be (INPUTFAIL)
        controller.gameState = PLAYING
        //Wrong row
        tui.processInputLine("0 5 0",BOT)
        controller.gameState should be (INPUTFAIL)
        controller.gameState = PLAYING
        //Wrong card
        tui.processInputLine("1 0 11",BOT)
        controller.gameState should be (INPUTFAIL)
        controller.gameState = PLAYING
        //Already set
        tui.processInputLine("0 0 0",BOT)
        controller.gameState should be (INPUTFAIL)
      }
    }
  }
}
