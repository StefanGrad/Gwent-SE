package scala.de.htwg.se.gwent.model

import scala.de.htwg.se.gwent.controller.WeatherState.State
import scala.de.htwg.se.gwent.controller.WeatherStatus.{FOG, FROST, SUNSHINE, WeatherState}


case class Evaluation() {
  def eval(field: Field, playerTop: Player, playerBot: Player, weatherstate: State):String = {
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
