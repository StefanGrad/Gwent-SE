package scala.de.htwg.se.gwent.controller

import de.htwg.se.gwent.controller.{CellChanged, PlayerChanged}

import scala.de.htwg.se.gwent.controller.WeatherStatus.{FROST, SUNSHINE, WeatherState}
import scala.de.htwg.se.gwent.controller.GameStatus.{GameStatus, INPUTFAIL, PASSED, PLAYING}
import scala.de.htwg.se.gwent.controller.WeatherState.Sunshine
import scala.de.htwg.se.gwent.model.PlayerType.{BOT, TOP}
import scala.de.htwg.se.gwent.model.{Card, Field, HandCard, Player, PlayerType}
import scala.de.htwg.se.gwent.util.{Observable, UndoManager}
import scala.swing.Publisher

class Controller(var field: Field, var playerTop: Player, var playerBot: Player,var weather: WeatherState.State) extends Publisher {
  var gameMessage = ""
  var gameState: GameStatus = PLAYING
  val logic = new GameLogic
  private val undoManager = new UndoManager
  def createField:Unit = {
    field = Field(Vector[Vector[Option[Card]]]()).clear
    publish(new CellChanged)
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
    publish(new CellChanged)
  }

  def updateWins(playerType: PlayerType.Value):Unit = {
    playerType match {
      case TOP => playerTop = playerTop.updateWins(playerTop)
        publish(new PlayerChanged)
      case BOT => playerBot = playerBot.updateWins(playerBot)
        publish(new PlayerChanged)
    }
  }

  def createPlayer(name:String,playerType: PlayerType.Value):Unit = {
    playerType match {
      case TOP =>
        playerTop = Player(playerType,name, HandCard(Vector[Card]()).newDeck(),0)
        publish(new PlayerChanged)
      case BOT =>
        playerBot = Player(playerType,name, HandCard(Vector[Card]()).newDeck(),0)
        publish(new PlayerChanged)
    }
  }

  def playerToString(player: Player): String = player.toString

  def playCardAt(fieldPlay: Field, row: Int, col:Int, playerType: PlayerType.Value , cardIndex: Int): Unit = {
    val player = choosePlayer.choice(playerType).player(this)
    val tuple = logic.applyTryLogic(fieldPlay,row, col, player, cardIndex)
    gameState = tuple._1
    gameMessage = tuple._2
    if (gameState.equals(INPUTFAIL)) {return publish(new CellChanged)}
    undoManager.doStep(new PlayCardCommand(fieldPlay,row,col, playerType, cardIndex, this))
    publish(new CellChanged)
  }

  def passRound():Unit = {
    if (gameState.equals(PLAYING)) {
      gameState = PASSED
      publish(new CellChanged)
    }
    evaluate(field,playerTop,playerBot)
  }

  def undo: Unit = {
    undoManager.undoStep
    publish(new CellChanged)
  }

  def redo: Unit = {
    undoManager.redoStep
    publish(new CellChanged)
  }

}
