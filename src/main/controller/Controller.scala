package controller

import util.Observable
import model.{Field, HandCard, Player}

class Controller(var field: Field, var playerTop: Player, var playerBot: Player) extends Observable {

  def createField:Unit = {
    field = Field(4,4)
    notifyObservers
  }

  def fieldToString: String = field.toString

  def evaluate(fieldPlay: Field, playerTop: Player, playerBot: Player): Unit = {
    fieldPlay.evaluator.eval(fieldPlay,playerTop,playerBot)
    fieldPlay.clear(fieldPlay)
  }

  def clearField(fieldPlay: Field): Unit = {
    field = fieldPlay.clear(fieldPlay)
    notifyObservers
  }

  def playCardAt(fieldPlay: Field, row: Int, col:Int, player: Player, cardIndex: Int): Unit = {
    val rememberTop = player.equals(playerTop)
    val tuple = player.handCard.playCard(cardIndex,fieldPlay,row,col)
    field = tuple._3
    val name = player.name
    if (rememberTop) {
      playerTop = Player(name, tuple._2)
      return notifyObservers
    }
    playerBot = Player(name,tuple._2)
    notifyObservers
  }



}
