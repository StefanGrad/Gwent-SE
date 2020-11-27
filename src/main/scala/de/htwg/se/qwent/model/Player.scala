package de.htwg.se.qwent.model


case class Player(name:String, handCard: HandCard, wins: Int) {
  def updateWins(p: Player): Player = Player(p.name,p.handCard,p.wins + 1)
  override def toString: String = name + " has won " + wins + " times and holds in his Hand: " + handCard
}
