package model

import org.scalatest.{Matchers, WordSpec}

class Evaluationspecs extends WordSpec with Matchers {
  "Evaluation compares the Attack Values of both Players and prints out the Winner of the Round" when{
    "Evaluation" should {
      val field = Field(4,4)
      "have a draw" in {
        Evaluation().eval(field,Player("Stefan") ,Player("Adrian")) should be (0)
      }
      "have playerTop win" in {
        //Player("Stefan").hand.playCard(0, field, 0, 0)
        field.set(0,0,Card("Test",1,1,1))
        Evaluation().eval(field, Player("Stefan"), Player("Adrian")) should be(1)
      }
      "have playerBot win" in {
        //Player("Adrian").hand.playCard(0, field, 2, 2)
        field.set(0,0,Card("",0,0,0))
        field.set(2,2,Card("Test",1,1,1))
        Evaluation().eval(field, Player("Stefan"), Player("Adrian")) should be(-1)
      }
    }
  }
}
