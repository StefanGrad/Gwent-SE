package model
import org.scalatest.{Matchers, WordSpec}

class Fieldspecs extends WordSpec with Matchers {
  "A Field is a 4x4 Array of Arrays with are then filled with Cards" when {
    "a Field" should {
      val emptyCard = Card("",0,0,0)
      "initially be filled with empty Cards" in {
        Field(4,4).get(0,0) should be (emptyCard)
        Field(4,4).get(0,1) should be (emptyCard)
        Field(4,4).get(0,2) should be (emptyCard)
        Field(4,4).get(0,3) should be (emptyCard)
        Field(4,4).get(1,0) should be (emptyCard)
        Field(4,4).get(1,1) should be (emptyCard)
        Field(4,4).get(1,2) should be (emptyCard)
        Field(4,4).get(1,3) should be (emptyCard)
        Field(4,4).get(2,0) should be (emptyCard)
        Field(4,4).get(2,1) should be (emptyCard)
        Field(4,4).get(2,2) should be (emptyCard)
        Field(4,4).get(2,3) should be (emptyCard)
        Field(4,4).get(3,0) should be (emptyCard)
        Field(4,4).get(3,1) should be (emptyCard)
        Field(4,4).get(3,2) should be (emptyCard)
        Field(4,4).get(3,3) should be (emptyCard)
      }
    }
      "place a Card in the chosen spot" in {
          Field(4,4).set(0,0,Card("Archer", 0, 3, 1)) should be (Card("Archer",0,3,1))
      }
      "get a Card out of a chosen spot" in {
        val field = Field(4,4)
        field.set(0,0,Card("Archer",0,3,1))
        field.get(0,0) should be (Card("Archer",0,3,1))
      }
      "is full when" in {
        val field = Field(4,4)
        val archer = Card("Archer", 0, 3, 1)
        field.set(0,0,archer)
        field.set(0,1,archer)
        field.set(0,2,archer)
        field.set(0,3,archer)
        field.set(1,0,archer)
        field.set(1,1,archer)
        field.set(1,2,archer)
        field.set(1,3,archer)
        field.set(2,0,archer)
        field.set(2,1,archer)
        field.set(2,2,archer)
        field.set(2,3,archer)
        field.set(3,0,archer)
        field.set(3,1,archer)
        field.set(3,2,archer)
        field.isNotFull(0,4) should be (true)
        field.set(3,3,archer)
        field.isNotFull(0,4) should be (false)
      }
    "is converted toString" in {
      Field(4,4).toString should be ("\n+---------------+---------------+\n" + "|(0,0)|(0,1)|(0,2)|(0,3)|\n|(1,0)|(1,1)|(1,2)|(1,3)|\n+---------------+---------------+\n|(2,0)|(2,1)|(2,2)|(2,3)|\n|(3,0)|(3,1)|(3,2)|(3,3)|\n+---------------+---------------+\n")
      }
    "can be cleared" in {
      val archer = Card("Archer", 0, 3, 1)
      val field = Field(4,4)
      field.set(0,0,archer) should be (Card("Archer",0,3,1))
      field.isEmpty(0,0) should be (false)
      field.clear(field)
      field.isEmpty(0,0) should be (true)
    }
    }
}
