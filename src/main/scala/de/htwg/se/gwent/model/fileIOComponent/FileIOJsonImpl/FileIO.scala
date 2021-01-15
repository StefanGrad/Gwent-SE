package de.htwg.se.gwent.model.fileIOComponent.FileIOJsonImpl

import com.google.inject.Guice
import de.htwg.se.gwent.GwentModule
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.{Fog, Frost, Sunshine}
import de.htwg.se.gwent.model.fieldComponent.{CardInterface, FieldInterface}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Field, HandCard}
import de.htwg.se.gwent.model.fileIOComponent.FileIOInterface
import de.htwg.se.gwent.model.playerComponent.Player
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import play.api.libs.json._
import scala.xml.{NodeSeq, PrettyPrinter}
import net.codingwell.scalaguice.InjectorExtensions._
import scala.io.Source
import scala.collection.immutable.VectorBuilder
import scala.collection.mutable.ListBuffer

class FileIO extends FileIOInterface {


  override def load: FieldInterface = {
    var field: FieldInterface = null
    val source: String = Source.fromFile("field.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val injector = Guice.createInjector(new GwentModule)

    val nPlayer = (json \\ "player")
    var playerTop = Player(TOP,"",HandCard(Vector[CardInterface]()),0)
    var playerBot = Player(TOP,"",HandCard(Vector[CardInterface]()),0)
    for(player <- nPlayer) {
      for(y <- 0 to 1){
        val name = (player(y) \ "@name").get.toString
        val nHand = (player(y) \\ "hand")
        val hand = new ListBuffer[CardInterface]
        for (n <- nHand){
          for(x <- 0 until (player(y) \ "handsize").get.toString.toInt){
            hand.append(Card((n(x) \ "@name").get.toString,(n(x)\"@ability").get.toString.toInt,(n(x) \ "@strength").get.toString.toInt,(n(x) \ "@range").get.toString.toInt))}
          val wins = (player(y) \ "@wins").get.toString.toInt
          val nType = (player(y) \ "@type").get.toString
          nType match {
            case "TOP" => playerTop = Player(TOP,name,HandCard(hand.toVector),wins)
            case "BOT" => playerBot = Player(BOT,name,HandCard(hand.toVector),wins)
          }
        }
      }
    }
    val turn = (json \ "@turn").get.toString.toInt
    val round = (json \ "@round").get.toString.toInt
    val fWeather = (json \ "@weather").get.toString
    val weather = fWeather match {
      case "FROST" => new Frost
      case "SUNSHINE"  => new Sunshine
      case "FOG" => new Fog
    }
    val nVecArr = (json \\ "field")
    val firstRow = rowFiller(nVecArr,0)
    val secondRow = rowFiller(nVecArr,4)
    val thirdRow = rowFiller(nVecArr,8)
    val fourthRow = rowFiller(nVecArr,12)

    Field(Vector(firstRow,secondRow,thirdRow,fourthRow),weather,playerTop,playerBot,turn,round)
  }

  def rowFiller(field: scala.collection.Seq[JsValue], start: Int): Vector[Option[CardInterface]] = {
    var s = start
    val end = start + 4
    val list = new ListBuffer[Option[CardInterface]]
    while (s < end){
      if((field(s) \ "@name").get.toString.equals("0")) {
        list.append(None)
        s += 1
      } else {
        list.append(Some(Card((field(s) \ "@name").get.toString,(field(s)\"@ability").get.toString.toInt,(field(s) \ "@strength").get.toString.toInt,(field(s) \ "@range").get.toString.toInt)))
        s += 1
      }
    }
    list.toVector
  }

  override def save(field: FieldInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("grid.json"))
    pw.write(Json.prettyPrint(fieldToJson(field)))
    pw.close
  }

  implicit val cardWrites = new Writes[CardInterface] {
    def writes(card: CardInterface) = Json.obj(
      "name" -> card.name,
      "ability" -> card.ability,
      "strength" -> card.strength,
      "range" -> card.range
    )
  }

  implicit val handCardWrites = new Writes[HandCard] {
    def writes(handCard: HandCard) = Json.obj(
      "handsize" -> handCard.hand.length,
      "hand"-> Json.toJson(
        for {
          index <- 0 until handCard.hand.length
        } yield {
          Json.toJson(handCard.show(index))
        }
      )
    )
  }

  implicit val playerWrites = new Writes[Player] {
    def writes(player: Player) = Json.obj(
      "name" -> player.name,
      "wins" -> player.wins,
      "type" -> player.playerType,
      "hand" -> Json.toJson(player.handCard)

    )
  }

  implicit val fieldWrites = new Writes[Vector[Vector[Option[CardInterface]]]] {
    def writes(field: Vector[Vector[Option[CardInterface]]]) = Json.obj(
      "field" -> Json.toJson(
        for {
          row <- 0 until 4
          col <- 0 until 4
        } yield {
          if (field(row)(col).equals(None)) {
            Json.toJson(Card("0", 0, 0, 0))
          } else {
            Json.toJson(field(row)(col).get)
          }
        }
      )
    )
  }

  def fieldToJson(field: FieldInterface) = {
    Json.obj(
      "fields" -> Json.obj(
        "weather" -> field.weather.weather.toString,
        "players" -> Json.toJson(for{
          p <- 0 to 1
        } yield {
          "player" -> Json.toJson(p match {
            case 0 => field.playerTop
            case 1 => field.playerBot
          })
        }),
        "turn" -> JsNumber(field.turn),
        "round" -> JsNumber(field.round),
        "field" -> Json.toJson(field.field)
      )
    )
  }

}

