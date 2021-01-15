package de.htwg.se.gwent.model.fileIOComponent.FileIOXmlImpl

import com.google.inject.Guice
import de.htwg.se.gwent.GwentModule
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.{Fog, Frost, Sunshine}
import de.htwg.se.gwent.model.fieldComponent.{CardInterface, FieldInterface}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Field, HandCard}
import de.htwg.se.gwent.model.fileIOComponent.FileIOInterface
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

import scala.xml.{NodeSeq, PrettyPrinter}
import net.codingwell.scalaguice.InjectorExtensions._

import scala.collection.immutable.VectorBuilder
import scala.collection.mutable.ListBuffer

class FileIO extends FileIOInterface {


  override def load: FieldInterface = {
    val file = scala.xml.XML.loadFile("field.xml")
    val nPlayer = (file \\ "player")
    var playerTop = Player(TOP,"",HandCard(Vector[CardInterface]()),0)
    var playerBot = Player(TOP,"",HandCard(Vector[CardInterface]()),0)
    nPlayer.foreach{player =>
      val name = (player \ "@name").text.toString
      val nHand = (player \ "@hand")
      val hand = new ListBuffer[CardInterface]
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
    val firstRow = rowFiller(nVecArr,0)
    val secondRow = rowFiller(nVecArr,4)
    val thirdRow = rowFiller(nVecArr,8)
    val fourthRow = rowFiller(nVecArr,12)

    Field(Vector(firstRow,secondRow,thirdRow,fourthRow),weather,playerTop,playerBot,turn,round)
  }

  def rowFiller(field: NodeSeq, start: Int): Vector[Option[CardInterface]] = {
    var s = start
    val end = start + 4
    val list = new ListBuffer[Option[CardInterface]]
    while (s < end){
      if((field(s) \ "@name").text.equals("0")) {
        list.append(None)
        s += 1
      } else {
        list.append(Some(Card((field(s) \ "@name").text.toString,(field(s)\"@ability").text.toInt,(field(s) \ "@strength").text.toInt,(field(s) \ "@range").text.toInt)))
        s += 1
      }
    }
    list.toVector
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
    <fields field={fieldToXml(field.field)} weather={field.weather.weather.toString} turn={field.turn.toString} round={field.round.toString}>
      <players>
        {
          playerToXml(field.playerTop)
          playerToXml(field.playerBot)
        }
      </players>
    </fields>
  }

  def cardToXml(card: CardInterface) = {
    <card name={card.name} ability={card.ability.toString} strength={card.range.toString} range={card.range.toString}>
    </card>
  }

  def playerToXml(player: Player) = {
    <player name={player.name} wins={player.wins.toString}
            type={player.playerType match {
              case TOP => "TOP"
              case BOT => "BOT"
            }}
            hand={for {i <- 0 until player.handCard.size}cardToXml(player.handCard.show(i))
            }></player>
  }

  def fieldToXml(field: Vector[Vector[Option[CardInterface]]]) = {
    <field>
      {for {
      row <- 0 until 4
      col <- 0 until 4
    } {
      if (field(row)(col).equals(None)) {
        cardToXml(Card("0", 0, 0, 0))
      } else {
        cardToXml(field(row)(col).get)
      }
    }}
    </field>
  }
}
