package aview

import model.{Evaluation, Field, HandCard, Player}

import scala.util.Random.nextInt

class Tui {

  def processInputLine(input: String, field:Field, playerTop: Player, playerBot: Player):(Field,Player,Player) = {
    val r = scala.util.Random
    input match {
      case "close" => (field,playerTop,playerBot)
      case "clear" => field.evaluator.eval(field,playerTop,playerBot)
        (field.clear(field),playerTop,playerBot)
      case "help" => println("Possiblites: close, clear, top/bot for Card display, top pr/bot pr for playing a Random Card")
        (field,playerTop,playerBot)
      case "top" =>
        println(playerTop)
        (field,playerTop,playerBot)
      case "bot" =>
        println(playerBot)
        (field,playerTop,playerBot)
      case "bot pr" =>
        var colR = r.nextInt(2) + 2
        var rowR = r.nextInt(4)
        var cardR = 0
        if(field.isNotFull(2,4) && !playerBot.handCard.handIsEmpty) {
          while (field.isEmpty(colR, rowR) == false) {
            colR = r.nextInt(2) + 2
            rowR = r.nextInt(4)
          }
          do {
            cardR = r.nextInt(playerBot.handCard.hand.size)
          } while (playerBot.handCard.show(cardR).isEmpty)
          val handBotNew = playerBot.handCard.playCard(cardR, field, rowR, colR)._2
          val name = playerBot.name
          val playerBotNew = Player(name,handBotNew)
          return (field,playerTop,playerBotNew)
        }
        (field,playerTop,playerBot)
      case "top pr" =>
        var colR = 0
        var rowR = 0
        var cardR = 0
        if(field.isNotFull(0,2) && !playerTop.handCard.handIsEmpty) {
          do {
            colR = r.nextInt(2)
            rowR = r.nextInt(4)
          } while (field.get(colR, rowR).isEmpty == false)
          do {
            cardR = r.nextInt(playerTop.handCard.hand.size)
          } while (playerTop.handCard.show(cardR).isEmpty)
          val handTopNew = playerTop.handCard.playCard(cardR, field, rowR, colR)._2
          return (field,Player(playerTop.name,handTopNew),playerBot)
        }
        (field,playerTop,playerBot)
      case _ => {
        (field,playerTop,playerBot)
      }
    }
  }

}
