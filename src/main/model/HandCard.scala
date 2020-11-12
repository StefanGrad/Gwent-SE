package main.model

case class HandCard() {
  val deck = List(new Card("Archer", 0, 3, 1))
  val hand:Vector[Card] = Vector()
  val size: Int = deck.length
  val r = scala.util.Random
  while(hand.size < 10) {
    hand :+ deck(r.nextInt(size))
  }
  def show(index: Int): Card = hand(index)
  //def playCard(cardAt: Int)
  def draw: Card = deck(r.nextInt(size))
}