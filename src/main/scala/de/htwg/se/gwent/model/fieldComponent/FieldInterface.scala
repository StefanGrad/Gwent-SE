package de.htwg.se.gwent.model.fieldComponent

import de.htwg.se.gwent.model.cardComponent.CardInterface
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.Evaluation

trait FieldInterface {
  def field: Vector[Vector[Option[CardInterface]]]
  def size: Int
  def evaluator: Evaluation
  def isEmpty(col:Int,row:Int): Boolean
  def isNotFull(fromRow:Int, tillRow:Int): Boolean
  def setCard(col:Int, row:Int, op: Option[CardInterface]):FieldInterface
  def getCard(col:Int, row:Int): Option[CardInterface]
  def clear: FieldInterface
}
