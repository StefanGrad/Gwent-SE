package model


case class Player(name:String, handCard: HandCard) {
  override def toString: String = name + " holds in his Hand: " + handCard
}
