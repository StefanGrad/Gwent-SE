package de.htwg.se.gwent.model.fieldComponent.realFieldImpl
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherStatus.SUNSHINE
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FieldSpec extends AnyWordSpec with Matchers {
  "For the Dependency Injection this Field is used as Constructor" when {
    "The game is started it initialises the Field used for the Controller" should {
      "have the Basic Values" in {
        val realField = new Field

        realField.playerTop.name should be ("Adrian")
        realField.playerTop.handCard.size should be (10)

        realField.playerBot.name should be ("Stefan")
        realField.playerTop.handCard.size should be (10)

        realField.weather.weather should be (SUNSHINE)

        realField.turn should be(0)
        realField.round should be(0)
      }
    }
  }
}
