package de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Field, HandCard}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ChoosePlayerSpec extends AnyWordSpec with Matchers {
  "ChoosePlayer uses a ?Pattern to determin which Player is making an Action" when {
    "a Playertype is known it" should {
      "be able to use its Method to return the right Player" in {
        val playerTop = Player(TOP,"Adrian",HandCard(Vector[Card]()).newHandCard(),0)
        val playerBot = Player(BOT,"Stefan",HandCard(Vector[Card]()).newHandCard(),0)
        val controller = new Controller(Field(Vector[Vector[Option[Card]]](),new Sunshine,playerTop,playerBot,0,0))

        choosePlayer.choice(TOP).player(controller) should be (playerTop)
        choosePlayer.choice(BOT).player(controller) should be (playerBot)
      }
    }
  }
}
