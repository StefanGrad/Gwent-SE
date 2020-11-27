package de.htwg.se.qwent.aview

import de.htwg.se.qwent.controller.Controller
import de.htwg.se.qwent.model.{Card, Field, HandCard, Player}
import org.scalatest.{Matchers, WordSpec}

class TuiSpec extends WordSpec with Matchers {
  "Tui works as a Text based User Interface" when {
    "a Tui" should {
      val archer = Card("Archer", 0, 3, 1)
      val field = Field(4, 4)
      val playerTop = Player("Top", HandCard(Vector[Card](archer,archer)),0)
      val playerBot = Player("Bot", HandCard(Vector[Card](archer,archer)),0)
      val controller = new Controller(field, playerTop, playerBot)
      val tui = new Tui(controller)

      "have the active player pass on input 'c' and if he's the second one to pass the game is evaluated" in {
        tui.processInputLineTop("c")
        tui.topPassed should be (true)
        tui.botPassed should be (false)
        tui.processInputLineBot("c")
        tui.topPassed should be (false)
        tui.botPassed should be (false)

        tui.processInputLineBot("c")
        tui.topPassed should be (false)
        tui.botPassed should be (true)
        tui.processInputLineTop("c")
        tui.topPassed should be (false)
        tui.botPassed should be (false)
      }

      "place the card with the chosen index into the chosen cell" in {
        tui.processInputLineTop("0 0 0")
        controller.playCardAt(field, 0, 0, playerTop, 0)
        field.isEmpty(0,0) should be (false)
        tui.processInputLineBot("2 2 0")
        controller.playCardAt(field, 0, 0, playerBot, 0)
        field.isEmpty(2,2) should be (false)
        tui.processInputLineTop("2 2 11")
        tui.failedInput should be (true)
        tui.processInputLineBot("2 2 11")
        tui.failedInput should be (true)

      }
    }
  }
}
