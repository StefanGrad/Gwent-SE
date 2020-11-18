package main.model

// bei floobits einmal drin, immer drin
// niemals unter keinen umständen auf gar keinen fall update während man in floobits drinnen ist

case class HandCard() {
  val deck = Deck()
  val hand = newDeck()
  val size: Int = deck.length
  val i = 0


  def newDeck(): Vector[Card] = {
    new Vector[Card](deck.getRandomCard(),deck.getRandomCard(),deck.getRandomCard(),deck.getRandomCard(),deck.getRandomCard(),
      deck.getRandomCard(), deck.getRandomCard(),deck.getRandomCard(),deck.getRandomCard(),deck.getRandomCard())

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

  //return wert auswählen z.b. val test = playCard(x,x,x,x,x)._1 für c oder ._2 für newhand
  def playCard(cardAt: Int, field: Field, inRow: Int, inCol: Int):(Card, Vector[Card]) ={
    val c = hand(cardAt)
    field.set(inCol, inRow, c)
    val newhand = deleteCard(c)
    (c, newhand)
  }
  // Läuft über die hand wenn das Jeweilige i meine card ist, gibt er eine leere Karte zurück und wenn nicht lässt er die Karte drin
  def deleteCard(card: Card): Vector[Card] = {
    val returnIndex = getCardIndex(card)
    hand patch (from = returnIndex, patch = Nil, replaced = 1)
  }

  def getCardIndex(card: Card): Int ={
    for (x <- 0 to hand.length){
      if(hand(x) == card){
        return x
      }
    }
    -1
  }
  def set(cardAt:Int, card: Card):Vector[Card]= {
    val returnIndex = getCardIndex(Card)
    Vector vector = new Vector(Card)
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
