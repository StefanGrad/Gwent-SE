package scala.de.htwg.se.gwent

import de.htwg.se.gwent.aview.gui.SwingGUI
import de.htwg.se.gwent.controller.controllerComponent.{CellChanged, Controller}
import de.htwg.se.gwent.model.playerComponent
import de.htwg.se.gwent.model.playerComponent.Player

import scala.de.htwg.se.gwent.aview.Tui
import de.htwg.se.gwent.model.cardComponent.cardBaseImpl.{Card, HandCard}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.Field
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

import scala.io.StdIn.readLine

object Gwent{
  val playerTop = Player(TOP,"Adrian",HandCard(Vector[Card]()).newDeck(),0)
  val playerBot = playerComponent.Player(BOT,"Stefan",HandCard(Vector[Card]()).newDeck(),0)
  val field = new Field(Vector[Vector[Option[Card]]]())
  val controller = new Controller(field.clear,playerTop,playerBot, new Sunshine)
  val tui = new Tui(controller)
  val gui = new SwingGUI(controller)
  controller.publish(new CellChanged)

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
      tui.processInputLine(input, controller.whoCanPlay)
    } while (input != "q")
    println("See you next time")
  }
}
