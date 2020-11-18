package aview

import model.{Evaluation, Field, HandCard, Player}

import scala.util.Random.nextInt

class Tui {

  def processInputLine(input: String, field:Field, playerTop: Player, handTop: HandCard, playerBot: Player, handBot: HandCard, eval:Evaluation):(Field,HandCard,HandCard) = {
    val r = scala.util.Random
    input match {
      case "close" => (field,handTop,handBot)
      case "clear" => eval.eval(field,playerTop,playerBot)
        (field.clear(field),handTop,handBot)
      case "help" => println("Possiblites: close, clear, top/bot for Card display, top pr/bot pr for playing a Random Card")
        (field,handTop,handBot)
      case "top" =>
        println(handTop)
        (field,handTop,handBot)
      case "bot" =>
        println(handBot)
        (field,handTop,handBot)
      case "bot pr" =>
        var colR = r.nextInt(2) + 2
        var rowR = r.nextInt(4)
        var cardR = 0
        if(field.isNotFull(2,4) && !handBot.handIsEmpty) {
          while (field.isEmpty(colR, rowR) == false) {
            colR = r.nextInt(2) + 2
            rowR = r.nextInt(4)
          }
          do {
            cardR = r.nextInt(handBot.hand.size)
          } while (handBot.show(cardR).isEmpty)
          val tuple = handBot.playCard(cardR, field, rowR, colR)
          val handBotNew = tuple._2
          return (field,handTop,handBotNew)
        }
        (field,handTop,handBot)
      case "top pr" =>
        var colR = 0
        var rowR = 0
        var cardR = 0
        if(field.isNotFull(0,2) && !handTop.handIsEmpty) {
          do {
            colR = r.nextInt(2)
            rowR = r.nextInt(4)
          } while (field.get(colR, rowR).isEmpty == false)
          do {
            cardR = r.nextInt(handTop.hand.size)
          } while (handTop.show(cardR).isEmpty)
          val handTopNew = handTop.playCard(cardR, field, rowR, colR)._2
          return (field,handTopNew,handBot)
        }
        (field,handTop,handBot)
      case _ => {
        (field,handTop,handBot)
      }
    }
  }

}