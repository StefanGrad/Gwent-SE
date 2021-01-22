package scala.de.htwg.se.gwent.model

import de.htwg.se.gwent.model.fieldComponent.CardInterface
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Evaluation, Field, HandCard}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.{Fog, Frost, Sunshine}
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

class Evaluationspecs extends AnyWordSpec with Matchers {
  val f = Field(Vector[Vector[Option[Card]]](),new Sunshine,Player(TOP,"Adrian",HandCard(Vector[Card]()).newHandCard(),0),Player(BOT,"Stefan",HandCard(Vector[Card]()).newHandCard(),0),0,0)
  "Evaluation compares the Attack Values of both Players and retuns an Integer" when{
    "Evaluation under normal Weather(Sunshine)" should {
      val field = f.clear
      val weatherState = new Sunshine
      "have a draw" in {
        Evaluation().eval(field,weatherState) should be (2)
      }
      "have playerTop win" in {
        var field = f.clear
        field = field.setCard(0,0,Some(Card("Test", 0,1,1)))
        Evaluation().eval(field,weatherState) should be(0)
      }
      "have playerBot win" in {
        var field = f.clear
        field = field.setCard(2,2,Some(Card("Test", 0,1,1)))
        Evaluation().eval(field,weatherState) should be(1)
      }
      "Under Weather Frost the front rows (1 and 2) are not counted" in {
        val weather = new Frost
        var field = f.clear
        field = field.setCard(1,1,Some(Card("Test", 0,1,0)))
        field = field.setCard(2,2,Some(Card("Test", 0,3,0)))
        Evaluation().eval(field,weather) should be (2)
      }
      "Under Weather FOG the back rows (0 and 3) are not counted" in {
        val weather = new Fog
        var field = f.clear
        field = field.setCard(0,0,Some(Card("Test", 1,1,1)))
        field = field.setCard(3,3,Some(Card("Test", 0,3,0)))
        Evaluation().eval(field,weather) should be (2)
      }
    }
  }
}
