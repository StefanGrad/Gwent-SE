package de.htwg.se.gwent.model.playerComponent

import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

import scala.de.htwg.se.gwent.controller.Controller

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
