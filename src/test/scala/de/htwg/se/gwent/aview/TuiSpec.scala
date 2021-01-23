package scala.de.htwg.se.gwent.aview

import de.htwg.se.gwent.model.playerComponent
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.controller.controllerComponent.GameStatus.{INPUTFAIL, PASSED, PLAYING}
import de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Field, HandCard}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

class TuiSpec extends AnyWordSpec with Matchers {
  "Tui works as a Text based User Interface" when {
    "a Tui" should {
      val archer = Card("Archer", 0, 3, 1)
      val playerTop = Player(TOP,"Top", HandCard(Vector[Card](archer,archer)),0)
      val playerBot = playerComponent.Player(BOT,"Bot", HandCard(Vector[Card](archer,archer)),0)
      val f = new Field(Vector[Vector[Option[Card]]](),new Sunshine,playerTop,playerBot,0,0)
      val controller = new Controller(f.clear)
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
        controller.field.isEmpty(0,0) should be (false)
        tui.processInputLine("3 3 0",BOT)
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
        tui.processInputLine("5 0 0",TOP)
        controller.gameState should be (INPUTFAIL)
        controller.gameState = PLAYING
        //Wrong row
        tui.processInputLine("0 5 0",TOP)
        controller.gameState should be (INPUTFAIL)
        controller.gameState = PLAYING
        //Wrong card
        tui.processInputLine("1 0 11",TOP)
        controller.gameState should be (INPUTFAIL)
        controller.gameState = PLAYING
        //Wrong Input Form
        tui.processInputLine("1 0",TOP)
        controller.gameState should be (INPUTFAIL)
        controller.gameState = PLAYING
        //Already set
        tui.processInputLine("0 0 0",TOP)
        controller.gameState should be (INPUTFAIL)
      }
    }
  }
}
