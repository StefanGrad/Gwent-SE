package scala.de.htwg.se.gwent

import scala.de.htwg.se.gwent.aview.Tui
import scala.de.htwg.se.gwent.model.{Card, Field, HandCard, Player}
import scala.de.htwg.se.gwent.controller.Controller
import scala.de.htwg.se.gwent.controller.GameStatus.INPUTFAIL
import scala.de.htwg.se.gwent.model.PlayerType.{TOP,BOT}
import scala.io.StdIn.readLine

object Gwent{
  val playerTop = Player(TOP,"Adrian",HandCard(Vector[Card]()).newDeck(),0)
  val playerBot = Player(BOT,"Stefan",HandCard(Vector[Card]()).newDeck(),0)
  val field = new Field(Vector[Vector[Option[Card]]]())
  val controller = new Controller(field.clear,playerTop,playerBot)
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    println("Welcome to Gwent")
    var input: String = ""
    var turnFor = 0
    if(args.length != 0) {
      val input2 = args(0).split('_')
      for (i <- 0 until input2.length) {
        if(turnFor % 2 == 0) {
          tui.processInputLine(input2(i),TOP)
          turnFor += 1
        }
        tui.processInputLine(input2(i),BOT)
        turnFor += 1
      }

    }
    else do {
      if(turnFor % 2 == 0) {
        println(controller.playerTop)
        println("May chose his options (q,c,(row,col,cardAt))")
        input = readLine()//scala.io.StdIn.readLine()//
        tui.processInputLine(input,TOP)
      } else {
        println(controller.playerBot)
        println("May chose his options (q,c,(row,col,cardAt))")
        input = readLine()//scala.io.StdIn.readLine()//
        tui.processInputLine(input,BOT)
      }
      turnFor += 1
      if (controller.gameState.equals(INPUTFAIL)) {
        println("Your input was incorrect please try again. If you want to pass press 'c'")
        turnFor -= 1
      }
    } while (input != "q")
    println("See you next time")
  }
}
