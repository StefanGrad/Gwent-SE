package scala.de.htwg.se.gwent.aview

import de.htwg.se.gwent.controller.controllerComponent.{GameChange, ControllerInterface}
import de.htwg.se.gwent.model.playerComponent.PlayerType
import de.htwg.se.gwent.controller.controllerComponent.GameStatus.{INPUTFAIL, PLAYING}

import scala.swing.Reactor

class Tui(controller: ControllerInterface) extends Reactor{

  listenTo(controller)

  def processInputLine(input: String, playerType: PlayerType.Value):Unit = {
    input match {
      case "q" => controller.changeGameStatus(PLAYING)
      case "c" => controller.passRound()
      case "z" => controller.undo
      case "r" => controller.redo
      case _ => {
        input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: column :: cardIndex :: Nil => controller.playCardAt(row, column, playerType, cardIndex)
          case _ => controller.changeGameStatus(INPUTFAIL)
        }
      }
    }
  }

  reactions += {
    case event: GameChange => println(controller.field.toString)
  }

}
