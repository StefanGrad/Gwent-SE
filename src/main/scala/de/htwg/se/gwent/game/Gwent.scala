package de.htwg.se.gwent.game

import com.google.inject.Guice
import de.htwg.se.gwent.GwentModule
import de.htwg.se.gwent.aview.gui.SwingGUI
import de.htwg.se.gwent.controller.controllerComponent.{GameChange, ControllerInterface}

import scala.de.htwg.se.gwent.aview.Tui
import scala.io.StdIn.readLine

object Gwent{
  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new GwentModule)
    val controller = injector.getInstance(classOf[ControllerInterface])
    val tui = new Tui(controller)
    val gui = new SwingGUI(controller)
    controller.publish(new GameChange)

    println("Welcome to Gwent")
    println(controller.field.playerTop.handCard)
    println(controller.field.playerBot.handCard)
    var input: String = ""

    if(args.length != 0) {
      val input2 = args(0).split('_')
      for (i <- 0 until input2.length) {
        if(!input2(i).equals("q")) {
          tui.processInputLine(input2(i),controller.field.whoCanPlay)
        }
      }
    } else {
      do {
        input = readLine()
        tui.processInputLine(input, controller.field.whoCanPlay)
      } while (input != "q")
    }
    println("See you next time")
  }
}
