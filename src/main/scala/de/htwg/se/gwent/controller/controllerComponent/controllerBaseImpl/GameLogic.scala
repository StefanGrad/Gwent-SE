package de.htwg.se.gwent.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.gwent.controller.controllerComponent.GameStatus.{GameStatus, INPUTFAIL, PLAYING}
import de.htwg.se.gwent.model.fieldComponent.FieldInterface
import de.htwg.se.gwent.model.playerComponent.Player

import scala.util.{Failure, Success, Try}

class GameLogic{

  def applyTryLogic(field: FieldInterface, row: Int, col:Int, player: Player, cardIndex: Int): (GameStatus,String) = {
    Try(field.isEmpty(col,row)) match {
      case Success(v) =>
        if(v) {
          Try(player.handCard.show(cardIndex)) match {
            case Success(v) =>
              if (player.playerArea(v.range) == row) {
                return (PLAYING, "")
              }
              return (INPUTFAIL, "You are playing for the enemy.")
            case Failure(exception) =>
              return (INPUTFAIL, "You don't have such a card.")
          }
        }
        return (INPUTFAIL,"The Position is already filled.")
      case Failure(exception) =>
        return (INPUTFAIL,"Please play within the game area")
      case _ => return (INPUTFAIL,"Please play within the game area")
    }
  }
}