package de.htwg.se.gwent.model.fileIOComponent.FileIOXmlImpl

import com.google.inject.Guice
import de.htwg.se.gwent.GwentModule
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.{Fog, Frost, Sunshine}
import de.htwg.se.gwent.model.fieldComponent.{CardInterface, FieldInterface}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, HandCard}
import de.htwg.se.gwent.model.fileIOComponent.FileIOInterface
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

import scala.xml.{NodeSeq, PrettyPrinter}
import net.codingwell.scalaguice.InjectorExtensions._

import scala.collection.immutable.VectorBuilder
import scala.collection.mutable.ListBuffer

class FileIO extends FileIOInterface {
  override def load: FieldInterface = {
    var field: FieldInterface = null
    val file = scala.xml.XML.loadFile("field.xml")
    val injector = Guice.createInjector(new GwentModule)
    val nPlayer = (file \\ "player")
    var playerTop = Player(TOP,"",HandCard(Vector[CardInterface]()),0)
    var playerBot = Player(TOP,"",HandCard(Vector[CardInterface]()),0)
    nPlayer.foreach{player =>
      val name = (player \ "@name").text.toString
      val nHand = (player \ "@hand")
      val hand = new ListBuffer[Card]
      nHand.foreach{n => hand.append(Card((n \ "@name").text.toString,(n\"@ability").text.toInt,(n \ "@strength").text.toInt,(n \ "@range").text.toInt))}
      val wins = (player \ "@wins").text.toInt
      val nType = (player \ "@type").text.toString
      nType match {
        case "TOP" => playerTop = Player(TOP,name,HandCard(hand.toVector),wins)
        case "BOT" => playerBot = Player(BOT,name,HandCard(hand.toVector),wins)
      }
    }
    val turn = (file \ "@turn").text.toInt
    val round = (file \ "@round").text.toInt
    val fWeather = (file \ "@weather").text.toString
    val weather = fWeather match {
      case "FROST" => new Frost
      case "SUNSHINE"  => new Sunshine
      case "FOG" => new Fog
    }
    val nVecArr = (file \\ "field")
    nVecArr.foreach{

    }
  }


  override def save(grid: FieldInterface): Unit = ???
}
