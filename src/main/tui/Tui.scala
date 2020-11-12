
package main.tui

import main.model.{Evaluation, Field, Player}

import scala.util.Random.nextInt

class Tui {

  def processInputLine(input: String, field:Field, playerTop: Player, playerBot: Player, eval:Evaluation):Field = {
    input match {
      case "close" => field
      case "clear" => eval.eval(field,playerTop,playerBot)
        field.clear(field)
      case "help" => println("Possiblites: close, clear, top + Cardindex + Col + Row, bot + Cardindex + Col + Row")
        field
      case "top" => playerTop.hand.playCard(nextInt(),field,nextInt(),nextInt())
        field
      case "bot" => playerBot.hand.playCard(nextInt(),field,nextInt(),nextInt())
        field
      case _ => {
        field
      }
    }
  }

}
