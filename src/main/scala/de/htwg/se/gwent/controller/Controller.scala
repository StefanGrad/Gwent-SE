package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.controller.WeatherStatus.{FROST, SUNSHINE, WeatherState}
import scala.de.htwg.se.gwent.controller.GameStatus.{GameStatus, INPUTFAIL, PASSED, PLAYING}
import scala.de.htwg.se.gwent.controller.WeatherState.Sunshine
import scala.de.htwg.se.gwent.model.PlayerType.{BOT, TOP}
import scala.de.htwg.se.gwent.model.{Card, Field, HandCard, Player, PlayerType}
import scala.de.htwg.se.gwent.util.{Observable, UndoManager}

class Controller(var field: Field, var playerTop: Player, var playerBot: Player,var weather: WeatherState.State) extends Observable {
  var gameMessage = ""
  var gameState: GameStatus = PLAYING
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
    if (winner.equals("The winner of this round is Top")) {
      updateWins(TOP)
      undoManager.nextRound
      return clearField(fieldPlay)
    }
    if (winner.equals("The winner of this round is Bot")) {
      updateWins(BOT)
      undoManager.nextRound
      return clearField(fieldPlay)
    }
    undoManager.nextRound
    clearField(fieldPlay)
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
    gameState = logic.applyTryLogic(fieldPlay,row, col, player, cardIndex)._1
    if (gameState.equals(INPUTFAIL)) {return notifyObservers}
    undoManager.doStep(new PlayCardCommand(fieldPlay,row,col, playerType, cardIndex, this))
    notifyObservers
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
