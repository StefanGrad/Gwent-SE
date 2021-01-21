package de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl

import de.htwg.se.gwent.model.fieldComponent.FieldInterface
import de.htwg.se.gwent.model.fieldComponent.fieldBaseImpl.WeatherState.State

case class Evaluation() {
  def eval(field: FieldInterface, weatherstate: State):Int = {
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
      return 0
    }
    if(topC - botC < 0){
      return 1
    }
    2
  }
}
