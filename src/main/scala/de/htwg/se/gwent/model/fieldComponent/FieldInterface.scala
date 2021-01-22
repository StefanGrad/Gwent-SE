package de.htwg.se.gwent.model.fieldComponent

import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Evaluation, WeatherStatus}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.State
import de.htwg.se.gwent.model.playerComponent.{Player, PlayerType}

trait FieldInterface {
  def field: Vector[Vector[Option[CardInterface]]]
  def weather: State
  def playerTop: Player
  def playerBot: Player
  def turn: Int
  def round: Int
  def size: Int
  def evaluator: Evaluation
  def whoCanPlay: PlayerType.Value
  def nextRound: FieldInterface
  def doTurn: FieldInterface
  def undoTurn: FieldInterface
  def updateWins(playerType: PlayerType.Value) : FieldInterface
  def changeWeather(card: CardInterface): FieldInterface
  def changeWeather(weatherStatus: WeatherStatus.Value): FieldInterface
  def isEmpty(col:Int,row:Int): Boolean
  def isNotFull(fromRow:Int, tillRow:Int): Boolean
  def setCard(col:Int, row:Int, op: Option[CardInterface]):FieldInterface
  def getCard(col:Int, row:Int): Option[CardInterface]
  def clear: FieldInterface
}
