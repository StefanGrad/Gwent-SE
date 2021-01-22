package scala.de.htwg.se.gwent

import com.google.inject.Guice
import de.htwg.se.gwent.GwentModule
import de.htwg.se.gwent.aview.gui.SwingGUI
import de.htwg.se.gwent.controller.controllerComponent.{CellChanged, ControllerInterface}
import de.htwg.se.gwent.model.playerComponent.PlayerType.{TOP,BOT}

import scala.de.htwg.se.gwent.aview.Tui
import scala.io.StdIn.readLine

object Gwent{
  val injector = Guice.createInjector(new GwentModule)
  val controller = injector.getInstance(classOf[ControllerInterface])
  val tui = new Tui(controller)
  val gui = new SwingGUI(controller)
  controller.publish(new CellChanged)

  def main(args: Array[String]): Unit = {
    println("Welcome to Gwent")
    controller.field.whoCanPlay match {
      case TOP => println(controller.field.playerTop)
      case BOT => println(controller.field.playerBot)
    }
    var input: String = ""
    /*
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
    else */
    do {
      input = readLine()
      tui.processInputLine(input, controller.field.whoCanPlay)
      controller.field.whoCanPlay match {
        case TOP => println(controller.field.playerTop)
        case BOT => println(controller.field.playerBot)
      }
    } while (input != "q")
    println("See you next time")
  }
}
