package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.controller.WeatherStatus.{FROST, SUNSHINE, WeatherState}
import scala.de.htwg.se.gwent.controller.GameStatus.{GameStatus, INPUTFAIL, PASSED, PLAYING}
import scala.de.htwg.se.gwent.model.PlayerType.{BOT, TOP}
import scala.de.htwg.se.gwent.model.{Card, Field, HandCard, Player, PlayerType}
import scala.de.htwg.se.gwent.util.{Observable, UndoManager}

class Controller(var field: Field, var playerTop: Player, var playerBot: Player) extends Observable {
  var gameState: GameStatus = PLAYING
  var weather = new WeatherState.Sunshine
  val logic = new GameLogic
  private val undoManager = new UndoManager
  def createField:Unit = {
    field = Field(Vector[Vector[Option[Card]]]()).clear
    notifyObservers
  }
  val change = WeatherState.choice(FROST)
  def fieldToString: String = field.toString

  def evaluate(fieldPlay: Field, playerTop: Player, playerBot: Player): Unit = {
    val winner = fieldPlay.evaluator.eval(fieldPlay,playerTop,playerBot,weather)
    gameState = PLAYING
    if (winner == 1) {
      updateWins(TOP)
      return fieldPlay.clear
    }
    if (winner == 2) {
      updateWins(BOT)
      return fieldPlay.clear
    }
    fieldPlay.clear
  }

  def clearField(fieldPlay: Field): Unit = {
    field = fieldPlay.clear
    gameState = PLAYING
    notifyObservers
  }

  def updateWins(playerType: PlayerType.Value):Unit = {
    playerType match {
      case TOP => playerTop = playerTop.updateWins(playerTop)
        notifyObservers
      case BOT => playerBot = playerBot.updateWins(playerBot)
        notifyObservers
    }
  }

  def createPlayer(name:String,playerType: PlayerType.Value):Unit = {
    playerType match {
      case TOP =>
        playerTop = Player(playerType,name, HandCard(Vector[Card]()).newDeck(),0)
        return notifyObservers
      case BOT =>
        playerBot = Player(playerType,name, HandCard(Vector[Card]()).newDeck(),0)
        notifyObservers
    }
  }

  def playerToString(player: Player): String = player.toString

  def playCardAt(fieldPlay: Field, row: Int, col:Int, playerType: PlayerType.Value , cardIndex: Int): Unit = {
    val player = choosePlayer.choice(playerType).player(this)
    gameState = logic.applyLogic(fieldPlay,row, col, player, cardIndex)
    if (gameState.equals(INPUTFAIL)) {return notifyObservers}
    undoManager.doStep(new PlayCardCommand(fieldPlay,row,col, playerType, cardIndex, this))
    notifyObservers
    /*weather.changeWeather(player.handCard.show(cardIndex))
    val tuple = player.handCard.playCard(cardIndex,fieldPlay,row,col)
    field = tuple._3
    val name = player.name
    playerType match {
      case TOP =>
        playerTop = Player(playerType,name, tuple._2,player.wins)
        return notifyObservers
      case BOT =>
        playerBot = Player(playerType,name, tuple._2,player.wins)
        return notifyObservers
    }*/
  }

  def passRound():Unit = {
    if (gameState.equals(PLAYING)) {
      gameState = PASSED
      return notifyObservers
    }
    evaluate(field,playerTop,playerBot)
  }

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
    notifyObservers
  }

}
