package de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl

import de.htwg.se.gwent.model.cardComponent.CardInterface
import de.htwg.se.gwent.model.fieldComponent.FieldInterface
import scala.math.sqrt

case class Field(field: Vector[Vector[Option[CardInterface]]]) extends FieldInterface{

  val evaluator = Evaluation()
  val size = 4
  val blocknum = sqrt(size).toInt
  def isEmpty(col:Int,row:Int):Boolean = field(col)(row) match {
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
      case 0 => Field(Vector(field(0).updated(col, op),field(1),field(2),field(3)))
      case 1 => Field(Vector(field(0),field(1).updated(col, op),field(2),field(3)))
      case 2 => Field(Vector(field(0),field(1),field(2).updated(col, op),field(3)))
      case 3 => Field(Vector(field(0),field(1),field(2),field(3).updated(col, op)))
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
    Field(Vector[Vector[Option[CardInterface]]](Vector(None,None,None,None),Vector(None,None,None,None),Vector(None,None,None,None),Vector(None,None,None,None)))
  }
}
