package aview

import controller.Controller

import util.Observer

class Tui(controller: Controller) extends Observer{

  controller.add(this)
  var botPassed = false
  var topPassed = false
  var failedInput = false

  def processInputLineTop(input: String):Unit = {
    input match {
      case "q" =>
      case "c" =>
        if (botPassed) {
          botPassed = false
          topPassed = false
          controller.evaluate(controller.field,controller.playerTop,controller.playerBot)
        }
        topPassed = true
        println("Player " + controller.playerTop.name + " has passed this Turn.")
      case _ => {
        input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: column :: cardIndex :: Nil =>
            if (controller.playerTop.handCard.size > cardIndex & cardIndex >= 0 & controller.field.isEmpty(column,row) & 0 <= column & column < 4 & 0 <= row & row < 2) {
              controller.playCardAt(controller.field, row, column, controller.playerTop, cardIndex)
              topPassed = false
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
      case "c" =>
        if (topPassed) {
          botPassed = false
          topPassed = false
          controller.evaluate(controller.field,controller.playerBot,controller.playerBot)
        }
        botPassed = true
        println("Player " + controller.playerBot.name + " has passed this Turn.")
      case _ => {
        input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case row :: column :: cardIndex :: Nil =>
            if (controller.playerBot.handCard.size > cardIndex & cardIndex >= 0 & controller.field.isEmpty(column,row) & 0 <= column & column < 4 & 2 <= row & row < 4) {
              controller.playCardAt(controller.field, row, column, controller.playerBot, cardIndex)
              botPassed = false
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

  override def update: Unit =  println(controller.fieldToString)

}
