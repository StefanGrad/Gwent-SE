package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.model.PlayerType.{BOT, TOP}
import scala.de.htwg.se.gwent.model.{Player, PlayerType}

object choosePlayer{
  trait Chooser {
    def player(controller: Controller): Player
  }
  class chooseTop extends Chooser {
    override def player(controller: Controller) =  controller.playerTop
  }
  class chooseBot extends Chooser {
    override def player(controller: Controller) = controller.playerBot
  }
  def choice(playerType:PlayerType.Value):Chooser = playerType match {
    case TOP => new chooseTop
    case BOT => new chooseBot
  }
}


