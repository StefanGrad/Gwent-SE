package de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.controller.Controller
import scala.de.htwg.se.gwent.util.Command

class PassCommand(controller: Controller) extends Command{
  val prevGameState = controller.gameState
  override def doStep: Unit = ???

  override def undoStep: Unit = ???

  override def redoStep: Unit = ???
}
