package de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject}
import de.htwg.se.gwent.GwentModule
import de.htwg.se.gwent.controller.controllerComponent.GameStatus.{GameStatus, INPUTFAIL, PASSED, PLAYING}
import de.htwg.se.gwent.controller.controllerComponent._
import de.htwg.se.gwent.model.fieldComponent.{CardInterface, FieldInterface}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Field, HandCard, TurnLogic, WeatherState}
import de.htwg.se.gwent.model.playerComponent
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import de.htwg.se.gwent.model.playerComponent.{Player, PlayerType}

import scala.de.htwg.se.gwent.util.UndoManager
import scala.swing.Publisher

class Controller @Inject() (var field: FieldInterface) extends ControllerInterface with Publisher {
  var gameMessage = ""
  var gameState: GameStatus = PLAYING
  val logic = new GameLogic
  val injector = Guice.createInjector(new GwentModule)
  clearField(field)

  private val undoManager = new UndoManager
  def createField:Unit = {
    field = Field(Vector[Vector[Option[Card]]](),new Sunshine,Player(TOP,"Adrian",HandCard(Vector[Card]()).newDeck(),0),Player(BOT,"Stefan",HandCard(Vector[Card]()).newDeck(),0),TurnLogic(0,0)).clear
    publish(new CellChanged)
  }
  def fieldToString: String = field.toString

  def changeGameStatus(gameStatus: GameStatus.Value): Unit = gameState = gameStatus

  def evaluate(fieldPlay: FieldInterface): Unit = {
    val winner = fieldPlay.evaluator.eval(fieldPlay,fieldPlay.weather)
    gameState = PLAYING
    field = field.nextRound
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
    field = field.updateWins(playerType)
    publish(new CellChanged)
  }

  def playerToString(player: Player): String = player.toString

  def playCardAt(fieldPlay: FieldInterface, row: Int, col:Int, playerType: PlayerType.Value , cardIndex: Int): Unit = {
    val player = choosePlayer.choice(playerType).player(this)
    if (playerType != field.turnLogic.whoCanPlay) {return publish(new CellChanged)}
    val tuple = logic.applyTryLogic(fieldPlay,row, col, player, cardIndex)
    gameState = tuple._1
    gameMessage = tuple._2
    if (gameState.equals(INPUTFAIL)) {return publish(new CellChanged)}
    undoManager.doStep(new PlayCardCommand(fieldPlay,row,col, playerType, cardIndex, this))
    publish(new CellChanged)
  }

  def playCard(fieldPlay: FieldInterface, playerType: PlayerType.Value , cardIndex: Int): Unit = {
    val player = choosePlayer.choice(playerType).player(this)
    if (playerType != field.turnLogic.whoCanPlay) return publish(new CellChanged)
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
      evaluate(field)
      return publish(new CellChanged)
    }
    gameState = PASSED
    field = field.doTurn
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
