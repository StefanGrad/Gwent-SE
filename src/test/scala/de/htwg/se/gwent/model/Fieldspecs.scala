package scala.de.htwg.se.gwent.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class Fieldspecs extends AnyWordSpec with Matchers {
  val f = new Field(Vector[Vector[Option[Card]]]())
  "A Field is a 4x4 Array of Arrays with are then filled with Cards" when {
    "a Field" should {
      val field = f.clear
      "initially be filled with empty Cards" in {
        field.getCard(0,0) should be (None)
        field.getCard(0,1) should be (None)
        field.getCard(0,2) should be (None)
        field.getCard(0,3) should be (None)
        field.getCard(1,0) should be (None)
        field.getCard(1,1) should be (None)
        field.getCard(1,2) should be (None)
        field.getCard(1,3) should be (None)
        field.getCard(2,0) should be (None)
        field.getCard(2,1) should be (None)
        field.getCard(2,2) should be (None)
        field.getCard(2,3) should be (None)
        field.getCard(3,0) should be (None)
        field.getCard(3,1) should be (None)
        field.getCard(3,2) should be (None)
        field.getCard(3,3) should be (None)
      }
    }
      "place a Card in the chosen spot" in {
        var field = f.clear
        field = field.setCard(0,0,Some(Card("Archer", 0, 3, 1)))
        field.getCard(0,0).get should be (Card("Archer", 0, 3, 1))
      }
      "get a Card out of a chosen spot" in {
        var field = f.clear
        field = field.setCard(0,0,Some(Card("Archer", 0, 3, 1)))
        field.getCard(0,0).get should be (Card("Archer", 0, 3, 1))
      }
      "is full when" in {
        var field = f.clear
        val archer = Some(Card("Archer", 0, 3, 1))
        field = field.setCard(0,0,archer)
        field = field.setCard(0,1,archer)
        field = field.setCard(0,2,archer)
        field = field.setCard(0,3,archer)
        field = field.setCard(1,0,archer)
        field = field.setCard(1,1,archer)
        field = field.setCard(1,2,archer)
        field = field.setCard(1,3,archer)
        field = field.setCard(2,0,archer)
        field = field.setCard(2,1,archer)
        field = field.setCard(2,2,archer)
        field = field.setCard(2,3,archer)
        field = field.setCard(3,0,archer)
        field = field.setCard(3,1,archer)
        field = field.setCard(3,2,archer)
        field.isNotFull(0,4) should be (true)
        field = field.setCard(3,3,archer)
        field.isNotFull(0,4) should be (false)
      }
    "is converted toString" in {
      f.clear.toString should be ("\n+---------------+---------------+\n" + "|None|None|None|None|\n|None|None|None|None|\n+---------------+---------------+\n|None|None|None|None|\n|None|None|None|None|\n+---------------+---------------+\n")
      }
    "can be cleared" in {
      val archer = Some(Card("Archer", 0, 3, 1))
      var field = f.clear
      field = field.setCard(0,0,archer)
      field.isEmpty(0,0) should be (false)
      field = field.clear
      field.isEmpty(0,0) should be (true)
    }
    }
}
