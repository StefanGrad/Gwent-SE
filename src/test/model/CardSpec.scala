import org.scalatest.{Matchers, WordSpec}
import main.model.Card

class CardSpec extends WordSpec with Matchers {
  "A Card is an immutable Combination of a name: String, a Spezial: int, an atkValue: int and a Range: Int" when {
    "added" should {
      val emptyCard = new Card("", 0, 0, 0)
      val nonEmptyCard = new Card("Archer", 0, 3, 1)
      "be or not be Empty" in {
        emptyCard.isEmpty should be(true)
        emptyCard.isEmpty should be(false)
      }
    }
    "when not empty" should {
      val nonEmptyCard = new Card("Archer", 0, 3, 1)
      "contain the values" in {
        nonEmptyCard.cardSpecs should be("Archer", 0, 3, 1)
      }
    }
    "can be converted to String" should{
      val nonEmptyCard = new Card("Archer", 0, 3, 1)
      "as a toString" in {
        nonEmptyCard.toString should be("Archer A3 S1")
      }
    }
  }
}