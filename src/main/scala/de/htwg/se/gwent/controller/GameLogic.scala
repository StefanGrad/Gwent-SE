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

  def applyTryLogic(field: Field, row: Int, col:Int, player: Player, cardIndex: Int): GameStatus = {
    Try(field.isEmpty(col,row)) match {
      case Success(v) =>
        if (v) {
          Try(player.playerArea.contains(row)) match {
            case Success(v) =>
              if (v) {
                Try(player.handCard.show(cardIndex)) match {
                  case Success(v) => return PLAYING
                  case Failure(exception) =>
                    println("You don't have such a card.")
                    return INPUTFAIL
                }
              }
              println("You are playing for the enemy.")
              return INPUTFAIL
            case Failure(exception) =>
              println("Your playArea does not contain this row")
              return INPUTFAIL
          }
        }
        println("The Position is already filled.")
        return INPUTFAIL
      case Failure(exception) =>
        println("Please play within the game area")
        return INPUTFAIL
    }
  }
}
/*
val test = applyTryLogic(field, row, col, player, cardIndex)
    test._1 match {
      case Success(v) =>
        if (v) {
          test._2 match {
            case Success(v) =>
              if (v) {
                test._3 match {
                case Success(v) =>
                  if (v) {
                    return PLAYING
                  }
                  return INPUTFAIL
                case Failure(exception) => return INPUTFAIL
                }
              }
              return INPUTFAIL
            case Failure(exception) => return INPUTFAIL
          }
        }
        INPUTFAIL
      case Failure(exception) => return INPUTFAIL
    }
 */