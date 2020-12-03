package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.controller.Controller
import scala.de.htwg.se.gwent.model.{Card, HandCard, Player}

object choosePlayer{
  trait Chooser {
    def player(controller: Controller): Player
  }
  class chooseTop extends Chooser {
    override def player(controller: Controller) =  {
      controller.playerTop
    }
  }
  class chooseBot extends Chooser {
    override def player(controller: Controller) = controller.playerBot
  }
  def choice(b :Boolean):Chooser = if(b) new chooseTop else new chooseBot
}


