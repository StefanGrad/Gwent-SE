package de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl

import de.htwg.se.gwent.model.cardComponent.CardInterface
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherStatus.{FOG, FROST, SUNSHINE}

object WeatherStatus extends Enumeration {
  type WeatherState = Value
  val SUNSHINE,FROST,FOG = Value
}

object WeatherState{
  trait State {
    val rowBot = Vector[Int](0,1,2,3)
    val rowTop = Vector[Int](0,1,2,3)
    val weather = SUNSHINE
    def changeWeather(card : CardInterface): State =
        card.ability match {
      case 0 => this
      case 1 => choice(FROST)
      case 2 => choice(FOG)
      case 3 => choice(SUNSHINE)
    }
    def changeWeather(weatherStatus: WeatherStatus.Value): State = choice(weatherStatus)
  }
  class Frost extends State {
    override val weather = FROST
    override val rowTop = Vector[Int](0)
    override val rowBot = Vector[Int](3)
  }
  class Fog extends State {
    override val weather = FOG
    override val rowTop = Vector[Int](1)
    override val rowBot = Vector[Int](2)
  }
  class Sunshine extends State {
    override val weather = SUNSHINE
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
