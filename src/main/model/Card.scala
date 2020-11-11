package main.model

case class Card(name: String, ability: Int, strength: Int, range: Int) {
  def cardSpecs: (String,Int,Int,Int) = (name,ability,strength, range)
  override def toString: String = name + ability + strength + range
}