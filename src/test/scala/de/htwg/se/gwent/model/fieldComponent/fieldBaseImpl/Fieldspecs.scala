package de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl

import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.{Fog, Sunshine}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherStatus.{FOG, SUNSHINE}
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class Fieldspecs extends AnyWordSpec with Matchers {
  val f = new Field(Vector[Vector[Option[Card]]](),new Sunshine,Player(TOP,"Adrian",HandCard(Vector[Card]()).newHandCard(),0),Player(BOT,"Stefan",HandCard(Vector[Card]()).newHandCard(),0),0,0)
  "A Field is a 4x4 Array of Arrays with are then filled with Cards" when {
    "the game is started, the clear method is used to generate a playable empty Field" should {
      val field = f.clear
      "initially be filled with None from Option[CardInterface]" in {
        field.getCard(0, 0) should be(None)
        field.getCard(0, 1) should be(None)
        field.getCard(0, 2) should be(None)
        field.getCard(0, 3) should be(None)
        field.getCard(1, 0) should be(None)
        field.getCard(1, 1) should be(None)
        field.getCard(1, 2) should be(None)
        field.getCard(1, 3) should be(None)
        field.getCard(2, 0) should be(None)
        field.getCard(2, 1) should be(None)
        field.getCard(2, 2) should be(None)
        field.getCard(2, 3) should be(None)
        field.getCard(3, 0) should be(None)
        field.getCard(3, 1) should be(None)
        field.getCard(3, 2) should be(None)
        field.getCard(3, 3) should be(None)
      }
    }
    "place a Card in the chosen spot" in {
      var field = f.clear
      field = field.setCard(0, 0, Some(Card("Archer", 0, 3, 1)))

      field.getCard(0, 0).get should be(Card("Archer", 0, 3, 1))
    }
    "get a Card out of a chosen spot" in {
      var field = f.clear
      field = field.setCard(0, 0, Some(Card("Archer", 0, 3, 1)))

      field.getCard(0, 0).get should be(Card("Archer", 0, 3, 1))
    }
    "is full when" in {
      var field = f.clear
      val archer = Some(Card("Archer", 0, 3, 1))

      field = field.setCard(0, 0, archer)
      field = field.setCard(0, 1, archer)
      field = field.setCard(0, 2, archer)
      field = field.setCard(0, 3, archer)
      field = field.setCard(1, 0, archer)
      field = field.setCard(1, 1, archer)
      field = field.setCard(1, 2, archer)
      field = field.setCard(1, 3, archer)
      field = field.setCard(2, 0, archer)
      field = field.setCard(2, 1, archer)
      field = field.setCard(2, 2, archer)
      field = field.setCard(2, 3, archer)
      field = field.setCard(3, 0, archer)
      field = field.setCard(3, 1, archer)
      field = field.setCard(3, 2, archer)
      field.isNotFull(0, 4) should be(true)
      field = field.setCard(3, 3, archer)

      field.isNotFull(0, 4) should be(false)
    }
    "is converted toString" in {
      f.clear.toString should be("\n+---------------+---------------+\n"
        + "|None|None|None|None|\n|None|None|None|None|\n"
        + "+---------------+---------------+\n"
        +"|None|None|None|None|\n|None|None|None|None|\n"
        + "+---------------+---------------+\n")
    }
    "can change Weather with a Card" in {
      val field = f.clear

      val cf = field.changeWeather(Card("fog", 2, 0, 0))
      cf.weather.weather should be(FOG)
    }
    "can change Weather with a Weatherstatus Value" in {
      var field = f.clear

      field = field.changeWeather((new Fog).weather)
      field.weather.weather should be(FOG)
    }
    "can be cleared which resets the weather also" in {
      val archer = Some(Card("Archer", 0, 3, 1))
      var field = f.clear

      field = field.changeWeather((new Fog).weather)
      field.weather.weather should be(FOG)

      field = field.setCard(0, 0, archer)
      field.isEmpty(0, 0) should be(false)

      field = field.clear
      field.isEmpty(0, 0) should be(true)
      field.weather.weather should be(SUNSHINE)
    }
    "can count in which turn and round(every new round the turns get reset) it is " in {
      var field = f.clear

      field.turn should be(0)
      field.round should be(0)

      field = field.doTurn
      field.turn should be(1)

      field = field.nextRound
      field.round should be(1)
      field.turn should be(0)
    }
    "can decide which player can play. For every even turn -> Top PLayer and uneven -> Bot PLayer can play" in {
      var field = f.clear

      field.turn should be(0)
      field.whoCanPlay should be (TOP)

      field = field.doTurn
      field.turn should be(1)
      field.whoCanPlay should be (BOT)
    }
    "reverse a turn when the undo button function is used" in {
      var field = f.clear

      field = field.doTurn
      field.turn should be(1)
      field.whoCanPlay should be (BOT)

      field = field.undoTurn
      field.turn should be(0)
      field.whoCanPlay should be (TOP)
    }
    "add a win for the Player when the evaluation is done" in  {
      val field = f.clear
      field.playerTop.wins should be (0)
      field.playerBot.wins should be (0)
      field.updateWins(TOP).playerTop.wins should be (1)
      field.updateWins(BOT).playerBot.wins should be (1)
    }
  }
}
