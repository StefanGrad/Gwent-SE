package scala.de.htwg.se.gwent.aview

import de.htwg.se.gwent.controller.CellChanged
import de.htwg.se.gwent.model.playerComponent.PlayerType

import scala.de.htwg.se.gwent.util.Observer
import scala.de.htwg.se.gwent.controller.Controller
import scala.de.htwg.se.gwent.controller.GameStatus.INPUTFAIL
import scala.swing.Reactor

class Tui(controller: Controller) extends Reactor{

  listenTo(controller)

  def processInputLine(input: String, playerType: PlayerType.Value):Unit = {
    input match {
      case "q" =>
      case "c" => controller.passRound()
      case "z" => controller.undo
      case "r" => controller.redo
      case _ => {
        input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: column :: cardIndex :: Nil => controller.playCardAt(controller.field, row, column, playerType, cardIndex)
          case _ => controller.gameState = INPUTFAIL
        }
      }
    }
  }

  reactions += {
    case event: CellChanged => println(controller.field.toString)
  }

}
