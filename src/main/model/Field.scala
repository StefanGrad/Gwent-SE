package main.model
import scala.math.sqrt

case class Field(col:Int, row:Int) {
  val emptyCard = Card("",0,0,0)
  val field = Array.fill(col,row)(emptyCard)
  val size = field.size
  val blocknum: Int = sqrt(size).toInt
  def set(col:Int,row:Int, card:Card) = (field(col)(row) = card)
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
