import org.scalatest.{Matchers, WordSpec}
import main.model.Card

class CardSpec extends WordSpec with Matchers {
  "A Card is an immutable Combination of a name: String, a Spezial: int, an atkValue: int and a Range: Int" when {
    "a Card" should {
      "have a Value" in {
        Card("Archer", 0, 3, 1) shouldBe a [Card]
      }
      "be empty" in {
        Card("",0,0,0).isEmpty should be (true)
      }
      "have specs" in {
        Card("Archer", 0, 3, 1).cardSpecs should be ("Archer", 0, 3, 1)
      }
      "can be converted to a nice String" in {
        Card("Archer", 0 ,3, 1).toString should be("Archer A0 S3")
      }
    }
  }
}