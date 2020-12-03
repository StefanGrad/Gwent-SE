package scala.de.htwg.se.gwent.model

case class Player(name:String, handCard: HandCard, wins: Int) {
  def updateWins(p: Player): Player = Player(p.name,p.handCard,p.wins + 1)
  override def toString: String = name + " has won " + wins + " times and holds in his Hand: " + handCard
}
/*
trait Player{
  val name = "Name"
  val handCard = HandCard(Vector[Card]())
  val wins = 0
  def updateWins(p: Player): Either[PlayerTop,PlayerBot]
}
private case class PlayerTop(override val name:String, override val handCard: HandCard, override val wins: Int) extends Player {
  override def updateWins(p: Player): Either[PlayerTop,PlayerBot] = PlayerTop(this.name,this.handCard,this.wins + 1)
  override def toString: String = name + " has won " + wins + " times and holds in his Hand: " + handCard
}
private case class PlayerBot(override val name:String, override val handCard: HandCard, override val wins: Int) extends Player {
  def updateWins(p: Player): Either[PlayerTop,PlayerBot] = PlayerBot(this.name,this.handCard,this.wins + 1)
  override def toString: String = name + " has won " + wins + " times and holds in his Hand: " + handCard
}
object Player {
  def apply(kind:Int,name:String,handCard: HandCard, wins: Int) = kind match {
    case 0 => new PlayerTop(name,handCard,wins)
    case 1 => new PlayerBot(name,handCard,wins)
  }
}
*/