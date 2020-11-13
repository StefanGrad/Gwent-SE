
package main.tui

import main.model.{Evaluation, Field, Player}

import scala.util.Random.nextInt

class Tui {

  def processInputLine(input: String, field:Field, playerTop: Player, playerBot: Player, eval:Evaluation):Field = {
    val r = scala.util.Random
    input match {
      case "close" => field
      case "clear" => eval.eval(field,playerTop,playerBot)
        field.clear(field)
      case "help" => println("Possiblites: close, clear, top/bot for Card display, top/bot pr for playing a Random Card")
        field
      case "top" =>
        val sb = StringBuilder
        sb + playerTop.hand.show(0).toString
        for(x <- 1 to 9) {
          sb + ", " + playerTop.hand.show(x).toString
        }
        println(sb.toString)
        field
      case "bot" =>
        val sb = StringBuilder
        sb + playerBot.hand.show(0).toString
        for(x <- 1 to 9) {
          sb + ", " + playerBot.hand.show(x).toString
        }
        println(sb.toString)
        field
      case "bot pr" =>
        var colR = 0
        var rowR = 0
        var cardR = 0
        do {
          colR = r.nextInt(4)
          rowR = r.nextInt(4)
        } while (!field.isEmpty(colR,rowR,field))
        do {
          cardR = r.nextInt(10)
        } while (playerBot.hand.show(cardR).isEmpty)
        playerBot.hand.playCard(cardR,field,rowR,colR)
        field
      case "top pr" =>
        var colR = 0
        var rowR = 0
        var cardR = 0
        do {
          colR = r.nextInt(4)
          rowR = r.nextInt(4)
        } while (!field.isEmpty(colR,rowR,field))
        do {
          cardR = r.nextInt(10)
        } while (playerTop.hand.show(cardR).isEmpty)
        playerTop.hand.playCard(cardR,field,rowR,colR)
        field
      case _ => {
        field
      }
    }
  }

}
