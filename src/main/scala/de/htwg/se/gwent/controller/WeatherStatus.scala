package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.controller.WeatherStatus.{FOG, FROST, SUNSHINE, WeatherState}
import scala.de.htwg.se.gwent.model.Card

object WeatherStatus extends Enumeration {
  type WeatherState = Value
  val SUNSHINE,FROST,FOG = Value
}

object WeatherState{
  trait State {
    def changeWeather(card : Card): State =
        card.ability match {
      case 0 => choice(SUNSHINE)
      case 1 => choice(FROST)
      case 2 => choice(FOG)
    }
  }
  class Frost extends State {
    val weather = FROST
  }
  class Fog extends State {
    val weather = FOG
  }
  class Sunshine extends State {
    val weather = SUNSHINE
  }
  def choice(weatherType: WeatherStatus.Value):State = weatherType match {
    case FROST => new Frost
    case FOG => new Fog
    case SUNSHINE => new Sunshine
  }
  var state: State = new Sunshine
}
