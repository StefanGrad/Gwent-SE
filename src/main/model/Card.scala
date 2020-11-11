package main.model

case class Card(name: String, ability: Int, strength: Int) {
  def cardSpecs: (String,Int,Int) = (name,ability,strength)
  override def toString: String = name + ability + strength
}