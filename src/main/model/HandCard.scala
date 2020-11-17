package main.model

import model.Deck

case class HandCard(hand: List[Card]) {

  val size: Int = Deck().length
  val i = 0
  val r = scala.util.Random

  def draw(handCard: HandCard): HandCard = {
    var temp = handCard.hand
    temp = handCard.hand.++(List((Deck ().deck (r.nextInt (size) ))))
    HandCard(temp)
  }

  def drawTen: List[Card] = {
    var list = List[Card] ()
      for (x <- 0 to 9) {
        list = list.++ (List (Deck ().deck (r.nextInt (size) ) ) )
      }
    list
  }

  def this() {
    this(drawTen)
  }

  def handIsEmpty: Boolean = {
    /*var notEmpty = 0
    for (x <- 0 to hand.size) {
      if (hand(x).isEmpty) {
        notEmpty += 1
      }
    }
    notEmpty == 10*/
    hand.size == 0
  }

  def show(index: Int): Card = hand(index)

  def playCard(cardAt: Int, field: Field, inRow: Int, inCol: Int):HandCard = {
    field.set(inCol, inRow, hand(cardAt))
    var temp = List[Card]()
    for (x <- 0 to hand.size) {
      if (x != cardAt) {
        temp = temp.++(hand.take(x))
      }
    }
    HandCard(temp)
  }
/*
  def set(i:Int, card: Card):Card = {
    val oldCard = hand(i)
    hand.update(i,card)
    oldCard
  }

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
