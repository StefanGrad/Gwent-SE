package scala.de.htwg.se.gwent.controller

import scala.de.htwg.se.gwent.controller.GameStatus.{GameStatus, INPUTFAIL, PLAYING}
import scala.de.htwg.se.gwent.model.{Field, Player}
import scala.util.{Failure, Success, Try}

class GameLogic{
  def applyLogic(field: Field, row: Int, col:Int, player: Player, cardIndex: Int):GameStatus = {
/*Try(player.handCard.size > cardIndex && cardIndex >= 0 && 0 <= col && col < 4 && (row == player.playerArea(0)) || (row == player.playerArea(1)) && field.isEmpty(col,row)) match {
  case Success(value) => return PLAYING
  case Failure(exception) => return INPUTFAIL
}
*/if (player.handCard.size > cardIndex && cardIndex >= 0){
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
/*
trait Try[GameLogic] {
def map(f:GameLogic => GameLogic):Try[GameLogic]
}
case class Success[GameLogic](val l: GameLogic) extends Try[GameLogic] {
override def map(f: GameLogic => GameLogic) = new Success(f(l))
}
case class Failure[GameLogic]() extends Try[GameLogic] {
override def map(f: GameLogic => GameLogic) = new Failure
}*/