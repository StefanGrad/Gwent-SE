package main

import main.model.{Card, Evaluation, Field, HandCard, Player}
import main.tui
import main.tui.Tui


object Gwent{
    var field = Field(4,4)
    val playerTop = Player("Adrian")
    val playerBot = Player("Stefan")
    val eval = new Evaluation
    val hand = new HandCard()
    println(hand.toString)
    hand.playCard(1,field,1,0)
    println(field.toString)
    println(hand.toString)
    println(eval.eval(field,playerTop, playerBot).toString)
    val tui = new Tui

    def main(args: Array[String]): Unit = {
      var input: String = ""

      do {
        println("Field : \n" + field.toString)
        input = scala.io.StdIn.readLine()
        field = tui.processInputLine(input, field, playerTop, playerBot,eval)
      } while (input != "close")

    }

}