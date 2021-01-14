package de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.TurnLogic
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TurnLogicSpec extends AnyWordSpec with Matchers{
  "A Turn counts the Aktions of the Players in the Game" when {
    "An Action happens" should {
      var turnLogic = new TurnLogic(1,1)
      "the the Turnlogic start counting" in {
        turnLogic.getTurn should be (1)
        turnLogic = turnLogic.doTurn
        turnLogic.getTurn should be (2)
      }
    }
  }

}
