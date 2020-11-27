package scala.de.htwg.se.gwent

import scala.de.htwg.se.gwent.aview.Tui
import scala.de.htwg.se.gwent.model.{Card, Field, HandCard, Player}
import scala.de.htwg.se.gwent.controller.Controller
import scala.io.StdIn.readLine

object Gwent{
  val playerTop = Player("Adrian",HandCard(Vector[Card]()).newDeck(),0)
  val playerBot = Player("Stefan",HandCard(Vector[Card]()).newDeck(),0)
  val controller = new Controller(new Field(4,4),playerTop,playerBot)
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input: String = ""
    var turnFor = 0

    do {
      if(turnFor % 2 == 0) {
        println(controller.playerTop)
        println("May chose his options (q,c,(row,col,cardAt))")
        input = readLine()//scala.io.StdIn.readLine()//
        tui.processInputLineTop(input)
      } else {
        println(controller.playerBot)
        println("May chose his options (q,c,(row,col,cardAt))")
        input = readLine()//scala.io.StdIn.readLine()//
        tui.processInputLineBot(input)
      }
      turnFor += 1
      if (tui.failedInput) {
        println("Your input was incorrect please try again. If you want to pass press 'c'")
        turnFor -= 1
      }
    } while (input != "q")
  }
}
