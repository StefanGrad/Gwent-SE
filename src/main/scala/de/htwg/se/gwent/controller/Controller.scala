package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.model.{Card, Field, HandCard, Player}

import scala.de.htwg.se.gwent.util.Observable

class Controller(var field: Field, var playerTop: Player, var playerBot: Player) extends Observable {

  def createField:Unit = {
    field = Field(4,4)
    notifyObservers
  }

  def fieldToString: String = field.toString

  def evaluate(fieldPlay: Field, playerTop: Player, playerBot: Player): Unit = {
    val winner = fieldPlay.evaluator.eval(fieldPlay,playerTop,playerBot)
    if (winner == 1) {
      updatePlayerTopWins(playerTop)
      return fieldPlay.clear(fieldPlay)
    }
    if (winner == 2) {
      updatePlayerBotWins(playerBot)
      return fieldPlay.clear(fieldPlay)
    }
    fieldPlay.clear(fieldPlay)
  }

  def clearField(fieldPlay: Field): Unit = {
    field = fieldPlay.clear(fieldPlay)
    notifyObservers
  }

  def createPlayerTop(name:String):Unit = {
    playerTop = Player(name, HandCard(Vector[Card]()).newDeck(),0)
    notifyObservers
  }

  def createPlayerBot(name:String):Unit = {
    playerBot = Player(name, HandCard(Vector[Card]()).newDeck(),0)
    notifyObservers
  }

  def updatePlayerTopWins(player: Player): Unit = {
    playerTop = player.updateWins(player)
    notifyObservers
  }

  def updatePlayerBotWins(player: Player): Unit = {
    playerBot = player.updateWins(player)
    notifyObservers
  }

  def playerToString(player: Player): String = player.toString

  def playCardAt(fieldPlay: Field, row: Int, col:Int, player: Player, cardIndex: Int): Unit = {
    val rememberTop = player.equals(playerTop)
    val tuple = player.handCard.playCard(cardIndex,fieldPlay,row,col)
    field = tuple._3
    val name = player.name
    if (rememberTop) {
      playerTop = Player(name, tuple._2,player.wins)
      return notifyObservers
    }
    playerBot = Player(name,tuple._2, player.wins)
    notifyObservers
  }



}
