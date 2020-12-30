package de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl

import de.htwg.se.gwent.model.playerComponent.PlayerType
import de.htwg.se.gwent.model.playerComponent.PlayerType.{BOT, TOP}

case class TurnLogic(turn: Int, round: Int) {
  def getTurn: Int = turn
  def doTurn: TurnLogic = TurnLogic(this.turn +1, this.round)
  def undoTurn: TurnLogic = TurnLogic(this.turn -1, this.round)
  def getRound: Int = round
  def nextRound: TurnLogic = TurnLogic(0, this.round + 1)
  def whoCanPlay: PlayerType.Value = turn % 2 match {
    case 0 => TOP
    case 1 => BOT
  }
}
