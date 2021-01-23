package de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherStatus.{FOG, FROST, SUNSHINE}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class WeatherStateSpec extends AnyWordSpec with Matchers{
  "The Weather changes depending on the played Cards" when {
    "Cards are played" should {
      val weather = new Sunshine
      "change to the weather when triggered" in {
        var weatherChanged = weather.changeWeather(Card("FOG", 2, 0, 3))
        weatherChanged.weather should be(FOG)
        weatherChanged = weather.changeWeather(Card("FROST", 1, 0, 3))
        weatherChanged.weather should be(FROST)
        weatherChanged = weather.changeWeather(Card("SUNSHINE", 3, 0, 3))
        weatherChanged.weather should be(SUNSHINE)
        weatherChanged = weather.changeWeather(Card("Archer", 0, 3, 1))
        weatherChanged should be (weather)
      }
    }
  }
}