package main.model

case class Cell(card: Card) {
  def isSet: Boolean = false == (card.name == "")

  override def toString: String = card.toString.replace('0', ' ')
}