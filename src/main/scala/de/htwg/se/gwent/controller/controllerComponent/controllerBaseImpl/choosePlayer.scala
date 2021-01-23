package de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.gwent.controller.controllerComponent.ControllerInterface
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import de.htwg.se.gwent.model.playerComponent.{Player, PlayerType}

object choosePlayer{
  trait Chooser {
    def player(controller: ControllerInterface): Player
  }
  class chooseTop extends Chooser {
    override def player(controller: ControllerInterface) =  controller.field.playerTop
  }
  class chooseBot extends Chooser {
    override def player(controller: ControllerInterface) = controller.field.playerBot
  }
  def choice(playerType:PlayerType.Value):Chooser = playerType match {
    case TOP => new chooseTop
    case BOT => new chooseBot
  }
}
