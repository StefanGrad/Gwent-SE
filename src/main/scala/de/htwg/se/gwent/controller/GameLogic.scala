package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.controller.GameStatus.{GameStatus, INPUTFAIL, PLAYING}
import scala.de.htwg.se.gwent.model.{Field, Player, PlayerArea}

class GameLogic {
  def applyLogic(field: Field, row: Int, col:Int, player: Player, cardIndex: Int):GameStatus = {
    if (player.handCard.size > cardIndex && cardIndex >= 0){
      if (0 <= col && col < 4) {
        if((row == player.playerArea(0)) || (row == player.playerArea(1))) {
          if(field.isEmpty(col,row)) {
            return PLAYING
          }
        }
      }
    }
    INPUTFAIL
  }
}