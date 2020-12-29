package de.htwg.se.gwent.controller.controllerComponent

object GameStatus extends Enumeration {
  type GameStatus = Value
  val PLAYING,PASSED,INPUTFAIL = Value
}
