package main.model

case class Card(name: String, ability: Int, strength: Int, range: Int) {
  def cardSpecs: (String,Int,Int,Int) = (name,ability,strength, range)
  def isEmpty: Boolean = this.toString.equals(Card("",0,0,0))
  override def toString: String = (name + " A" + ability + " S" + strength).replace(" A0 S0", "    ")
}
