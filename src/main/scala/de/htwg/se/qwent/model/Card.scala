package de.htwg.se.qwent.model

case class Card(name: String, ability: Int, strength: Int, range: Int) {
  def isEmpty: Boolean = this.equals(Card("",0,0,0))
  override def toString: String = (name + " A" + ability + " S" + strength + " R" + range).replace(" A0 S0 R0", "    ")
}
