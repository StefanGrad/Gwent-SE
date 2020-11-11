package main.model

case class Deck() {
  val deck:Vector[Card] = Vector(new Card("Archer", 0, 3))
  def get(index:Int): Card = deck(index)
}