package de.htwg.se.gwent.model.fieldComponent

import de.htwg.se.gwent.model.cardComponent.CardInterface
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.Evaluation
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.State
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherStatus.WeatherState

trait FieldInterface {
  def field: Vector[Vector[Option[CardInterface]]]
  def weather: State
  def size: Int
  def evaluator: Evaluation
  def changeWeather(card: CardInterface): State
  def isEmpty(col:Int,row:Int): Boolean
  def isNotFull(fromRow:Int, tillRow:Int): Boolean
  def setCard(col:Int, row:Int, op: Option[CardInterface]):FieldInterface
  def getCard(col:Int, row:Int): Option[CardInterface]
  def clear: FieldInterface
}
