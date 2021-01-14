package de.htwg.se.gwent.model.playerComponent

import de.htwg.se.gwent.model.fieldComponent.CardInterface
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.HandCard
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

object PlayerType extends Enumeration {
  type player = Value
  val TOP,BOT = Value
}
object PlayerArea {
  def handel(t: PlayerType.Value) = {
    t match {
      case TOP => Vector[Int](1,0)
      case BOT => Vector[Int](2,3)
    }
  }
}
trait Player{
  val name = "Name"
  val handCard = HandCard(Vector[CardInterface]())
  val wins = 0
  val playerType = TOP
  val playerArea = Vector[Int]()
  def updateWins(p: Player): Player = Player.apply(playerType,name,handCard,wins +1)
  override def toString: String = name + " has won " + wins + " times and holds in his Hand: " + handCard
}
private case class PlayerTop(override val name:String, override val handCard: HandCard, override val wins: Int, override val playerType: PlayerType.Value, override val playerArea: Vector[Int]) extends Player {
}
private case class PlayerBot(override val name:String, override val handCard: HandCard, override val wins: Int,override val playerType: PlayerType.Value, override val playerArea: Vector[Int]) extends Player {
}
object Player {
  def apply(kind:PlayerType.Value,name:String,handCard: HandCard, wins: Int): Player = kind match {
    case TOP => new PlayerTop(name,handCard,wins,TOP, PlayerArea.handel(TOP))
    case BOT => new PlayerBot(name,handCard,wins,BOT, PlayerArea.handel(BOT))
  }
}