package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.model.{Card, Field, HandCard, Player}
import scala.de.htwg.se.gwent.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.de.htwg.se.gwent.model.PlayerType.{TOP,BOT}

class ControllerSpec extends AnyWordSpec with Matchers {
  "the Controller acts as a medium between de.htwg.se.de.htwg.se.qwent.qwent.model and de.htwg.se.de.htwg.se.qwent.qwent.aview" when {
    "A Controller " should {
      val archer = Card("Archer", 0, 3, 1)
      val field = Field(Vector[Vector[Option[Card]]]()).clear
      val playerTop = Player(TOP,"Top", HandCard(Vector[Card](archer,archer,archer)),0)
      val playerBot = Player(BOT,"Bot", HandCard(Vector[Card](archer,archer,archer)),0)
      val ctrl = new Controller(field, playerTop, playerBot)
      val observer = new Observer {
        var updated: Boolean = false
        def isUpdated: Boolean = updated
        override def update:Boolean = {updated = true; true}
      }
      ctrl.add(observer)
      "create a playing Field" in {
        ctrl.createField
        ctrl.field.size should be(4)
        observer.updated should be(true)

      }
      "convert a Field into a String" in {
        ctrl.createField
        ctrl.fieldToString should be ("\n+---------------+---------------+\n" + "|None|None|None|None|\n|None|None|None|None|\n+---------------+---------------+\n|None|None|None|None|\n|None|None|None|None|\n+---------------+---------------+\n")
      }
      "clear the Field" in {
        ctrl.playCardAt(field, 1,1,TOP,0)
        observer.updated should be(true)
        ctrl.clearField(field)
        observer.updated should be(true)
        ctrl.field.isEmpty(1,1) should be(true)
      }
      "evaluate the Game" in {
        val ctrl = new Controller(field,playerTop,playerBot)
        ctrl.playCardAt(ctrl.field,3,3,BOT,0)
        observer.updated should be(true)
        ctrl.evaluate(ctrl.field,ctrl.playerTop,ctrl.playerBot)
        observer.updated should be(true)
        ctrl.playerTop.wins should be(0)
        ctrl.playerBot.wins should be(1)
        ctrl.playCardAt(ctrl.field,0,0,TOP,0)
        observer.updated should be(true)
        ctrl.evaluate(ctrl.field,ctrl.playerTop,ctrl.playerBot)
        observer.updated should be(true)
        ctrl.playerTop.wins should be(1)
        ctrl.playerBot.wins should be(1)
        ctrl.evaluate(ctrl.field,ctrl.playerTop,ctrl.playerBot)
        observer.updated should be(true)
        ctrl.playerTop.wins should be(1)
        ctrl.playerBot.wins should be(1)
        observer.updated should be(true)
      }
      "create the Top Player" in {
        ctrl.createPlayer("Stefan",TOP)
        observer.updated should be(true)
        ctrl.playerTop.name should be("Stefan")
      }
      "create the Bot Player" in {
        ctrl.createPlayer("Stefan",BOT)
        observer.updated should be(true)
        ctrl.playerBot.name should be("Stefan")
      }
      "turn a player into a String" in {
        ctrl.playerTop = Player(TOP,"Adrian", HandCard(Vector[Card](archer)),0)
        ctrl.playerToString(ctrl.playerTop) should be("Adrian has won 0 times and holds in his Hand: Archer A0 S3 R1")
      }
      "play a Card at a chosen Cell" in {
        ctrl.clearField(field)
        ctrl.playCardAt(field, 1,1,TOP,0)
        observer.updated should be(true)
        ctrl.field.getCard(1,1) should be(archer)
      }
    }
  }
}