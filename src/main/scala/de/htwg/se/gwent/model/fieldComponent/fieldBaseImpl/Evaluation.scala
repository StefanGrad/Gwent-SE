package de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl

import de.htwg.se.gwent.model.fieldComponent.FieldInterface
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.State
import de.htwg.se.gwent.model.playerComponent.Player

case class Evaluation() {
  def eval(field: FieldInterface, playerTop: Player, playerBot: Player, weatherstate: State):String = {
    var topC = 0
    var botC = 0
    for {
      c <- 0 until field.size
      index <- 0 until weatherstate.rowTop.length
    } field.getCard(weatherstate.rowTop(index), c) match {
        case Some(value) => topC += value.strength
        case None =>
      }
    for {
      c <- 0 until field.size
      index <- 0 until weatherstate.rowBot.length
    } field.getCard(weatherstate.rowBot(index), c) match {
        case Some(value) => botC += value.strength
        case None =>
      }
    if (topC - botC > 0) {
      return "The winner of this round is Top"
    }
    if(topC - botC < 0){

      return "The winner of this round is Bot"
    }
    "The game ended with a tie"
  }

}
