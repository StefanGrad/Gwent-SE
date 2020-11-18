package main

import main.model.{Card, Evaluation, Field, HandCard, Player}
import main.tui
import main.tui.Tui


object Gwent{
    var field = Field(4,4)
    val playerTop = Player("Adrian")
    val playerBot = Player("Stefan")
    val eval = new Evaluation
    var handBot = HandCard(Vector[Card]()).newDeck()
    var handTop = HandCard(Vector[Card]()).newDeck()
    val tui = new Tui

    def main(args: Array[String]): Unit = {
      var input: String = ""

      do {
        println("Field : \n" + field.toString)
        input = scala.io.StdIn.readLine()
        val change = tui.processInputLine(input, field, playerTop, handTop, playerBot, handBot, eval)
        field = change._1
        handTop = change._2
        handBot = change._3

      } while (input != "close")

    }

}