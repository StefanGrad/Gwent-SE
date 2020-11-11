import org.scalatest.{Matchers, WordSpec}
import main.model.Card
class Cardspec extends WordSpec with Matchers {
  "A Card is an immutable Combination of a name: String, a Spezial: int and an atkValue: int" when {
    "no Specs are added" should {
      val emptyCard = new Card("", 0, 0, 0)
      "have no specific value" in {
        emptyCard.cardSpecs() should be("", 0, 0, 0)
      }
    }
    "it is spizified" should {
      val nonEmptyCard = new Card("Archer", 0, 3, 1)
      "contain the values" in {
        nonEmptyCard.cardSpecs() should be("Archer", 0, 3, 1)
    }
  }
}