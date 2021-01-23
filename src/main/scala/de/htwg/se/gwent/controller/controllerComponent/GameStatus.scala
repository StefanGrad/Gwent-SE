package de.htwg.se.gwent.controller.controllerComponent

object GameStatus extends Enumeration {
  type GameStatus = Value
  val PLAYING,PASSED,INPUTFAIL,SAVED,LOADED = Value
  //with mapping strings to the Values, a message System could be enabled
}
