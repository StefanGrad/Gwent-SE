package de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.gwent.controller.controllerComponent
import de.htwg.se.gwent.controller.controllerComponent.GameStatus.{GameStatus, INPUTFAIL, PASSED, PLAYING}
import de.htwg.se.gwent.controller.controllerComponent._
import de.htwg.se.gwent.model.cardComponent.CardInterface
import de.htwg.se.gwent.model.cardComponent.cardBaseImpl.{Card, HandCard}
import de.htwg.se.gwent.model.fieldComponent.FieldInterface
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Field, TurnLogic, WeatherState}
import de.htwg.se.gwent.model.playerComponent
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import de.htwg.se.gwent.model.playerComponent.{Player, PlayerType}

import scala.de.htwg.se.gwent.util.UndoManager
import scala.swing.Publisher

class Controller(var field: FieldInterface, var playerTop: Player, var playerBot: Player) extends ControllerInterface with Publisher {
  var gameMessage = ""
  var gameState: GameStatus = PLAYING
  val turnLogic = new TurnLogic(0,0)
  val logic = new GameLogic
  private val undoManager = new UndoManager
  def createField:Unit = {
    field = Field(Vector[Vector[Option[Card]]]()).clear
    publish(new CellChanged)
  }
  def fieldToString: String = field.toString

  def changeGameStatus(gameStatus: GameStatus.Value): Unit = gameState = gameStatus

  def evaluate(fieldPlay: FieldInterface, playerTop: Player, playerBot: Player): Unit = {
    val winner = fieldPlay.evaluator.eval(fieldPlay,fieldPlay.weather)
    gameState = PLAYING
    turnLogic.nextRound
    if (winner == 0) {
      updateWins(TOP)
      undoManager.nextRound
      gameMessage = "Winner Top"
      return clearField(fieldPlay)
    }
    if (winner == 1) {
      updateWins(BOT)
      undoManager.nextRound
      return clearField(fieldPlay)
    }
    undoManager.nextRound
    clearField(fieldPlay)
  }

  def clearField(fieldPlay: FieldInterface): Unit = {
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
        playerTop = playerComponent.Player(playerType,name, HandCard(Vector[CardInterface]()).newDeck(),0)
        publish(new PlayerChanged)
      case BOT =>
        playerBot = playerComponent.Player(playerType,name, HandCard(Vector[CardInterface]()).newDeck(),0)
        publish(new PlayerChanged)
    }
  }

  def playerToString(player: Player): String = player.toString

  def playCardAt(fieldPlay: FieldInterface, row: Int, col:Int, playerType: PlayerType.Value , cardIndex: Int): Unit = {
    val player = choosePlayer.choice(playerType).player(this)
    if (playerType != turnLogic.whoCanPlay) {return publish(new CellChanged)}
    val tuple = logic.applyTryLogic(fieldPlay,row, col, player, cardIndex)
    gameState = tuple._1
    gameMessage = tuple._2
    if (gameState.equals(INPUTFAIL)) {return publish(new CellChanged)}
    undoManager.doStep(new PlayCardCommand(fieldPlay,row,col, playerType, cardIndex, this))
    publish(new CellChanged)
  }

  def playCard(fieldPlay: FieldInterface, playerType: PlayerType.Value , cardIndex: Int): Unit = {
    val player = choosePlayer.choice(playerType).player(this)
    if (playerType != turnLogic.whoCanPlay) return publish(new CellChanged)
    for {
      row <- 0 until 4
      column <- 0 until 4
    } {
      var tuple = logic.applyTryLogic(fieldPlay,row, column, player, cardIndex)
      if (tuple._1.equals(PLAYING)) {
        if (field.isEmpty(row, column)) {
          gameState = tuple._1
          gameMessage = tuple._2
          undoManager.doStep(new PlayCardCommand(fieldPlay, row, column, playerType, cardIndex, this))
          return publish(new CellChanged)
        }
      }
    }
    gameMessage = "No available Spots for this Card"
    gameState = INPUTFAIL
    publish(new CellChanged)
  }

  def passRound():Unit = {
    if (gameState.equals(PASSED)) {
      evaluate(field,playerTop,playerBot)
      return publish(new CellChanged)
    }
    gameState = PASSED
    turnLogic.doTurn
    publish(new CellChanged)
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
