package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.controller.GameStatus.{GameStatus, PLAYING, INPUTFAIL}
import scala.de.htwg.se.gwent.model.{Field, Player}

class GameLogic {
  def applyLogic(field: Field, row: Int, col:Int, player: Player, cardIndex: Int):GameStatus = {
    if (player.handCard.size > cardIndex && cardIndex >= 0 && 0 <= col && col < 4 && (row.equals(player.playArea(1)) || row.equals(player.playArea(0))) && field.isEmpty(col,row)) {
      return PLAYING
    }
    INPUTFAIL
  }
}

