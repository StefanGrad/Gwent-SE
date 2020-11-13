package main.model
import scala.math.sqrt

case class Field(col:Int, row:Int) {
  val emptyCard = Card("",0,0,0)
  val field = Array.fill(col,row)(emptyCard)
  val size = field.size
  val blocknum: Int = sqrt(size).toInt
  def isEmpty(col:Int,row:Int,field: Field):Boolean = field.get(col, row).toString.equals(Card("",0,0,0))
  def set(col:Int,row:Int, card:Card):Card = {
    val oldCard = field(col)(row)
    field(col)(row) = card
    oldCard
  }
  override def toString: String = {
    val lineseparator = ("+-----" + ("-----" * blocknum)) * blocknum + "+\n"
    val line = ("|" + "x") * row + "|\n"
    var box = "\n" + (lineseparator + (line * blocknum)) * blocknum + lineseparator
    for {
      row <- 0 until row
      col <- 0 until col
    } box = box.replaceFirst("x", field(row)(col).toString)
    box
  }
  def get(col:Int,row:Int): Card = field(col)(row)
  def clear(field: Field): Field = {
    for {
      row <- 0 until row
      col <- 0 until col
    } field.set(col,row,Card("",0,0,0))
    field
  }
}
