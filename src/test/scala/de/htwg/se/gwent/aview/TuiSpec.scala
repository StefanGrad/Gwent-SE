package scala.de.htwg.se.gwent.aview

import scala.de.htwg.se.gwent.controller.GameStatus.{INPUTFAIL, PASSED, PLAYING}
import scala.de.htwg.se.gwent.model.{Card, Field, HandCard, Player}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.de.htwg.se.gwent.controller.Controller

class TuiSpec extends AnyWordSpec with Matchers {
  "Tui works as a Text based User Interface" when {
    "a Tui" should {
      val archer = Card("Archer", 0, 3, 1)
      val field = Field(4, 4)
      val playerTop = Player("Top", HandCard(Vector[Card](archer,archer)),0, true)
      val playerBot = Player("Bot", HandCard(Vector[Card](archer,archer)),0, false)
      val controller = new Controller(field, playerTop, playerBot)
      val tui = new Tui(controller)


      "have the active player pass on input 'c' and if he's the second one to pass the game is evaluated" in {
        controller.gameState should be (PLAYING)
        tui.processInputLine("c",true)
        controller.gameState should be (PASSED)
        tui.processInputLine("c",false)
        controller.gameState should be (PLAYING)
      }

      "place the card with the chosen index into the chosen cell" in {
        tui.processInputLine("0 0 0",true)
        //controller.playCardAt(field, 0, 0, playerTop, 0)
        field.isEmpty(0,0) should be (false)
        tui.processInputLine("2 2 0",false)
        //controller.playCardAt(field, 2, 2, playerBot, 0)
        field.isEmpty(2,2) should be (false)

      }
      "error input" in {
        controller.gameState = PLAYING
        //Wrong col
        tui.processInputLine("5 0 0",true)
        controller.gameState should be (INPUTFAIL)
        controller.gameState = PLAYING
        //Wrong row
        tui.processInputLine("0 5 0",true)
        controller.gameState should be (INPUTFAIL)
        controller.gameState = PLAYING
        //Wrong card
        tui.processInputLine("1 0 11",true)
        controller.gameState should be (INPUTFAIL)
        controller.gameState = PLAYING
        //Already set
        tui.processInputLine("0 0 0",true)
        controller.gameState should be (INPUTFAIL)
      }
    }
  }
}
