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
  val i = 0
  val r = scala.util.Random

  for( x <- 0 to 9){
    hand += deck(r.nextInt(size))
  }

  def handIsEmpty: Boolean = {
    var notEmpty = 0
    for (x <- 0 to 9) {
      if (hand(x).isEmpty) {
        notEmpty += 1
      }
    }
    notEmpty == 10
  }

  def show(index: Int): Card = hand(index)

  def playCard(cardAt: Int, field: Field, inRow: Int, inCol: Int):Card ={
    val c = hand(cardAt)
    field.set(inCol, inRow, c)
    hand.update(cardAt, Card("",0,0,0))
    c
  }

  def set(i:Int, card: Card):Card = {
    val oldCard = hand(i)
    hand.update(i,card)
    oldCard
  }
/*
 def draw: Card = deck(r.nextInt(size))
/al sb = StringBuilder
       sb + playerBot.hand.show(0).toString
       for(x <- 1 to 9) {
         sb + ", " + playerBot.hand.show(x).toString
       }
       println(sb.toString)
 */
 override def toString: String = hand.toString().replace("ArrayBuffer", "").replace("(", "").replace(")", "")
}
