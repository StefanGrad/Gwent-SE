package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.controller.WeatherStatus.{FOG, FROST, SUNSHINE, WeatherState}
import scala.de.htwg.se.gwent.model.Card

object WeatherStatus extends Enumeration {
  type WeatherState = Value
  val SUNSHINE,FROST,FOG = Value
}

object WeatherState{
  trait State {
    val rowBot = Vector[Int](0,1,2,3)
    val rowTop = Vector[Int](0,1,2,3)
    def changeWeather(card : Card): State =
        card.ability match {
      case 0 => choice(SUNSHINE)
      case 1 => choice(FROST)
      case 2 => choice(FOG)
    }
  }
  class Frost extends State {
    val weather = FROST
    override val rowTop = Vector[Int](0)
    override val rowBot = Vector[Int](3)
  }
  class Fog extends State {
    val weather = FOG
    override val rowTop = Vector[Int](1)
    override val rowBot = Vector[Int](2)
  }
  class Sunshine extends State {
    val weather = SUNSHINE
    override val rowTop = Vector[Int](0,1)
    override val rowBot = Vector[Int](2,3)

  }
  def choice(weatherType: WeatherStatus.Value):State = weatherType match {
    case FROST => new Frost
    case FOG => new Fog
    case SUNSHINE => new Sunshine
  }
  var state: State = new Sunshine
}
