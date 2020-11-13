
import org.scalatest.{Matchers, WordSpec}
import main.model.{Card, Field}

class Fieldspecs extends WordSpec with Matchers {
  "A Field is a 4x4 Array of Arrays with are then filled with Cards" when {
    "empty" should {
      val field = Field(4,4)
      "only be filled with empty Cards" in {
        for {
          row <- 0 until field.row
          col <- 0 until field.col
        } field.isEmpty(col, row) should be (true)
      }
    }
    "is set" should {
      val field = Field(4,4)
      "place a Card in the chosen spot" in {
        field.set(0,0, new Card("Archer", 0, 3, 1)) should be (Card("Archer", 0, 3, 1))
      }
    }
    "can be Converted to String" should {
      val field = Field(4,4)
      "have a nice TUI layout" in {
        field.toString should be ("+---------------+---------------+\n|    |    |    |    |\n|    |    |    |    |\n+---------------+---------------+\n|    |    |    |    |\n|    |    |    |    |\n+---------------+---------------+")
      }

    }
  }
}
