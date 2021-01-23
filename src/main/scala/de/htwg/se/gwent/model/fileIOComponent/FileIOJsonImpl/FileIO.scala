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
import scala.io.Source
import scala.collection.mutable.ListBuffer

class FileIO extends FileIOInterface {

  override def load: FieldInterface = {
    val source: String = Source.fromFile("field.json").getLines.mkString
    val json: JsValue = Json.parse(source)

    val nPlayer = (json \\ "players")
    var playerTop = Player(TOP,"",HandCard(Vector[CardInterface]()),0)
    var playerBot = Player(TOP,"",HandCard(Vector[CardInterface]()),0)

    for(player <- nPlayer) {
      for(y <- 0 to 1){
        val name = (player(y) \ "name").get.toString
        val nHand = (player(y) \\ "hand")
        val hand = new ListBuffer[CardInterface]
        val size = (player(y) \ "handsize").get.toString.toInt
        for (n <- nHand){
          for(x <- 0 until size){
            hand.append(Card((n(x) \ "name").get.toString,(n(x)\"ability").get.toString.toInt,(n(x) \ "strength").get.toString.toInt,(n(x) \ "range").get.toString.toInt))}
        }
        val wins = (player(y) \ "wins").get.toString.toInt
        val nType = (player(y) \ "type").get.toString
        y match {
          case 0 => playerTop = Player(TOP,name,HandCard(hand.toVector),wins)
          case 1 => playerBot = Player(BOT,name,HandCard(hand.toVector),wins)
        }
      }
    }
    val turn = (json \ "turn").get.toString.toInt
    val round = (json \ "round").get.toString.toInt
    val fWeather = (json \ "weather").get.toString.toInt
    val weather = fWeather match {
      case 2 => new Frost
      case 0  => new Sunshine
      case 1 => new Fog
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
    for (n <- field) {
      for (x <- start until end) {
        if((n(x)\"ability").get.toString.toInt == 0 && (n(x) \ "strength").get.toString.toInt == 0 && (n(x) \ "range").get.toString.toInt == 0) {
          list.append(None)
        } else {
          list.append(Some(Card((n(x) \ "name").get.toString,(n(x)\"ability").get.toString.toInt,(n(x) \ "strength").get.toString.toInt,(n(x) \ "range").get.toString.toInt)))
        }
      }
    }
    list.toVector
  }

  override def save(field: FieldInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("field.json"))
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
      "handsize" -> player.handCard.hand.length,
      "hands" -> Json.toJson(player.handCard)

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
        "weather" -> Json.toJson(field.weather.weather.toString match {
          case "FROST" => JsNumber(2)
          case "SUNSHINE"  => JsNumber(0)
          case "FOG" => JsNumber(1)
        }),
        "players" -> Json.toJson(for{
          p <- 0 to 1
        } yield {Json.toJson(p match {
            case 0 => field.playerTop
            case 1 => field.playerBot
          })
        }),
        "turn" -> JsNumber(field.turn),
        "round" -> JsNumber(field.round),
        "fields" -> Json.toJson(field.field)
    )
  }

}

