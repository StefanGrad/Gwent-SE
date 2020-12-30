package de.htwg.se.gwent.controller.controllerComponent

import de.htwg.se.gwent.model.fieldComponent.FieldInterface
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.TurnLogic
import de.htwg.se.gwent.model.playerComponent.{Player, PlayerType}

import scala.swing.Publisher
import scala.swing.event.Event

trait ControllerInterface extends Publisher{
  def field: FieldInterface
  def playerTop: Player
  def playerBot: Player
  def turnLogic: TurnLogic
  def gameMessage: String
  def createField:Unit
  def clearField(fieldPlay: FieldInterface): Unit
  def evaluate(fieldPlay: FieldInterface, playerTop: Player, playerBot: Player): Unit
  def updateWins(playerType: PlayerType.Value):Unit
  def playCardAt(fieldPlay: FieldInterface, row: Int, col:Int, playerType: PlayerType.Value , cardIndex: Int): Unit
  def playCard(fieldPlay: FieldInterface, playerType: PlayerType.Value , cardIndex: Int): Unit
  def passRound():Unit
  def changeGameStatus(gameStatus: GameStatus.Value): Unit
  def undo: Unit
  def redo: Unit
}

class CellChanged extends Event
class PlayerChanged extends Event
