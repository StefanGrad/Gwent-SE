import org.scalatest.{Matchers, WordSpec}
import main.model.{Card, Cell}

class CellSpec extends WordSpec with Matchers {

  "A Cell" when {
    "not contains any card " should {
      val emptyCell = Cell(Card("",0,0,0))
      "have no specs" in {
        emptyCell.getCard.cardSpecs should be("", 0, 0, 0)
      }
      "not be set" in {
        emptyCell.isSet should be(false)
      }
    }
    "contains a card" should {
      val nonEmptyCell = Cell(new Card("Archer", 0, 3, 1))
      "return that value" in {
        nonEmptyCell.getCard.cardSpecs should be("Archer", 0, 3, 1)
      }
      "be set" in {
        nonEmptyCell.isSet should be(true)
      }
    }
  }

}