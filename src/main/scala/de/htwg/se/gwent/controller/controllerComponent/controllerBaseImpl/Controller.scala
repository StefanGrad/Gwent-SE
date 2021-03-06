package de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject}
import de.htwg.se.gwent.GwentModule
import de.htwg.se.gwent.controller.controllerComponent.GameStatus.{GameStatus, INPUTFAIL, LOADED, PASSED, PLAYING,SAVED}
import de.htwg.se.gwent.controller.controllerComponent._
import de.htwg.se.gwent.model.fieldComponent.{FieldInterface}
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.Sunshine
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.{Card, Field, HandCard}
import de.htwg.se.gwent.model.fileIOComponent.FileIOInterface
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}
import de.htwg.se.gwent.model.playerComponent.{Player, PlayerType}
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector

import scala.de.htwg.se.gwent.util.UndoManager
import scala.swing.Publisher

class Controller @Inject() (var field: FieldInterface) extends ControllerInterface with Publisher {
  var gameMessage = ""
  var gameState: GameStatus = PLAYING
  val logic = new GameLogic
  val injector = Guice.createInjector(new GwentModule)
  val fileIo = injector.instance[FileIOInterface]
  clearField(field)

  private val undoManager = new UndoManager
  def createField:Unit = {
    undoManager.clear
    field = Field(Vector[Vector[Option[Card]]](),new Sunshine,Player(TOP,"Player Top",HandCard(Vector[Card]()).newHandCard(),0),Player(BOT,"Player Bottom",HandCard(Vector[Card]()).newHandCard(),0),0,0).clear
    publish(new GameChange)
  }
  def fieldToString: String = field.toString

  def changeGameStatus(gameStatus: GameStatus.Value): Unit = gameState = gameStatus

  def evaluate(fieldPlay: FieldInterface): Unit = {
    val winner = fieldPlay.evaluator.eval(fieldPlay,fieldPlay.weather)
    gameState = PLAYING
    field = fieldPlay.nextRound
    undoManager.clear
    publish(new Sunny)
    if (field.round == 4) {
      gameMessage = "The Game ended with " + field.playerTop.wins + " wins for " + field.playerTop.name + " and " + field.playerBot.wins + " wins for " + field.playerBot.name
      return createField
    }
    if (winner == 0) {
      updateWins(TOP)
      gameMessage = "Winner Top"
      return clearField(field)
    }
    if (winner == 1) {
      updateWins(BOT)
      gameMessage = "Winner Bot"
      return clearField(field)
    }
    gameMessage = "The game ended with a Draw"
    clearField(field)
  }

  def clearField(fieldPlay: FieldInterface): Unit = {
    field = fieldPlay.clear
    gameState = PLAYING
    publish(new Sunny)
    publish(new GameChange)
  }

  def updateWins(playerType: PlayerType.Value):Unit = {
    field = field.updateWins(playerType)
    publish(new GameChange)
  }

  def playerToString(player: Player): String = player.toString

  def playCardAt(row: Int, col:Int, playerType: PlayerType.Value , cardIndex: Int): Unit = {
    val player = choosePlayer.choice(playerType).player(this)
    val abilityOfCard = player.handCard.show(cardIndex).ability
    if (playerType != field.whoCanPlay) {return}

    val tuple = logic.applyTryLogic(this.field,row, col, player, cardIndex)
    gameState = tuple._1
    gameMessage = tuple._2
    if (gameState.equals(PLAYING)) {
      abilityOfCard match {
        case 1 => publish(new Frosty)
        case 2 => publish(new Fogy)
        case 3 => publish(new Sunny)
        case _ =>
      }
      undoManager.doStep(new PlayCardCommand(this.field, row, col, playerType, cardIndex, this))
    }
    publish(new GameChange)
  }

  def playCard(playerType: PlayerType.Value , cardIndex: Int): Unit = {
    if (playerType != this.field.whoCanPlay) {return}
    for {
      row <- 0 until 4
      column <- 0 until 4
    } {
      playCardAt(row, column,playerType,cardIndex)
      if(gameState.equals(PLAYING)) return publish(new GameChange)
    }
    gameMessage = "No available Spots for this Card"
    gameState = INPUTFAIL
    publish(new GameChange)
  }

  def safe: Unit = {
    fileIo.save(field)
    gameState = SAVED
    gameMessage = "Saved Game"
    publish(new GameChange)
  }

  def load: Unit = {
    field = fileIo.load
    gameState = LOADED
    gameMessage = "Loaded Game"
    undoManager.clear
    publish(new GameChange)
  }

  def passRound():Unit = {
    if (gameState.equals(PASSED)) {
      evaluate(field)
      return publish(new GameChange)
    }
    gameState = PASSED
    field = field.doTurn
    publish(new GameChange)
  }

  def undo: Unit = {
    undoManager.undoStep
    publish(new GameChange)
  }

  def redo: Unit = {
    undoManager.redoStep
    publish(new GameChange)
  }

}
