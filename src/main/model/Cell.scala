package main.model
case class Cell(value: Card) {
  def isSet: Boolean = value != Card("",0,0,0)
  def getCard: Card = value;
  override def toString: String = value.toString.replace('0', ' ')
}