package scala.de.htwg.se.gwent.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.de.htwg.se.gwent.controller.WeatherState.{Fog, Frost, Sunshine}
import scala.de.htwg.se.gwent.controller.WeatherStatus.{FOG, FROST, SUNSHINE}
import scala.de.htwg.se.gwent.model.PlayerType.{BOT, TOP}

class Evaluationspecs extends AnyWordSpec with Matchers {
  val f = Field(Vector[Vector[Option[Card]]]())
  "Evaluation compares the Attack Values of both Players and prints out the Winner of the Round" when{
    "Evaluation" should {
      val field = f.clear
      var weatherState = new Sunshine
      val p1 = Player(TOP,"Stefan",HandCard(Vector[Card]()),0)
      val p2 = Player(BOT, "Adrian",HandCard(Vector[Card]()),0)
      "have a draw" in {
        Evaluation().eval(field,p1 ,p2,weatherState) should be ("The game ended with a tie")
      }
      "have playerTop win" in {
        //Player("Stefan").hand.playCard(0, field, 0, 0)
        var field = f.clear
        field = field.setCard(0,0,Some(Card("Test", 1,1,1)))
        Evaluation().eval(field, p1, p2,weatherState) should be("The winner of this round is Top")
      }
      "have playerBot win" in {
        //Player("Adrian").hand.playCard(0, field, 2, 2)
        var field = f.clear
        field = field.setCard(2,2,Some(Card("Test", 3,3,1)))
        Evaluation().eval(field, p1, p2,weatherState) should be("The winner of this round is Bot")
      }
      "Under Weather Frost" in {
        val weather = new Frost
        var field = f.clear
        field = field.setCard(1,1,Some(Card("Test", 1,1,1)))
        Evaluation().eval(field,p1 ,p2,weather) should be ( "The game ended with a tie")
      }
      "Under Weather FOG" in {
        val weather = new Fog
        field.setCard(0,0,Some(Card("Test", 1,1,1)))
        Evaluation().eval(field,p1 ,p2,weather) should be ( "The game ended with a tie")
      }
    }
  }
}
