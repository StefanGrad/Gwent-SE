package scala.de.htwg.se.gwent.aview

import scala.de.htwg.se.gwent.util.Observer
import scala.de.htwg.se.gwent.controller.Controller

class Tui(controller: Controller) extends Observer{

  controller.add(this)
  var failedInput = false

  def processInputLineTop(input: String):Unit = {
    input match {
      case "q" =>
      case "c" =>
        controller.passRound()
      case _ => {
        input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: column :: cardIndex :: Nil =>
            if (controller.playerTop.handCard.size > cardIndex & cardIndex >= 0 & controller.field.isEmpty(column,row) & 0 <= column & column < 4 & 0 <= row & row < 2) {
              controller.playCardAt(controller.field, row, column, controller.playerTop, cardIndex)
              failedInput = false
              return
            }
            println("You choose an nonexisting Index or an invalid row/col.")
            failedInput = true
          case _ => failedInput = true
        }
      }
    }
  }

  def processInputLineBot(input: String):Unit = {
    input match {
      case "q" =>
      case "c" => controller.passRound()
      case _ => {
        input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: column :: cardIndex :: Nil =>
            if (controller.playerBot.handCard.size > cardIndex & cardIndex >= 0 & controller.field.isEmpty(column,row) & 0 <= column & column < 4 & 2 <= row & row < 4) {
              controller.playCardAt(controller.field, row, column, controller.playerBot, cardIndex)
              failedInput = false
              return
            }
            println("You choose the wrong Index")
            failedInput = true
          case _ => failedInput = true
        }
      }
    }
  }

   def update: Boolean =  {
    println(controller.fieldToString)
    true
  }

}
