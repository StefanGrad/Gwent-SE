package de.htwg.se.gwent.model.cardComponent.cardBaseImpl

import de.htwg.se.gwent.model.cardComponent.CardInterface
import de.htwg.se.gwent.model.fieldComponent.FieldInterface
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.Field

case class HandCard(hand: Vector[CardInterface]) {
  val deck = Deck()
  val size: Int = hand.length


  def newDeck(): HandCard = {
    val newDeck = Vector[CardInterface](deck.getRandomCard, deck.getRandomCard, deck.getRandomCard, deck.getRandomCard, deck.getRandomCard, deck.getRandomCard, deck.getRandomCard, deck.getRandomCard, deck.getRandomCard, deck.getRandomCard)
    HandCard(newDeck)
  }

  def draw(card: CardInterface): HandCard = HandCard(hand ++ Vector[CardInterface](card))

  def drawRandom: HandCard = HandCard(hand ++ Vector[CardInterface](deck.getRandomCard))

  def handIsEmpty: Boolean = 0 == hand.size

  def show(index: Int): CardInterface = hand(index)

  //return wert auswählen z.b. val test = playCard(x,x,x,x,x)._1 für c oder ._2 für newhand
  def playCard(cardAt: Int, field: FieldInterface, inRow: Int, inCol: Int): (CardInterface, HandCard, FieldInterface) = {
    val c = hand(cardAt)
    (c, deleteCard(c), field.setCard(inCol, inRow, Some(c)))
  }

  // Läuft über die hand wenn das Jeweilige i meine card ist, gibt er eine leere Karte zurück und wenn nicht lässt er die Karte drin
  def deleteCard(card: CardInterface): HandCard = {
    val returnIndex = getCardIndex(card)
    var newHand = Vector[CardInterface]()
    if (returnIndex != -1) {
      for (x <- hand.indices) {
        if (x != returnIndex) {
          newHand = newHand ++ Vector[CardInterface](hand(x))
        }
      }
      return HandCard(newHand)
    }
    HandCard(hand)
  }

  def getCardIndex(card: CardInterface): Int = {
    for (x <- hand.indices) {
      if (hand(x) == card) {
        return x
      }
    }
    -1
  }

  override def toString: String = hand.toString().replace("Vector", "").replace("(", "").replace(")", "")
}
