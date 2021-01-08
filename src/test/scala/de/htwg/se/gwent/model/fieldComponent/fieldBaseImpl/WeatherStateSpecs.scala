package de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl
import de.htwg.se.gwent.model.cardComponent.cardBaseImpl.Card
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherStatus.FOG
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class WeatherStateSpecs extends AnyWordSpec with Matchers{
 "The Weather changes depending on the played Cards" when {
   "Cards are played" should {
     "trigger changes" in {
       val weather = new Sunshine
       val weatherChanged = weather.changeWeather(Card("fog", 2, 0, 0))
       weatherChanged.weather should be(FOG)

     }
   }
 }
}
