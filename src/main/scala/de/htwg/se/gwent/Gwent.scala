package scala.de.htwg.se.gwent

import com.google.inject.Guice
import de.htwg.se.gwent.GwentModule
import de.htwg.se.gwent.aview.gui.SwingGUI
import de.htwg.se.gwent.controller.controllerComponent.{CellChanged, ControllerInterface}
import de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.gwent.model.playerComponent
import de.htwg.se.gwent.model.playerComponent.Player

import scala.de.htwg.se.gwent.aview.Tui
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Field, HandCard, TurnLogic}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

import scala.io.StdIn.readLine

object Gwent{
  val injector = Guice.createInjector(new GwentModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
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
      tui.processInputLine(input, controller.turnLogic.whoCanPlay)
    } while (input != "q")
    println("See you next time")
  }
}
