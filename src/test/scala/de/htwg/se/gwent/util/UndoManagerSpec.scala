package scala.de.htwg.se.gwent.util
import de.htwg.se.gwent.util.de.htwg.se.sudoku.util.incrCommand
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.de.htwg.se.gwent.controller.WeatherState.Sunshine
import scala.de.htwg.se.gwent.controller.{Controller, PlayCardCommand}
import scala.de.htwg.se.gwent.model.{Card, Field, HandCard}
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

class UndoManagerSpec extends AnyWordSpec with Matchers {
  "An UndoManager" should {
    val undoManager = new UndoManager

    "have a do, undo and redo" in {
      val command = new incrCommand
      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.undoStep
      command.state should be(0)
      undoManager.redoStep
      command.state should be(1)
    }

    "handle multiple undo steps correctly" in {
      val command = new incrCommand
      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.doStep(command)
      command.state should be(2)
      undoManager.undoStep
      command.state should be(1)
      undoManager.undoStep
      command.state should be(0)
      undoManager.redoStep
      command.state should be(1)
      undoManager.redoStep
      command.state should be(2)
      undoManager.redoStep
      command.state should be(2)
    }
    "And can be cleared for a new Round" in {
      val command = new incrCommand
      command.state should be(0)
      undoManager.doStep(command)
      command.state should be(1)
      undoManager.doStep(command)
      command.state should be(2)
      undoManager.nextRound
      undoManager.undoStep
      command.state should be(2)
    }
  }
}
