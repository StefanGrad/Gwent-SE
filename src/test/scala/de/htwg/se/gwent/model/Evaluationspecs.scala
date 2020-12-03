package scala.de.htwg.se.gwent.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class Evaluationspecs extends AnyWordSpec with Matchers {
  "Evaluation compares the Attack Values of both Players and prints out the Winner of the Round" when{
    "Evaluation" should {
      val field = Field(4,4)
      val p1 = Player(0,"Stefan",HandCard(Vector[Card]()),0)
      val p2 = Player(1,"Adrian",HandCard(Vector[Card]()),0)
      "have a draw" in {
        Evaluation().eval(field,p1 ,p2) should be (0)
      }
      "have playerTop win" in {
        //Player("Stefan").hand.playCard(0, field, 0, 0)
        field.setCard(0,0,Card("Test",1,1,1))
        Evaluation().eval(field, p1, p2) should be(1)
      }
      "have playerBot win" in {
        //Player("Adrian").hand.playCard(0, field, 2, 2)
        field.setCard(0,0,Card("",0,0,0))
        field.setCard(2,2,Card("Test",1,1,1))
        Evaluation().eval(field, p1, p2) should be(2)
      }
    }
  }
}
