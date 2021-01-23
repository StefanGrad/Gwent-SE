package de.htwg.se.gwent.model.FileIOComponent.FileIOJsonImpl

import de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Field, HandCard}
import de.htwg.se.gwent.model.fileIOComponent.FileIOJsonImpl.FileIO
import de.htwg.se.gwent.model.playerComponent
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class FileIOSpec extends AnyWordSpec with Matchers {
  "The File IO" when {
    "it is used by the GwentModule" should {
      "be able to safe a Field as an Json-File and load Json-File into a Field" in {
        val archer = Card("Archer", 0, 3, 1)
        val playerTop = Player(TOP,"Top", HandCard(Vector[Card](archer,archer)),0)
        val playerBot = Player(BOT,"Bot", HandCard(Vector[Card](archer,archer)),0)
        val f = new Field(Vector[Vector[Option[Card]]](),new Sunshine,playerTop,playerBot,0,0)
        var field = f.clear
        val fileIO = new FileIO

        field = field.setCard(0,0,Some(archer))
        field.isEmpty(0,0) should be (false)

        fileIO.save(field)

        field = field.setCard(3,3,Some(archer))
        field.isEmpty(3,3) should be (false)

        val loadedField = fileIO.load

        loadedField.isEmpty(0,0) should be (false)
        loadedField.isEmpty(3,3) should be (true)
      }
    }
  }
}
