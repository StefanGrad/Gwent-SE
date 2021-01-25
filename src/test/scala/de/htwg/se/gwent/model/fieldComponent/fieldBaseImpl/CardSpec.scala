package de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl

import de.htwg.se.gwent.model.fieldComponent.CardInterface
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CardSpec extends AnyWordSpec with Matchers {
  "A Card is an immutable Combination of a name: String, a Spezial: int, an atkValue: int and a Range: Int" when {
    "a Card" should {
      "have a Value" in {
        val archer = Card("Archer", 0, 3, 1) //shouldBe a [Card]
        archer.name should be("Archer")
        archer.ability should be(0)
        archer.strength should be(3)
        archer.range should be(1)
        archer shouldBe a [CardInterface]
    }
      "be empty" in {
        Card("",0,0,0).isEmpty should be (true)
      }
      "can be converted to a nice String" in {
        Card("Archer", 0 ,3, 1).toString should be("Archer S3 R1")
      }
    }
  }
}