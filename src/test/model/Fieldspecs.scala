
import org.scalatest.{Matchers, WordSpec}
import main.model.{Card, Field}

class Fieldspecs extends WordSpec with Matchers {
  "A Field is a 4x4 Array of Arrays with are then filled with Cards" when {
    "a Field" should {
      val emptyCard = new Card("",0,0,0)
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
      "place a the String of a Card in the chosen spot" in {
          Field(4,4).set(0,0,Card("Archer", 0, 3, 1)) should be (Card("Archer",0,3,1))
      }
    }
    "can be Converted to String" should {
      val field = Field(4,4)
      "have a nice TUI layout" in {
        field.toString should be ("+---------------+---------------+\n|    |    |    |    |\n|    |    |    |    |\n+---------------+---------------+\n|    |    |    |    |\n|    |    |    |    |\n+---------------+---------------+")
      }

    }
}
