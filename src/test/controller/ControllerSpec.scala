package controller

import model.{Card, Field, HandCard, Player}
import org.scalatest.{Matchers, WordSpec}
import util.Observer

class ControllerSpec extends  WordSpec with Matchers {
  "the Controller acts as a medium between model and aview" when {
    "A Controller " should {
      val archer = Card("Archer", 0, 3, 1)
      val field = Field(4, 4)
      val playerTop = Player("Top", HandCard(Vector[Card](archer)),0)
      val playerBot = Player("Bot", HandCard(Vector[Card](archer)),0)
      val ctrl = new Controller(field, playerTop, playerBot)
      val observer = new Observer {
        var updated: Boolean = false
        def isUpdated: Boolean = updated
        override def update: Unit = updated = true
      }
      ctrl.add(observer)
      "create a playing Field" in {
        ctrl.createField
        ctrl.field.size should be(4)
        observer.updated should be(true)

      }
      "convert a Field into a String" in {
        ctrl.createField
        ctrl.fieldToString should be ("\n+---------------+---------------+\n" + "|(0,0)|(0,1)|(0,2)|(0,3)|\n|(1,0)|(1,1)|(1,2)|(1,3)|\n+---------------+---------------+\n|(2,0)|(2,1)|(2,2)|(2,3)|\n|(3,0)|(3,1)|(3,2)|(3,3)|\n+---------------+---------------+\n")
      }
      "clear the Field" in {
        ctrl.playCardAt(field, 1,1,Player("Top", HandCard(Vector[Card](archer)),0),0)
        observer.updated should be(true)
        ctrl.clearField(field)
        observer.updated should be(true)
        ctrl.field.isEmpty(1,1) should be(true)
      }
      "evaluate the Game" in {
        ctrl.createField
        ctrl.playCardAt(field,0,0,playerBot,0)
        ctrl.evaluate(field,playerTop,playerBot) should be (4)
        ctrl.clearField(field)
        ctrl.playCardAt(field,3,3,playerBot,0)
        ctrl.evaluate(field,playerTop,playerBot) should be ("ugf")
        ctrl.clearField(field)
        ctrl.evaluate(field,playerTop,playerBot) should be ("uzg")
        observer.updated should be(true)
      }
      "create the Top Player" in {
        ctrl.createPlayerTop("Stefan")
        observer.updated should be(true)
        ctrl.playerTop.name should be("Stefan")
      }
      "create the Bot Player" in {
        ctrl.createPlayerBot("Stefan")
        observer.updated should be(true)
        ctrl.playerBot.name should be("Stefan")
      }
      "turn a player into a String" in {
        ctrl.playerTop = Player("Adrian", HandCard(Vector[Card](archer)),0)
        ctrl.playerToString(ctrl.playerTop) should be("Adrian holds in his Hand: Archer A0 S3 R1")
      }
      "play a Card at a chosen Cell" in {
        ctrl.playCardAt(field, 1,3,ctrl.playerTop,0)
        observer.updated should be(true)
        ctrl.field.get(3,1) should be(archer)
      }
    }
  }
}