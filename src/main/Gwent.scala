package main

import main.model.{Player,Grid}
import main.tui


object Gwent{
  def main(args: Array[String]): Unit = {
    var grid = new Grid(4)
    val tui = new Tui

    def main(args: Array[String]): Unit = {
      var input: String = ""

      do {
        println("Grid : " + grid.toString)
        input = readLine()
        grid = tui.processInputLine(input, grid)
      } while (input != "q")
    }
  }
}