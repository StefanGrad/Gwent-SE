package scala.de.htwg.se.gwent.model

import scala.collection.immutable.Nil.patch

// bei floobits einmal drin, immer drin
// niemals unter keinen umständen auf gar keinen fall update während man in floobits drinnen ist

case class HandCard(hand: Vector[Card]) {
  val deck = Deck()
  val size: Int = deck.length


  def newDeck(): HandCard = {
    val newDeck = Vector[Card](deck.getRandomCard,deck.getRandomCard,deck.getRandomCard,deck.getRandomCard,deck.getRandomCard, deck.getRandomCard, deck.getRandomCard,deck.getRandomCard,deck.getRandomCard,deck.getRandomCard)
    HandCard(newDeck)
  }

  def draw(card: Card): HandCard = HandCard(hand++Vector[Card](card))

  def drawRandom: HandCard = HandCard(hand++Vector[Card](deck.getRandomCard))

  def handIsEmpty: Boolean = 0 == hand.size

  def show(index: Int): Card = hand(index)

  //return wert auswählen z.b. val test = playCard(x,x,x,x,x)._1 für c oder ._2 für newhand
  def playCard(cardAt: Int, field: Field, inRow: Int, inCol: Int):(Card, HandCard, Field) ={
    val c = hand(cardAt)
    field.set(inCol, inRow, c)
    (c, deleteCard(c),field)
  }
  // Läuft über die hand wenn das Jeweilige i meine card ist, gibt er eine leere Karte zurück und wenn nicht lässt er die Karte drin
  def deleteCard(card: Card): HandCard = {
    val returnIndex = getCardIndex(card)
    var newHand = Vector[Card]()
    if (returnIndex != -1) {
      for (x <- hand.indices) {
        if (x != returnIndex) {
          newHand = newHand ++ Vector[Card](hand(x))
        }
      }
      return HandCard(newHand)
    }
    HandCard(hand)
  }

  def getCardIndex(card: Card): Int ={
    for (x <- hand.indices){
      if(hand(x) == card){
        return x
      }
    }
    -1
  }

 override def toString: String = hand.toString().replace("Vector", "").replace("(", "").replace(")", "")
}
