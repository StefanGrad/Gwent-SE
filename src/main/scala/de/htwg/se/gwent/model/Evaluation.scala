package scala.de.htwg.se.gwent.model

import scala.de.htwg.se.gwent.controller.WeatherState.State
import scala.de.htwg.se.gwent.controller.WeatherStatus.{FOG, FROST, SUNSHINE, WeatherState}


case class Evaluation() {
  def eval(field: Field, playerTop: Player, playerBot: Player, weatherstate: State):Int = {
    var topC = 0
    var botC = 0
    for {
      c <- 0 until field.size
      index <- 0 until weatherstate.rowTop.length
    } topC = field.getCard(weatherstate.rowTop(index), c) match {
        case Some(value) => topC + value.strength
        case None =>topC
      }
    for {
      c <- 0 until field.size
      index <- 0 until weatherstate.rowBot.length
    } field.getCard(weatherstate.rowTop(index), c) match {
        case Some(value) => botC += value.strength
        case None =>
      }
    /*
   if (weatherstate.equals(SUNSHINE)) {
      for {
        r <- 0 until (field.row -2)
        c <- 0 until field.col
      } topC += field.getCard(r,c).strength
      for {
        r <- 2 until field.row
        c <- 0 until field.col
      } botC += field.getCard(r,c).strength

    } else if (weatherstate.equals(FOG)) {
      for {
      r <- 1 until (field.row -2)
      c <- 0 until field.col
      } topC += field.getCard(r,c).strength
      for {
        r <- 2 until (field.row -1)
        c <- 0 until field.col
      } botC += field.getCard(r,c).strength

    } else if (weatherstate.equals(FROST)){
      for {
        r <- 0 until (field.row - 3)
        c <- 0 until field.col
      } topC += field.getCard(r,c).strength
      for {
        r <- 3 until field.row
        c <- 0 until field.col
      } botC += field.getCard(r,c).strength
    }
*/
    if (topC - botC > 0) {
      println("The winner of this round is " + playerTop.name)
      return 1
    }
    if(topC - botC < 0){
      println("The winner of this round is " + playerBot.name)
      return 2
    }
    println("The game ended with a tie")
    0
  }

}
