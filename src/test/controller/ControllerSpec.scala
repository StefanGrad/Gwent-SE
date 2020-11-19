package controller

import model.{Card, Field, HandCard, Player}
import org.scalatest.{Matchers, WordSpec}

class ControllerSpec extends  WordSpec with Matchers {
  "the Controller acts as a medium between model and aview" when {
    "A Controller " should {
      val archer = Card("Archer", 0, 3, 1)
      val field = Field(4, 4)
      val playerTop = Player("Top", HandCard(Vector[Card](archer)))
      val playerBot = Player("Bot", HandCard(Vector[Card](archer)))
      val ctrl = new Controller(field, playerTop, playerBot)
      "create a playing Field" in {
        ctrl.createField should be (Field(4, 4))
      }
      "convert a Field into a String" in {
        ctrl.createField
        ctrl.fieldToString should be ()
      }
      "clear the Field" in {
        ctrl.playCardAt(field, 1,1,playerTop,playerBot)
      }
      "evaluate the Game" in {

      }
      "create the Top Player" in {
      }

      "create the Bot Player" in {
      }

      "play a Card at a chosen Cell" in {

      }

    }

  }

}