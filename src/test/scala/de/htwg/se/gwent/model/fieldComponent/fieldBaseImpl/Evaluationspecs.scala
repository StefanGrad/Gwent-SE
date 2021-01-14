package scala.de.htwg.se.gwent.model

import de.htwg.se.gwent.model.fieldComponent.CardInterface
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Evaluation, Field, HandCard, TurnLogic}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.{Fog, Frost, Sunshine}
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

class Evaluationspecs extends AnyWordSpec with Matchers {
  val f = Field(Vector[Vector[Option[Card]]](),new Sunshine,Player(TOP,"Adrian",HandCard(Vector[Card]()).newDeck(),0),Player(BOT,"Stefan",HandCard(Vector[Card]()).newDeck(),0),TurnLogic(0,0))
  "Evaluation compares the Attack Values of both Players and prints out the Winner of the Round" when{
    "Evaluation" should {
      val field = f.clear
      var weatherState = new Sunshine
      "have a draw" in {
        Evaluation().eval(field,weatherState) should be (2)
      }
      "have playerTop win" in {
        //Player("Stefan").hand.playCard(0, field, 0, 0)
        var field = f.clear
        field = field.setCard(0,0,Some(Card("Test", 1,1,1)))
        Evaluation().eval(field,weatherState) should be(0)
      }
      "have playerBot win" in {
        //Player("Adrian").hand.playCard(0, field, 2, 2)
        var field = f.clear
        field = field.setCard(2,2,Some(Card("Test", 3,3,1)))
        Evaluation().eval(field,weatherState) should be(1)
      }
      "Under Weather Frost" in {
        val weather = new Frost
        var field = f.clear
        field = field.setCard(1,1,Some(Card("Test", 1,1,1)))
        Evaluation().eval(field,weather) should be (2)
      }
      "Under Weather FOG" in {
        val weather = new Fog
        field.setCard(0,0,Some(Card("Test", 1,1,1)))
        Evaluation().eval(field,weather) should be (2)
      }
    }
  }
}
