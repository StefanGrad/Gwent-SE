package main.model

case class HandCard() {
  val deck = List(new Card("Archer", 0, 3, 1),
    Card ("Berserker", 0, 4, 0),
    Card ("Einschüchterer", 0 , 4, 0),
    Card ("Infanterie", 1 , 1, 0),
    Card ("Brigade", 0 , 1, 0),
    Card ("Junger Gesandter", 1, 5, 0),
    Card ("Fußsoldat", 0, 1, 0),
    Card ("Zwergen-Scharmützler", 2, 3, 0),
    Card ("Bogenschütze", 0, 2, 1),
    Card ("Heiler", 2, 0, 1),
    Card ("Scharfschütze", 1, 2, 1),
    Card ("Steinwerfer", 0, 1, 1),
    Card ("Kanonier", 0, 5, 1),
    Card ("Kreuzbogenschütze", 0, 4, 1),
    Card ("Speerdude", 1, 3, 0),
    Card ("Reiter", 0, 4, 0),
    Card ("Pikenier", 1, 2, 0),
    Card ("Musketier", 1, 3, 1),
    Card ("Hobbit", 2, 0, 0))
  val hand = new scala.collection.mutable.ArrayBuffer[Card]()
  val size: Int = deck.length
  val length: Int = hand.size
  val i = 0
  val r = scala.util.Random
  for( x <- 0 to 9){
    hand += deck(r.nextInt(size))
  }
  def show(index: Int): Card = hand(index)
  def playCard(cardAt: Int, field: Field, inRow: Int, inCol: Int): Unit ={
    field.set(inCol, inRow, hand(cardAt))
    hand.update(cardAt, Card("",0,0,0))
  }
  def set(i:Int, card: Card) = hand(i) = card
  def draw: Card = deck(r.nextInt(size))

  override def toString: String = hand.toString().replace("ArrayBuffer", "")
}
