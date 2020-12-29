package de.htwg.se.gwent.model.cardComponent.cardBaseImpl
import de.htwg.se.gwent.model.cardComponent.CardInterface

case class Card(name: String, ability: Int, strength: Int, range: Int) extends CardInterface{
  def isEmpty: Boolean = this.equals(Card("", 0, 0, 0))
  override def toString: String = (name + " A" + ability + " S" + strength + " R" + range)
}
