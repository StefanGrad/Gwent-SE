package main.model

// bei floobits einmal drin, immer drin
// niemals unter keinen umständen auf gar keinen fall update während man in floobits drinnen ist

case class HandCard(hand: Vector[Card]) {
  val deck = Deck()
  val size: Int = deck.length


  def newDeck(): HandCard = {
    val newDeck = Vector[Card](deck.getRandomCard(),deck.getRandomCard(),deck.getRandomCard(),deck.getRandomCard(),deck.getRandomCard(), deck.getRandomCard(), deck.getRandomCard(),deck.getRandomCard(),deck.getRandomCard(),deck.getRandomCard())
    HandCard(newDeck)
  }

  def draw(card: Card): HandCard = HandCard(hand++Vector[Card](card))

  def drawRandom: HandCard = HandCard(hand++Vector[Card](deck.getRandomCard()))

  def handIsEmpty: Boolean = 0 == hand.size

  def show(index: Int): Card = hand(index)

  //return wert auswählen z.b. val test = playCard(x,x,x,x,x)._1 für c oder ._2 für newhand
  def playCard(cardAt: Int, field: Field, inRow: Int, inCol: Int):(Card, HandCard) ={
    val c = hand(cardAt)
    field.set(inCol, inRow, c)
    (c, deleteCard(c))
  }
  // Läuft über die hand wenn das Jeweilige i meine card ist, gibt er eine leere Karte zurück und wenn nicht lässt er die Karte drin
  def deleteCard(card: Card): HandCard = {
    val returnIndex = getCardIndex(card)
      var newHand = Vector[Card]()
      for (x <- 0 to hand.size-1) {
        if (returnIndex != x) {
          newHand = newHand++Vector[Card](hand(x))
        }
      }
      //return HandCard(hand patch (from = returnIndex, patch = Nil, replaced = 1))
    HandCard(newHand)
  }

  def getCardIndex(card: Card): Int ={
    for (x <- 0 to hand.length){
      if(hand(x) == card){
        return x
      }
    }
    -1
  }
/*  def set(cardAt:Int, card: Card):Vector[Card]= {
    val returnIndex = getCardIndex(card)
    Vector vector = new Vector(Card)
    oldCard
    val newVektor = hand.filterNot(p => card.equals(p))
  }

 def draw: Card = deck(r.nextInt(size))
/al sb = StringBuilder
       sb + playerBot.hand.show(0).toString
       for(x <- 1 to 9) {
         sb + ", " + playerBot.hand.show(x).toString
       }
       println(sb.toString)
 */
 override def toString: String = hand.toString().replace("Vector", "").replace("(", "").replace(")", "")
}
