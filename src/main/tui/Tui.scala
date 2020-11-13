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
      case "help" => println("Possiblites: close, clear, eval, top/bot for Card display, top pr/bot pr for playing a Random Card")
        field
      case "top" =>
        println(playerTop.hand)
        field
      case "bot" =>
        println(playerBot.hand.toString)
        field
      case "bot pr" =>
        var colR = r.nextInt(2) + 2
        var rowR = r.nextInt(4)
        var cardR = 0
        if(field.isNotFull(2,4) && !playerBot.hand.handIsEmpty) {
          while (field.isEmpty(colR, rowR) == false) {
            colR = r.nextInt(2) + 2
            rowR = r.nextInt(4)
          }
          do {
            cardR = r.nextInt(10)
          } while (playerBot.hand.show(cardR).isEmpty)
          playerBot.hand.playCard(cardR, field, rowR, colR)
        }
        field
      case "top pr" =>
        var colR = 0
        var rowR = 0
        var cardR = 0
        if(field.isNotFull(0,2) && !playerTop.hand.handIsEmpty) {
          do {
            colR = r.nextInt(2)
            rowR = r.nextInt(4)
          } while (field.get(colR, rowR).isEmpty == false)
          do {
            cardR = r.nextInt(10)
          } while (playerTop.hand.show(cardR).isEmpty)
          playerTop.hand.playCard(cardR, field, rowR, colR)
        }
        field
      case _ => {
        field
      }
    }
  }

}
