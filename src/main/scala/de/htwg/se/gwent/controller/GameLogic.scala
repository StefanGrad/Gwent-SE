package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.controller.GameStatus.{GameStatus, INPUTFAIL, PLAYING}
import scala.de.htwg.se.gwent.model.{Field, Player}
import scala.util.{Failure, Success, Try}

class GameLogic{
  def applyLogic(field: Field, row: Int, col:Int, player: Player, cardIndex: Int):GameStatus = {
    if (player.handCard.size > cardIndex && cardIndex >= 0) {
      if (0 <= col && col < 4) {
        if ((row == player.playerArea(0)) || (row == player.playerArea(1))) {
          if (field.isEmpty(col, row)) {
            return PLAYING
          }
        }
      }
    }
    INPUTFAIL
  }

  def applyTryLogic(field: Field, row: Int, col:Int, player: Player, cardIndex: Int): (GameStatus,String) = {
    Try(field.isEmpty(col,row)) match {
      case Success(v) =>
        if (v) {
          if (player.playerArea.contains(row)) {
            Try(player.handCard.show(cardIndex)) match {
              case Success(v) => return (PLAYING,"")
              case Failure(exception) =>
                return (INPUTFAIL,"You don't have such a card.")
            }
          }
          return (INPUTFAIL,"You are playing for the enemy.")
        }
        return (INPUTFAIL,"The Position is already filled.")
      case Failure(exception) =>
        return (INPUTFAIL,"Please play within the game area")
    }
  }
}