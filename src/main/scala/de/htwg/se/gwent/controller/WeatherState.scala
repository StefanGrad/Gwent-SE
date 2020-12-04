package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.controller.WeatherState.{FOG, FROST, SUNSHINE, WeatherState}

object WeatherState extends Enumeration {
  type WeatherState = Value
  val SUNSHINE,FROST,FOG = Value
}

object weatherChange{
  trait Change {
    def getWeather(): WeatherState
  }
  class Frost extends Change {
    override def getWeather() =  FROST
  }
  class Fog extends Change {
    override def getWeather() = FOG
  }
  class Sunshine extends Change {
    override def getWeather() = SUNSHINE
  }
  def choice(weatherType: WeatherState.Value):Change = weatherType match {
    case FROST => new Frost
    case FOG => new Fog
    case SUNSHINE => new Sunshine
  }
}
