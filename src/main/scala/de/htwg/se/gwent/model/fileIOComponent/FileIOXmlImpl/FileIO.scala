package de.htwg.se.gwent.model.fileIOComponent.FileIOXmlImpl

import com.google.inject.Guice
import de.htwg.se.gwent.GwentModule
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.{Fog, Frost, Sunshine}
import de.htwg.se.gwent.model.fieldComponent.{CardInterface, FieldInterface}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Field, HandCard}
import de.htwg.se.gwent.model.fileIOComponent.FileIOInterface
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

import scala.xml.PrettyPrinter

class FileIO extends FileIOInterface {


  override def load: FieldInterface = {
    val file = scala.xml.XML.loadFile("field.xml")
    val injector = Guice.createInjector(new GwentModule)
    val turn = (file \\ "game" \ "turn").text.trim.toInt
    val round = (file \\ "game" \ "round").text.trim.toInt
    val fWeather = (file \\ "game" \ "weather").text.trim
    val weather = fWeather match {
      case "FROST" => new Frost
      case "SUNSHINE"  => new Sunshine
      case "FOG" => new Fog
    }
    val playerVector = (file \\ "game" \ "players")
    var playerTop = Player(TOP,"",HandCard(Vector[CardInterface]()),0)
    var playerBot = Player(BOT,"",HandCard(Vector[CardInterface]()),0)
    for (player <- playerVector \ "player") {
      val name = (player \ "name").text.trim
      val wins = (player \ "wins").text.trim.toInt
      val handVector = (player \ "hand")
      val handBuilder = Vector.newBuilder[CardInterface]
      for (card <- handVector \ "card") {
        val name = (card \ "name").text.trim
        val ability = (card \ "ability").text.trim.toInt
        val strength = (card \ "strength").text.trim.toInt
        val range = (card \ "range").text.trim.toInt
        handBuilder.+=(Card(name,ability,strength,range))
      }
      val nType = (player \ "type").text.trim
      nType match {
        case "TOP" => playerTop = Player(TOP,name,HandCard(handBuilder.result()),wins)
        case "BOT" => playerBot = Player(BOT,name,HandCard(handBuilder.result()),wins)
      }
    }
    val fieldMatrix = file \\ "game" \ "field"
    val vectorBuilder = Vector.newBuilder[Vector[Option[CardInterface]]]
    for (row <- (fieldMatrix \ "card").sliding(4, 4)) {
      val rowBuilder = Vector.newBuilder[Option[CardInterface]]
      for (card <- row) {
        val name = (card \ "name").text.trim
        val ability = (card \ "ability").text.trim.toInt
        val strength = (card \ "strength").text.trim.toInt
        val range = (card \ "range").text.trim.toInt
        if (!name.equals("0")) rowBuilder.+=(Some(Card(name,ability,strength,range))) else rowBuilder.+=(None)
      }
      vectorBuilder.+=(rowBuilder.result)
    }

    Field(vectorBuilder.result,weather,playerTop,playerBot,turn,round)
  }

  override def save(field: FieldInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("field.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(getXml(field))
    pw.write(xml)
    pw.close()
  }

  def saveXML(field: FieldInterface): Unit = {
    scala.xml.XML.save("grid.xml", getXml(field))
  }

  def getXml(field: FieldInterface) = {
    <game>
        {fieldToXml(field.field)}
      <weather>
        {field.weather.weather.toString}
      </weather>
      <turn>
        {field.turn.toString}
      </turn>
      <round>
        {field.round.toString}
      </round>
      <players>
        {for {
        pl <- 0 to 1
      } yield {
        pl match {
          case 0 => playerToXml(field.playerTop)
          case 1 => playerToXml(field.playerBot)
        }
      }}
      </players>
    </game>
  }

  def cardToXml(card: CardInterface) = {
    <card>
      <name>
        {card.name}
      </name>
      <ability>
        {card.ability.toString}
      </ability>
      <strength>
        {card.range.toString}
      </strength>
      <range>
        {card.range.toString}
      </range>
    </card>
  }

  def playerToXml(player: Player) = {
    <player>
      <name>
        {player.name}
      </name>
      <wins>
        {player.wins.toString}
      </wins>
      <type>
        {player.playerType match {
          case TOP => "TOP"
          case BOT => "BOT"
          }
        }
      </type>
      <hand>
        {
        for {i <- 0 until player.handCard.size} yield {cardToXml(player.handCard.show(i))}
        }
      </hand>
    </player>
  }

  def fieldToXml(field: Vector[Vector[Option[CardInterface]]]) = {
    <field>
      {for {
      row <- 0 until 4
      col <- 0 until 4
    } yield {
      if (field(row)(col).equals(None)) {
        cardToXml(Card("0", 0, 0, 0))
      } else {
        cardToXml(field(row)(col).get)
      }
    }}
    </field>
  }
}
