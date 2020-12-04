package scala.de.htwg.se.gwent.model

import scala.de.htwg.se.gwent.model.PlayerType.{BOT, TOP}

object PlayerType extends Enumeration {
  type player = Value
  val TOP,BOT = Value
}
object PlayerArea {
  def handel(t: PlayerType.Value) = {
    t match {
      case TOP => Vector[Int](0,1)
      case BOT => Vector[Int](3,2)
    }
  }
}
trait Player{
  val name = "Name"
  val handCard = HandCard(Vector[Card]())
  val wins = 0
  val playerType = TOP
  val playerArea = Vector[Int]()
  def updateWins(p: Player): Player = Player.apply(playerType,name,handCard,wins +1)
  override def toString: String = name + " has won " + wins + " times and holds in his Hand: " + handCard
}
private case class PlayerTop(override val name:String, override val handCard: HandCard, override val wins: Int, override val playerType: PlayerType.Value, override val playerArea: Vector[Int]) extends Player {
  //override def updateWins(p: Player): PlayerTop = PlayerTop(p.name,p.handCard,p.wins + 1,playerType,playerArea)
}
private case class PlayerBot(override val name:String, override val handCard: HandCard, override val wins: Int,override val playerType: PlayerType.Value, override val playerArea: Vector[Int]) extends Player {
  //override def updateWins(p: Player): PlayerBot = PlayerBot(p.name,p.handCard,p.wins + 1,playerType,playerArea)
}
object Player {
  def apply(kind:PlayerType.Value,name:String,handCard: HandCard, wins: Int): Player = kind match {
    case TOP => new PlayerTop(name,handCard,wins,TOP, PlayerArea.handel(TOP))
    case BOT => new PlayerBot(name,handCard,wins,BOT, PlayerArea.handel(BOT))
  }
}

/*
case class Player(name:String, handCard: HandCard, wins: Int, whichPlayer:Boolean) {
  val playArea = PlayerArea.handel(whichPlayer)
  def updateWins(p: Player): Player = Player(p.name,p.handCard,p.wins + 1,p.whichPlayer)
  override def toString: String = name + " has won " + wins + " times and holds in his Hand: " + handCard
}*/