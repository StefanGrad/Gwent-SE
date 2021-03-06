package de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl

import de.htwg.se.gwent.model.fieldComponent.{CardInterface, FieldInterface}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.{Fog, Frost, State, Sunshine}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherStatus.{FOG, FROST, SUNSHINE}
import de.htwg.se.gwent.model.playerComponent.{Player, PlayerType}
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

import scala.math.sqrt

case class Field (field: Vector[Vector[Option[CardInterface]]],weather: State, playerTop: Player, playerBot: Player, turn: Int, round: Int) extends FieldInterface{
  val evaluator = Evaluation()
  val size = 4
  val blocknum = sqrt(size).toInt

  def nextRound: FieldInterface = Field(field, weather, playerTop, playerBot, 0, round + 1)

  def doTurn: FieldInterface = Field(field, weather, playerTop, playerBot, turn + 1, round)

  def undoTurn: FieldInterface = Field(field, weather, playerTop, playerBot, turn - 1 , round)

  def whoCanPlay: PlayerType.Value = turn % 2 match {
    case 0 => TOP
    case 1 => BOT
  }

  def updateWins(playerType: PlayerType.Value) : FieldInterface = playerType match {
    case TOP => Field(field, weather, playerTop.updateWins(playerTop), playerBot, turn, round)
    case BOT => Field(field, weather, playerTop, playerBot.updateWins(playerBot), turn, round)
  }

  def changeWeather(card: CardInterface): FieldInterface = {
    Field(this.field,weather.changeWeather(card),this.playerTop,this.playerBot,turn, round)
  }

  def changeWeather(weatherStatus: WeatherStatus.Value): FieldInterface = {
    Field(this.field,weather.changeWeather(weatherStatus),this.playerTop,this.playerBot,turn, round)
  }

  def isEmpty(row:Int,col:Int):Boolean = field(col)(row) match {
    case Some(value) => false
    case None => true
  }

  def isNotFull(fromRow:Int, tillRow:Int): Boolean = {
    var notFull = 0
    for {
      row <- fromRow until tillRow
      col <- 0 until field(0).length
    } if (field(row)(col).isEmpty) {notFull += 1}
    notFull > 0
  }
  def setCard(col:Int, row:Int, op: Option[CardInterface]):FieldInterface = {
    row match {
      case 0 => Field(Vector(field(0).updated(col, op),field(1),field(2),field(3)),weather,this.playerTop,this.playerBot,turn, round)
      case 1 => Field(Vector(field(0),field(1).updated(col, op),field(2),field(3)),weather,this.playerTop,this.playerBot,turn, round)
      case 2 => Field(Vector(field(0),field(1),field(2).updated(col, op),field(3)),weather,this.playerTop,this.playerBot,turn, round)
      case 3 => Field(Vector(field(0),field(1),field(2),field(3).updated(col, op)),weather,this.playerTop,this.playerBot,turn, round)
    }
  }

  override def toString: String = {
    val lineseparator = ("+-----" + ("-----" * blocknum)) * blocknum + "+\n"
    val line = ("|" + "x") * size + "|\n"
    var box = "\n" + (lineseparator + (line * blocknum)) * blocknum + lineseparator
    for {
      row <- 0 until size
      col <- 0 until size
    } {
      val s = StringBuilder.newBuilder
      s.append("(").append(row).append(",").append(col).append(")")
      box = box.replaceFirst("x", field(row)(col).toString.replace("    ", s.toString()))
    }
    box
  }

  def getCard(col:Int, row:Int): Option[CardInterface] = field(col)(row)

  def clear: FieldInterface = {
    Field(Vector[Vector[Option[CardInterface]]](Vector(None,None,None,None),Vector(None,None,None,None),Vector(None,None,None,None),Vector(None,None,None,None)),weather.changeWeather(SUNSHINE),this.playerTop,this.playerBot,turn, round)
  }

}
